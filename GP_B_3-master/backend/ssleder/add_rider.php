<?php
/**
 *
 * Accepts POST requests to add a rider to a specific ride
 *
 * Changes to the ride by call to a Python helper script. Arguments are passed
 * to the helper script via a shell_exec() call with arguments in ARGV.
 *
 * @param string $_POST['ride_id'] unique ID number of the ride to add a user to
 *
 * @param string $_POST['email'] email of the rider to be added
 *
 * @param string $_POST['user_id'] unique ID of the particular user
 *
 *
 * @return void
 */

	#session_start();
	$msg = "lol";
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
	echo $msg;
?>
