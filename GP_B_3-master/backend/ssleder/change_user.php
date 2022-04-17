<?php
/**
 * Accepts POST requests to update a user account.
 *
 * Changes to the user account are made by further passing the POST arguments
 * to a Python function via ARGV.
 *
 * @author Steven Sleder
 *
 * @param string $_POST['email'] current email of the account to change
 *
 * @param string $_POST['new_password'] password to replace current
 *
 * @param string $_POST['new_firstname'] firstname to replace current
 *
 * @param string $_POST['new_lastname'] lastname to replace current
 *
 * @param string $_POST['new_email'] email to replace current
 *
 * @param boolean $_POST['new_rider_status'] enables/disables rider status
 *
 * @param boolean $_POST['new_driver_status'] enables/disables driver status
 *
 * @param boolean $_POST['new_admin_status'] enables/disables admin status
 *
 * @param boolean $_POST['new_ban_status'] enables/disables ban status
 *
 * @param boolean $_POST['delete_account'] flag to delete account or not
 *
 * @return void
 */
	#session_start();
	$msg = "No function called";
	
	#if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
	#{
		$email = $_POST['email'];
		$new_password = $_POST['new_password'];	
		$new_firstname = $_POST['new_firstname'];
		$new_lastname = $_POST['new_lastname'];
		$new_email = $_POST['new_email'];
		$new_rider_status = $_POST['new_rider_status'];
		$new_driver_status = $_POST['new_driver_status'];
		$new_admin_status = $_POST['new_admin_status'];
		$new_ban_status = $_POST['new_ban_status'];
		$delete_account = $_POST['delete_account'];

		$msg = shell_exec("./php_helpers/php_change_user_helper.py $email $new_password $new_firstname $new_lastname $new_email $new_rider_status $new_driver_status $new_admin_status $new_ban_status $delete_account");
	
	#}

	#else
	#{
		$msg = "Request Not Post or Not Logged In";
	#}
	echo ($msg);
?>
