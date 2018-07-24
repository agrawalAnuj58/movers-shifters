
    <?php
    define('HOST','localhost');
    define('USER','id6424619_sahilpratap7200');
    define('PASS','sahil173573');
    define('DB','id6424619_android');
     
    $con = mysqli_connect(HOST,USER,PASS,DB);
      
    
    if(isset($_POST['email']))
    {  
    
    $email = $_POST['email'];
    
    $query = "select * from register_data where email = '$email'";
 
    $res = mysqli_query($con,$query);
     
    $result = array();
     
    while($row = mysqli_fetch_array($res))
{
    array_push($result,
    array('id'=>$row[0],
    'userName'=>$row[1],
    'email'=>$row[2],
    'password'=>$row[3]
    ));
    }
    echo json_encode(array("result"=>$result));
}
    mysqli_close($con);
    ?> 
	
	
	