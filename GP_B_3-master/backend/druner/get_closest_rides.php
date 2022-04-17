<!DOCTYPE html>
<html>
<body>

<?php
	$output = shell_exec('/usr/bin/python test1.py');
	echo $output;
?> 

</body>
</html>