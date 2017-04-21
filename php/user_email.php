<?php

require_once('connect_add.php');

$email=$_GET['email'];
$token=$_GET['token'];
$response=array();
$check=false;
$value=0;

$qr="select * from useremail where email='$email'";

$result=Blogic::execute_query($qr);
if(mysql_affected_rows())
{
    if($result)
    {
        
    while($row=mysql_fetch_array($result))
    {
        if($row["token"]==$token)
        {
        $response["success"]=1;
        $response["id"]=$row["id"];
        }
        else
        {
             $value=$row[0];
             $qr2="delete from useremail where email='$row[1]' and token='$row[2]'";
             $result2=Blogic::execute_query($qr2);
             $qr1="insert into useremail values('$value','$email','$token')";
             $result1=Blogic::execute_query($qr1);
            $check=true;
            }
    }
    
    }
    else
    {
        $response["success"]=0;
        $response["message"]="Error";
    }
}
else
{
       $qr1="insert into useremail values('null','$email','$token')";
       $result1=Blogic::execute_query($qr1);
    }
    
        $result3=Blogic::execute_query($qr);   
        if($result3)
        {
    while($row=mysql_fetch_array($result3))
    {
        $response["success"]=1;
        $response["id"]=$row["id"];
       /*  if($check){
         $qr3="select * from orders where UserEmailId='$value'";
         
         $res1=Blogic::execute_query($qr3);
      
         if($res1)
         {
            if(mysql_affected_rows()>0)
            {
              while($row1=mysql_fetch_array($res1))
             {
                  $qr3="update orders set UserEmailId='$row[0]' where Id='$row1[0]'";
                  $res1=Blogic::execute_query($qr5);
             }    
            }
         }
         $qr3="select * from enquiry where UserEmailId='$value'";
           $res1=Blogic::execute_query($qr3);
         if($res1)
         {
            if(mysql_affected_rows()>0)
            {
              while($row1=mysql_fetch_array($res1))
             {
                  $qr3="update enquiry set UserEmailId='$row[0]' where id='$row1[0]'";
                  $res1=Blogic::execute_query($qr5);
             }    
            }
         }
    $qr3="select * from cart where UserEmailId='$value'";
           $res1=Blogic::execute_query($qr3);
         if($res1)
         {
            if(mysql_affected_rows()>0)
            {
              while($row1=mysql_fetch_array($res1))
             {
                  $qr3="update cart set UserEmailId='$row[0]' where id='$row1[0]'";
                  $res1=Blogic::execute_query($qr5);
             }    
            }
         }
    }*/
    }
    
    /*else
    {
        $response["success"]=0;
        $response["message"]="No Value";
    }*/
    }

    else
    {
        $response["success"]=0;
        $response["message"]="Not Inserted";
    } 


 echo json_encode($response);
?>