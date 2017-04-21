<?php

require_once('connect_add.php');

	if (isset($_GET["Token"])) {
		
		   $_uv_Token=$_GET["Token"];
 $q="INSERT INTO token VALUES ( 'null','$_uv_Token')";
 
 //"." ON DUPLICATE KEY UPDATE tokens = '$_uv_Token';"
 
 $reult=Blogic::execute_query($q);
 
 if($reult)
 {
    echo "Added in Database";
 }
 else
    echo mysql_error();

	}



?>