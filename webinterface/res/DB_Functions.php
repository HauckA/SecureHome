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
			if (!file_exists($_SERVER['DOCUMENT_ROOT'] . '/SecureHome/registratedUserhome/' . $user_email . "/" . $webcam_id)) {
					mkdir($_SERVER['DOCUMENT_ROOT'] . '/SecureHome/registratedUserhome/' . $user_email . "/" . $webcam_id, 0777, true);
					//echo "Ordner erfolgreich erstellt";
			}
			 
            $result = mysql_query("INSERT INTO user_device (user_id, device_id, isActive) VALUES ($user_id, '$webcam_id', false)");
            // return webcamid details
            return $webcam_id;
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