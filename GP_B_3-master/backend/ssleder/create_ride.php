<?php
/**
 *
 * Accepts POST requests to form a new ride
 *
 * Changes to generate a new, unique ride by call to a Python helper script. Arguments are passed
 * to the helper script via a shell_exec() call with arguments in ARGV.
 *
 * @author Steven Sleder
 *
 * @param string $_POST['email'] email of the driver to own the ride
 *
 * @param string $_POST['time'] time for the ride in the hh:mm:mm:dd format
 *
 * @param string $_POST['user_id'] unique ID of the driver
 *
 * @param string $_POST['max_radius'] maximum radius in miles the driver is willing to accept riders from
 *
 * @param string $_POST['start_address'] address that the ride will be leaving from
 *
 * @param string $_POST['end_address'] addresss that he ride will be ending at
 *
 * @param string $_POST['max_passengers'] maximum number of passengers the driver is willing to take on the ride
 *
 * @return void
 */

	#session_start();
	$msg = "No function called";
	
	#if ( ($_SERVER["REQUEST_METHOD"] == "POST") && (isset($_SESSION[])) && (!empty($_SESSION[])) )
	#{
		$email = $_POST['email'];
		$time = $_POST['time'];
		$UID = $_POST['user_id'];
		$max_radius = $_POST['max_radius'];
		$start_addr = $_POST['start_address'];
		$end_addr = $_POST['end_address'];
		$max_passengers = $_POST['max_passengers'];

		$msg = shell_exec("./php_helpers/php_add_ride_helper.py $email \"$time\" \"$start_addr\" \"$end_addr\" $max_passengers $max_radius");
	
	#}

	#else
	#{
		#$msg = "Request Not Post or Not Logged In";
	#}
	echo ($msg);
?>
