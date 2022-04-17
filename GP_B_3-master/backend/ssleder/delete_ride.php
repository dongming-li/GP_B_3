<?php
/**
 *
 * Accepts POST requests to delete an existing ride
 *
 * Deletion is actually handled by passing the POST arguments
 * to a Python function via ARGV which in turn calls the database handler.
 *
 * @author Steven Sleder
 *
 * @param boolean $_POST['ride_id'] unique ID number of the account to be deleted
 *
 * @return void
 */

#	session_start();
	$msg;
#	if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
#	{
		$ride_id = $_POST['ride_id'];
		$msg = exec("./php_helpers/php_delete_ride_helper.py $ride_id");
#	}

#	else
#	{
#		$msg = "Request Not Post or Not Logged In";
#	}
	echo ($msg);
?>
