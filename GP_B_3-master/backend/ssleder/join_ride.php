<?php
/**
 *
 * This script allows for a user to be added into an existing ride as a passenger
 *
 * The ride and user information are passed to the Python helper method
 * php_join_ride_helper.py in ARGV with a shell_exec() call
 *
 * @authoer Steven Sleder
 *
 * @param string $_POST['email'] email address of the account to be added
 *
 * @param integer $_POST['ride_id'] unique ride ID to find the ride
 *
 * @param integer $_POST['user_id'] unique user ID number
 *
 * @return void
 */

	session_start();
	$msg;
	#if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
	#{
		$ride_ID = $_POST['ride_id'];
		$email = $_POST['email'];
		$UID = $_POST['user_id'];
		$msg = shell_exec("./php_helpers/php_join_ride_helper.py $ride_ID $email");
	
	#}

	#else
	#{
	#	$msg = "Request Not Post or Not Logged In";
	#}
	echo ($msg);
?>
