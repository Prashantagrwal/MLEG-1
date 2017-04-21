<?php

require_once('connect_add.php');

$response=array();
$qr="SELECT * FROM contactinfo" ;
//$qr="ALTER TABLE contactinfo AUTO_INCREMENT = 0";
$row=Blogic::execute_query($qr);
 
 
    if($row)
{
    
  if (mysql_num_rows($row) >0) 
{

              $response["product"] = array();
            $response["success"] = 1;
            while($result = mysql_fetch_array($row))
 {
            $product["id"] = $result["ContactId"];
            $product["name"] = $result["Name"];
            $product["phone"] = $result["Phone"];
            $product["subject"] = $result["Subject"];
            $product["confirm"] =$result["Confirm"];
            
            // success

 
            // user node
              array_push($response["product"], $product);
  }
            // echoing JSON response
            echo json_encode($response);
        } 
         else {
          mysql_error();  
            // no product found
          $response["success"] = 0;
            // echo no users JSON
            echo json_encode($response);
        }
        
        }
        else
        echo "No one";
        
        ?>