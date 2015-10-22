<?php
class DB_Connect {
 
    // constructor
    function __construct() {
         
    }
 
    // destructor
    function __destruct() {
         //$this->close();
    }
 
    // Connecting to database
    public function connect() {
        /**
         * Database config variables
         */
         define("DB_HOST", "localhost");
         define("DB_USER", "root");
         define("DB_PASSWORD", "");
         define("DB_DATABASE", "db_homesecure");
 
        // connecting to mysql
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
        // selecting database
        mysql_select_db(DB_DATABASE);
	//print_r($con);
        // return database handler
        return $con;
    }
 
    // Closing database connection
    public function close() {
        mysql_close();
    }
 
}
?>