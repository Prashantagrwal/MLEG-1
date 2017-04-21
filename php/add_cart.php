<?php

require_once('connect_add.php');

$useremailid=$_GET['UserEmailId'];
$productCode=$_GET['productCode'];
$check=$_GET['check'];

$response=array();
if($check=="0")
{
$qr="insert into cart values('null','$useremailid','$productCode')";
$result=Blogic::execute_query($qr);

if($result)
{
    echo "Added to cart";
}
else
echo mysql_error();
}
else if($check=="1")
{
    $qr="delete from cart where productcode='$productCode' and UserEmailId='$useremailid'";
    $result=Blogic::execute_query($qr);
    if($result)
    {
        echo "Removed from cart";
    }
    else{
        mysql_error();
    }
}
else if($check=="2")
{
    $qr="select * from cart where Productcode='$productCode' and UserEmailId='$useremailid'";
    $qr1="select * from images_add where product_code in ('$productCode')";
    $result=Blogic::execute_query($qr);
    if($result)
    {
    if(mysql_affected_rows())
    
    { 
        $response["check_cart"]="true";}
    else
    { 
        $response["check_cart"]="false";}
    }
    $re=Blogic::execute_query($qr1);
     if($re)
{   
  if (mysql_num_rows($re) >0) 
{
           
           $img=array();
           $count=0;
            while($result = mysql_fetch_array($re))
 {            $count+=1;
            $img[] = $result["image_name"];            
 }
       $response["images"]=$img;
       $response["count"]=$count;
            echo json_encode($response);
 } 
}
}
else
{
    mysql_error();
}
?>