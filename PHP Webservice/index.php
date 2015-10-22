<?php
//header("Content-Type: application/json; charset=utf-8", true);
ini_set('display_errors', 'On');
error_reporting(E_ALL);
/**
 * File to handle all API requests
 * Accepts GET and POST
 *
 * Each request will be identified by TAG
 * Response will be JSON data
 
 
 /**
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {

 $tag = $_POST['tag'];
 
 // include db handler
 require_once 'DB_Functions.php';
 $db = new DB_Functions();
 
 // response Array
 $response = array("tag" => $tag, "success" => 0, "error" => 0);
 
 // check for tag type
 if ($tag == 'login') {
      // Request type is check Login
      $username = $_POST['username'];
      $password = $_POST['password'];
      mysql_select_db("db_homesecure");
      // check for user
      $user = $db->getUserByUsernameAndPassword($username, $password);
	  if ($user != false) {
          // user found
          // echo json with success = 1
          $response["success"] = 1;
		  
          $response["uid"] = $user["id"];
          $response["user"]["firstname"] = $user["firstname"];
		  $response["user"]["lastname"] = $user["lastname"];
		  $response["user"]["username"] = $user["username"];
          $response["user"]["email"] = $user["email"];
          echo json_encode($response);
      }
	  else {
          // user not found
          // echo json with error = 1
          $response["error"] = 1;
          $response["error_msg"] = "Incorrect username or password!";
          echo json_encode($response);
      }
 }else if ($tag == 'register') {
      // Request type is Register new user
      $firstname = $_POST['firstname'];
	  $lastname = $_POST['lastname'];
      $email = $_POST['email'];
	  $username = $_POST['username'];
      $password = $_POST['password'];
	  // check if user is already existed
      if ($db->isUserExisted($email)) {
          // user is already existed - error response
          $response["error"] = 2;
          $response["error_msg"] = "User already existed";
          echo json_encode($response);
	  } else {
		  // store user
		  $user = $db->storeUser($firstname, $lastname, $email, $username, $password);
		  
		  
		  if ($user) {
			   // user stored successfully
			   $response["success"] = 1;
			   $response["uid"] = $user["id"];
			   $response["user"]["firstname"] = $user["firstname"];
			   $response["user"]["lastname"] = $user["lastname"];
			   $response["user"]["username"] = $user["username"];
			   $response["user"]["email"] = $user["email"];
			   echo json_encode($response);
		  } else {
			   // user failed to store
			   $response["error"] = 1;
			   $response["error_msg"] = "Error occured in Registartion";
			   echo json_encode($response);
		 }
  
		}
  } else {
  echo "Invalid Request";
 }
} else {
 echo "Access Denied";
}
?>