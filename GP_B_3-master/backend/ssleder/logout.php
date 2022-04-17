<?PHP
/**
 *
 * This script logs out an account by destroying the session
 *
 * @author Steven Sleder
 *
 * @return void
 */

	session_start();

	session_destroy();
	$msg = "Destroyed Session";
?>
		<?php echo ($msg); ?>
