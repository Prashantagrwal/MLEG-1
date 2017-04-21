  <?php

require_once('connect_add.php');



   $Amount = $_GET['TotalAmount'];
   $Advance = $_GET['Advance'];
   $Left=$_GET['Left'];
   $ContactId=$_GET['ContactId'];
   $Confirm=$_GET['Confirm'];

   $qr="insert into amountinfo values(NULL,'$Amount','$Advance','$Left')";
 //$qr="ALTER TABLE confirminfo AUTO_INCREMENT = 0";
   
$result = Blogic::execute_query($qr);
   if($result)
   { 
  
  $qr1="SELECT AmountId FROM amountinfo WHERE AmountId =(SELECT COUNT(AmountId) FROM amountinfo)";
  $qr3="UPDATE contactinfo SET Confirm='$Confirm' WHERE ContactId='$ContactId'";
  $res2=Blogic::execute_query($qr3);
   $res = Blogic::execute_query($qr1);
   if($res)
   
   {
     if (mysql_num_rows($res) >0) 
{
 
    $res = mysql_fetch_array($res);
    $AmountId=$res["AmountId"];
    }
    else
    { mysql_error();
        return;
    }
    
    $qr2="insert into confirminfo values(NULL,'$ContactId','$AmountId')";
     $resl=Blogic::execute_query($qr2);
    
     if($resl)
    
        echo "You have Registed Successfully";  
    
        else
   {
    mysql_error();
    echo " Registed unSuccessfully"; 
   }     
    
   }
  
   else
   {
    mysql_error();
    
    return;
   }
   }
   else
   {
    mysql_error();
   
    retutn;
   }
     ?>