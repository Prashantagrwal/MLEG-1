<?php
require_once('connect_add.php');

$user=$_GET['UserEmailId'];
$qr="select * from orders where UserEmailId='$user'";
$row=Blogic::execute_query($qr);
$response=array();
if($row)
 {
   
 if (mysql_num_rows($row) >0) 
{
              $response["success"] = 1;
              $response["product"] = array();
            while($result = mysql_fetch_array($row))
 {
            $code=$result["Product code"];
            
            $product["code"]=$result["Product code"];
            $product["name"] = $result["Subject"];
            $product["quantity"]=$result["Quantity"];    
            
            $qr2= "select * from products where ProductCode='$code'";
            $res=Blogic::execute_query($qr2);
            if($res)
            {
               while($result1 = mysql_fetch_array($res))
               {
                $product["O_Price"]=$result1["Original Price"];
                $product["D_Price"]=$result1["Discount Price"];
            }}
            
            $product["call"] = $result["call"];
            $product["design"] =$result["design"];
            $product["delivery"] =$result["delivery"];
            $product["thankyou"] =$result["thankyou"];
            $product["CustomerName"]=$result["Name"];
            $product["phone"]=$result["Phone"];
            $product["email"]=$result["Email"];
            $product["address"]=$result["Address"];
            
        
        
         array_push($response["product"], $product);
         
  }
            // echoing JSON response
            echo json_encode($response);
        } 
        else
{
              $response["success"] = 0;
              echo json_encode($response);
}
}

?>