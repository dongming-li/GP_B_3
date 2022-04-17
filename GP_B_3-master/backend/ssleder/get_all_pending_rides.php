<?php
/**
 *
 * Simply fetches an array of all pending rides and prints them to stdout
 *
 * Rides are actually fetched via a shell_exec() to a Python helper method
 * php_get_all_pending_rides_helper.py
 *
 * @author Steven Sleder
 *
 * @return void
 */

#	session_start();
	$msg;
#	if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
#	{
		$msg = exec("./php_helpers/php_get_all_pending_rides_helper.py");
#	}

#	else
#	{
		$msg = "Request Not Post or Not Logged In";
#	}
?>

		<?php echo ($msg); ?>
