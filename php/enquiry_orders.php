<?php

require_once('connect_add.php');

if(isset($_GET["check"]))
{
    $check=$_GET["check"];
    if($check=="0") 
     {  $Name=$_GET["Name"];$Phone=$_GET["Phone"];$Email=$_GET["Email"];$Subject=$_GET["Subject"];
        $user=$_GET["userid"]; $code=$_GET['code'];
        $qr="select * from enquiry where UserEmailId='$user' and Productcode='$code'";
        
        $result=Blogic::execute_query($qr);
      
            if(mysql_affected_rows())
            {
            echo "You have already registered for this product";
            }                                                                                                                         
         else
        {
            $qr="insert into enquiry values ('null','$user','$Name','$Phone','$Email','$Subject','$code')";
            $result=Blogic::execute_query($qr);
            if($result){
            if(mysql_affected_rows())
            {
                echo "We will contact you As soon As possible";
            }
            }
        }
    }
    else if($check=="1")
    { $Name=$_GET["Name"];$Phone=$_GET["Phone"];$Email=$_GET["Email"];$Subject=$_GET["Subject"];$quantity=$_GET['Quantity'];
       $Address=$_GET['Address'];$user=$_GET["userid"];$code=$_GET['code'];
         $qr="insert into orders values ('null','$user','$Name','$Phone','$Email','$code','$Subject','$quantity','$Address',
         'no','no','no','no')";
            $result=Blogic::execute_query($qr);
            if($result)
            {
                echo "Your Order has been successfully placed check my orders for more details";
            }
    }
}
else
mysql_error();
?>