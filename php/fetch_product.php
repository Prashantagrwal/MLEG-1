<?php

require_once('connect_add.php');


$category=$_GET['category'];
 $subcategory=$_GET['subcategory'];
$response=array();

if($subcategory!="null")
{ 
   
$qr="select * from products where category='$category' and subcategory='$subcategory'";
$row=Blogic::execute_query($qr);
}
else
{ 
 $qr="select * from products where category='$category'";
$row=Blogic::execute_query($qr);
}


if($row)
 { 
 if (mysql_num_rows($row) >0) 
{
              $response["success"] = 1;
              $response["product"] = array();
            while($result = mysql_fetch_array($row))
 {
 
            $product["Product Code"] = $result["ProductCode"];
            $product["Product Name"] = $result["Product Name"];
            $product["Original Price"] = $result["Original Price"];
            $product["Discount Price"] =$result["Discount Price"];
            $product["Description"] =$result["Description"];
            $product["Requriment"] =$result["Requriment"];
            $product["image"] =$result["image"];
         
         array_push($response["product"], $product);
  }
            // echoing JSON response
            echo json_encode($response);
        } 
         else {
          echo mysql_error();  
            // no product found
          $response["success"] = 0;
           $response["message"]="something went wrong";
            // echo no users JSON
            echo json_encode($response);
        }
        
 }
else
         { $response["success"] = 0;
            // echo no users JSON
          $response["message"]="No products in the list";
            echo json_encode($response);
}
?>