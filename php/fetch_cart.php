<?php

require_once('connect_add.php');
$response=array();
$UserEmailId=$_GET['UserEmailId'];
$qr="select * from products where ProductCode in (SELECT Productcode FROM cart WHERE UserEmailId='$UserEmailId')";


//$qr="ALTER TABLE contactinfo AUTO_INCREMENT = 0";
$row=Blogic::execute_query($qr);
 
 
 if($row)
{
    
  if (mysql_num_rows($row) >0) 
{
              $response["success"] = 1;
              $response["cart"] = array();
           
            while($result = mysql_fetch_array($row))
 {
           
            $cart["name"] = $result["Product Name"];
            $cart["product_code"]=$result["ProductCode"];
            $cart["o_price"] = $result["Original Price"];
            $cart["d_price"] = $result["Discount Price"];
                       
            // success

 
            // user node
              array_push($response["cart"], $cart);
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
        echo "No one".mysql_error();
    
?>