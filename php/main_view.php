<?php

require_once('connect_add.php');

$qr="select * from main_view";
$row=Blogic::execute_query($qr);
$response=array();
$count=0;
if($row)
{
  
 if (mysql_num_rows($row) >0) 
{
              $response["success"] = 1;
              $response["product"] = array();
            while($result = mysql_fetch_array($row))
 {
            $count=$count+1;
            $product["category"] = $result["category"];
            $product["subcategory1"] = $result["subcategory1"];
            $product["subcategory2"] = $result["subcategory2"];
            $product["subcategory3"] =$result["subcategory3"];
            $product["price1"] =$result["price1"];
            $product["price2"] =$result["price2"];
            $product["price3"] =$result["price3"];
         
         array_push($response["product"], $product);   
}
             $response["count"]=$count;
  echo json_encode($response);
}
}
else
echo mysql_error();


?>