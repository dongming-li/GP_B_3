<?php
/**
 * Simply fetches an array of all rides and prints them to stdout
 * where the provided email address is listed as being a rider.
 *
 * Rides are fetched with a shell_exec() call to php_get_all_email_rider.py
 * with the email address of the account passed in ARGV.
 *
 * @param string $_POST['email'] email address of the account to check for
 *
 * @param integer $_POST['user_id'] unique user ID number for the account to be searched for
 *
 * @return void
 */

#	session_start();
#	$msg;
#	if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
#	{
		$email = $_POST['email'];
		$UID = $_POST['user_id'];

		$msg = exec("./php_helpers/php_get_all_email_rider.py $email");
#	}

#	else
#	{
#		$msg = "Request Not Post or Not Logged In";
#	}

	echo ($msg);
?>
