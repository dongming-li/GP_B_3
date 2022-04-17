<?php 

// input
	// sender email
	// sender id
	// recipient email
	// recipient id
	// last message

// output
	// all messages that happened after the last message in a JSON array

// echo "sender_email: " . $_GET["sender_email"] . "<br>";
// echo "sender_id: " . $_GET["sender_id"] . "<br>";
// echo "recipient_email: " . $_GET["recipient_email"] . "<br>";
// echo "recipient_id: " . $_GET["recipient_id"] . "<br>";
// echo "last message: " . $_GET["message"] . "<br>";

// Read JSON file
$json = file_get_contents($_GET["sender_id"] . '.json');

//Decode JSON
$json_data = json_decode($json,true);

//Print data
// print_r($json_data[$_GET["recipient_id"]]);

// echo substr($_GET["message"], 1, strlen($_GET["message"])-2);

$all_new = [];
foreach ($json_data as $convo) {
	foreach ($convo as $message) {
	if($message["timestamp"] > $_GET["timestamp"])
		array_push($all_new, $message);
	}
}

echo json_encode($all_new, JSON_PRETTY_PRINT);

?>