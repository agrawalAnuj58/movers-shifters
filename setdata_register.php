<?php
$con=mysqli_connect("localhost","id6424619_sahilpratap7200","sahil173573",
"id6424619_android")or die("connection not successfull");

mysqli_select_db($con,"id6424619_android")or die("database not found");


if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['phoneNo']) && isset($_POST['gender']) && isset($_POST['password']))
{

$name=$_POST['name'];
$email=$_POST['email'];
$phoneNo=$_POST['phoneNo'];
$gender=$_POST['gender'];
$password=$_POST['password'];

$qry="insert into register_data (name,email,phoneNo,gender,password) values('$name','$email','$phoneNo','$gender','$password')";
mysqli_query($con,$qry)or die("Query Problem");
}
else
{
echo "waiting for data...";

}
?>