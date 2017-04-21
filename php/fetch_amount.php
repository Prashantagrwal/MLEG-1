  <?php

require_once('connect_add.php');

$response=array();

$ContactId=$_GET['ContactId'];
$qr="select AmountId from confirminfo where ContactId='$ContactId'";
$result=Blogic::execute_query($qr);

if($result)
{
    if (mysql_num_rows($result) >0) 
          {
            $result = mysql_fetch_array($result);
           $AmountId=$result["AmountId"];
    }
    $qr1="select * from amountinfo where AmountId='$AmountId'";
    $res=Blogic::execute_query($qr1);
    if($res)
    {
        if (mysql_num_rows($res) >0) 
          {
            $res = mysql_fetch_array($res);
            
            $product["id"]=$res["AmountId"];
            $product["TotalAmount"] = $res["TotalAmount"];
            $product["Advance"] = $res["Advance"];
            $product["Paid"] = $res["Due left"];
            
            // success
            $response["success"] = 1;
 
            // user node
            $response["product"] = array();
            array_push($response["product"], $product);
            // echoing JSON response
            echo json_encode($response);
    }
    else
    {
        mysql_error();
        $response["success"] = 0;
        echo json_encode($response);
    }
    }
    else
    sql_error();
}
else
{
    mysql_error();
    echo "Nothing is their";
}
        
     ?>