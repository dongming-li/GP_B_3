<?php
/**
 *
 * Simply fetches an array of all emails and prints them to stdout
 *
 * Emails are actually fetched via a shell_exec() to a Python helper method.
 *
 * @author Steven Sleder
 *
 * @return void
 */

	#session_start();
	$msg;
	
	#if ( (isset($_SESSION[])) && (!empty($_SESSION[])) )
	#{
		$msg = exec("./php_helpers/php_get_all_emails_helper.py");
	/*}
	else
	{
		$msg = "Not Logged In";
	}*/
?>

<?php echo ($msg); ?>
