<?php
/**
 *
 * Returns a list of all rides where the given email is the driver.
 *
 * Rides are fetched by call with the email in ARGV via exec_shell()
 * to php_get_all_email_driver.py
 *
 * @author Steven Sleder
 *
 * @param string $_POST['email']  email address of the account to check for
 *
 * @param integer $_POST['user_id'] unique ID number of the user account
 *
 * @return void
 */

#	session_start();
	$msg;
#	if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
#	{
		$email = $_POST['email'];
		$UID = $_POST['user_id'];
		$msg = exec("./php_helpers/php_get_all_email_driver.py $email");
#	}

#	else
#	{
#		$msg = "Request Not Post or Not Logged In";
#	}
?>

		<?php echo ($msg); ?>
