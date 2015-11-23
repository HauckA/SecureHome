<?php
class DB_Functions {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
	public function registerWebcam($user_id, $webcam_description) {
		$query = "INSERT INTO device (isActive, description) VALUES (false,'$webcam_description')";
		$result = mysql_query($query) or die(mysql_error());
		
		if ($result) {
            // get last webcam id 
            $webcam_id = mysql_insert_id(); // last inserted id
			
			//Get email of Userid
			$query = "select * from user where id = $user_id";
			$user = mysql_query($query) or die(mysql_error());
			$user = mysql_fetch_array($user);
			$user_email = $user['email'];
			
			//echo "Webcam ID: " . $webcam_id;
			//echo "UserMail ID: " .$user_email;
			//CAM Ordner in Benutzerordner erstellen
			if (!file_exists($_SERVER['DOCUMENT_ROOT'] . '/api/registratedUserhome/' . $user_email . "/" . $webcam_id)) {
					mkdir($_SERVER['DOCUMENT_ROOT'] . '/api/registratedUserhome/' . $user_email . "/" . $webcam_id, 0777, true);
					//echo "Ordner erfolgreich erstellt";
			}
			 
            $result = mysql_query("INSERT INTO user_device (user_id, device_id, isActive) VALUES ($user_id, '$webcam_id', false)");
            // return webcamid details
            return $webcam_id;
        } else {
            return false;
        }
	}
	public function getMailByUserid($uid) {
		$query = "SELECT email from user where id=$uid";
		$result = mysql_query($query) or die(mysql_error());
		
		if ($result) {
			$user = mysql_fetch_array($result);
			$user_email = $user['email'];
            return $user_email;
        } else {
            return false;
        }
	}
 
 
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($firstname, $lastname, $email, $username, $password) {
		//echo "$firstname, $lastname, $email, $username, $password";
        $hash = $this->hashSSHA($password);
		
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
		
		$query = "INSERT INTO user(firstname, lastname, email, username, password, salt) VALUES ('$firstname', '$lastname','$email', '$username', '$encrypted_password', '$salt')";
		//echo $query;
		
        $result = mysql_query($query) or die(mysql_error());
		//print_r($result);
		
        // check for successful store
        if ($result) {
            // get user details 
            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM user WHERE id = $uid");
			
			 //create Directory for new User with EMAIL
			  if (!file_exists($_SERVER['DOCUMENT_ROOT'] . '/api/registratedUserhome/' . $email)) {
					mkdir($_SERVER['DOCUMENT_ROOT'] . '/api/registratedUserhome/' . $email, 0777, true);
					//echo "Ordner erfolgreich erstellt";
			  }
			  
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }
 
    /**
     * Get user by username and password
     */
    public function getUserByUsernameAndPassword($username, $password) {
		mysql_select_db("db_homesecure");
        $result = mysql_query("SELECT * FROM user WHERE username = '$username'") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $result = mysql_query("SELECT email from user WHERE email = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
	
	/*
	 * Get all IDs of the Webcams that are connected to the user
	 * Params: User ID
	 * Return: An Array that contains Webcam IDs
	*/
	
	public function getWebcamsFromUser($uid) {
		$query = mysql_query("SELECT device_id FROM user_device WHERE user_id = '$uid'");
				
		if(mysql_num_rows($query) > 0) {
			
			$webcamsOfUser = array();
			
			while($row = mysql_fetch_assoc($query)) {
				array_push($webcamsOfUser, $row["device_id"]);
				
			}
								
			return $webcamsOfUser;
			
		}
	}
 
 /*
	 * Get all information of a webcam
	 * Params: Webcam ID
	 * Return: An Array with the status and description of the webcam
	*/
	
	public function getWebcamInfos($id) {
		$query = mysql_query("SELECT isActive, description FROM device WHERE id = '$id'");
				
		if(mysql_num_rows($query) > 0) {
			
			$webcamInfos = array();
			
			while($row = mysql_fetch_assoc($query)) {
				array_push($webcamInfos, $row["isActive"]);
				array_push($webcamInfos, $row["description"]);
				
			}
						
			return $webcamInfos;
			
		}
	}
 
	/**
	  * Set Cam to active
	  * @param userid,webcam_id
	  * 
	 */
	 public function setCamToActive($webcam_id) {
		$query = mysql_query("UPDATE device SET isActive=1 WHERE id=$webcam_id");
	
		// check for successful UPDATE
		if ($query) {
			return true;
		} else {
			return false;
		}
	}

	/**
	  * Set Cam to Inactive
	  * @param userid,webcam_id
	  * 
	 */
	 public function setCamToInactive($webcam_id) {
		
		$query = mysql_query("UPDATE device SET isActive=0 WHERE id=$webcam_id");
	
		// check for successful UPDATE
		if ($query) {
			return true;
		} else {
			return false;
		}
	}
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
}
 
?>