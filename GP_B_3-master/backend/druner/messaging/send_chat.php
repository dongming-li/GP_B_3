<?php
	ini_set('display_errors', 1); error_reporting(E_ALL);
	// input:
		// sender_email
		// sender_id
		// recipient_email
		// recipient_id
		// message
	
	// post conditions:
		// added the message to the JSON file

	// output:
		// wether or not it was sent. "message sent" or "message failed to send". if it was succsesful, print the time as well. Everything in JSON format

	// Read JSON file
	$json = file_get_contents($_GET["sender_id"] . '.json');

	//Decode JSON
	$json_data = json_decode($json,true);

	$message_data = array("message" => $_GET["message"], "from" => $_GET["sender_email"], "timestamp" => time(), "to" => $_GET["recipient_email"]);

	array_push($json_data[$_GET["recipient_id"]], $message_data);
	echo json_encode($json_data, JSON_PRETTY_PRINT);

	// write the new data
	$myfile = fopen($_GET["sender_id"] . '.json', "w") or die("Unable to open file " . $_GET["sender_id"] . '.json' );
	fwrite($myfile, json_encode($json_data, JSON_PRETTY_PRINT));
	fclose($myfile);

	echo "message sent";
?> 