<?php

require_once('connect_add.php');

   $Entry=$_GET['EntryCode'];
   $Product=$_GET['ProductCode'];
   $Name = $_GET['Name'];
   $O_Price = $_GET['OriginalPrice'];
   $D_Price=$_GET['DiscountPrice'];
   $desp=$_GET['Desp'];
   $req=$_GET['Req'];
    $result = Blogic::execute_query("insert into products values($Entry,$Product,'$Name',$O_Price,
    $D_Price,'$desp','$req')"); 
    if($result)
    {
        echo "hello";
    }
    else
    {
        echo "fuck prashant";
    }
    ?>