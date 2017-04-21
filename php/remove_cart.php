<?php

require_once('connect_add.php');

if(isset($_GET["code"]) && isset($_GET["emailid"]))
{
    $code=$_GET["code"];
    $emailid=$_GET["emailid"];
    $qr="delete from cart where productcode='$code' and UserEmailId='$emailid'";
    $result=Blogic::execute_query($qr);
    if($result)
    {
        echo "Removed from cart";
    }
    else{
        mysql_error();
    }
}


?>