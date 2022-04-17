<?PHP
/**
 * This script is called to begin a session if a user properly authenticates.
 *
 * This script gets login information from a user via POST requests and then
 * compares this against the database of passwords, UIDs, and email addresses.
 * This information is found from calls to the Python helper methods
 * php_get_password_helper.py, php_get_user_info.py, and php_get_admin_helper.py.
 *
 * @author Steven Sleder
 *
 * @param string $_POST['email'] email for the user account being authenticated
 *
 * @param boolean $_POST['password'] password for the account being authenticated
 *
 * @return void
 */

	session_start();
	$email = null;
	$password = null;
	$UID = null;
	$msg = "No function executed";
	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		$email = $_POST['email'];
		$password = $_POST['password'];
		$db_password = exec("./php_helpers/php_get_password_helper.py $email");
		$db_UID = exec("./php_helpers/php_get_UID_helper.py $email");	
		$db_admin = exec("./php_helpers/php_get_admin_helper.py $db_UID");

		if ($password == "" || $db_password == "NULL")
		{
			$msg = "2";
		}

		// only need to compare these since the password
		// is found by username
		else if ($db_password == $password)
		{
			$msg = $db_UID;
			$_SESSION['UID'] = $db_UID;
			$_SESSION['admin'] = $db_admin;
		}
		// Incorrect login info
		else
		{
			$msg =  "1";
		}
	}
	else
	{
		$msg = "3";
	}
	echo $msg;
?>
