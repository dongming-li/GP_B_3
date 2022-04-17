<?php
/**
 *
 * Returns all user infomation for a specific account
 *
 * Provided email address is passed in ARGV with a shell_exec() call
 * to the Python helper method php_get_user_info.py
 *
 * @author Steven Sleder
 *
 * @param string $_POST['email'] Email address sent in a post request for the account
 * to return info on
 *
 * @return void
 */

	#session_start();
	$msg;
	#if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
	#{
		$email = $_POST['email'];

		$msg = exec("./php_helpers/php_get_user_info_helper.py $email");
	/*}

	else
	{
		$msg = "Request Not Post or Not Logged In";
	}*/
	
	echo ($msg);
?>
