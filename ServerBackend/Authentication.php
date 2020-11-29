<?php 
require_once dirname(__FILE__).'/DBConnect.php';

if($_POST['op']==1){
    
    createuser();
}
else if($_POST['op']==2){
    login();
}
else if($_POST['op']==3){
    fetch_food_details();
}
else if($_POST['op']==4){
    fetch_image();
}
else if($_POST['op']==5){
    fetch_items_by_restaurant();
}
else if($_POST['op']==7){
    change_details();
}
else{
    place_order();
}
function change_details(){
    $db=new DBConnect();
    $a=$db->connect();
    $na="dddddddd";
    //UPDATE `user` SET `NAME`="QQWww",`PASSWORD`="dddddd" WHERE NUMBER=666
    $stmt=$a->prepare("UPDATE user SET NAME=?,PASSWORD=? WHERE NUMBER=?");
    $stmt->bind_param("sss",$_POST['name'],$_POST['password'],$_POST['number']);
    $stmt->execute();

}
function place_order(){
    $db=new DBConnect();
    $a=$db->connect();
    
    foreach($_POST as $key => $values){
        //$b="4";
        //$db=new DBConnect();
        //$a=$db->connect();
        if($key!='op'and $key!='number'){
            $stmt=$a->prepare("INSERT INTO food_order(food_id,quantity,number) VALUES(?,?,?)");
            $stmt->bind_param("sss",$key,$values,$_POST['number']);
            $stmt->execute();
        }
    }
    $response=array();
    $response['error']=false;
    $response['message']="successful";
    echo json_encode($response);
}
//create the user
function createuser(){
    $db=new DBConnect();
    $a=$db->connect();
    if(!numberexists($_POST["number"])){
        $stmt=$a->prepare("INSERT INTO user(number,name,password) VALUES(?,?,?)");
        $stmt->bind_param("sss",$_POST['number'],$_POST['name'],$_POST['password']);
        if($stmt->execute()){
            $response=array();
            $response['error']=false;
            $response['message']='Successful';
            echo json_encode($response);
        }
        else{
            $response=array();
            $response['error']=true;
            $response['message']='Could not execute';
            echo json_encode($response);
        }
    }
    else{
        $response=array();
        $response['error']=true;
        $response['message']='number already exists';
        echo json_encode($response);
    }
}
//check whether the number exists or not
function numberexists($number){
    $db=new DBConnect();
    $a=$db->connect();
    $stmt=$a->prepare("SELECT * FROM user where number=?");
    $stmt->bind_param("s",$_POST['number']);
    $stmt->execute();
    $stmt->store_result();
    return $stmt->num_rows>0; 
}
//login a user
function login(){
    $db=new DBConnect();
    $a=$db->connect();
    $stmt=$a->prepare("SELECT NAME FROM user where number=? and password=?");
    $stmt->bind_param("ss",$_POST['number'],$_POST['password']);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($name);
    
    if($stmt->num_rows>0){
        while($stmt->fetch()){
            $Name=$name;
        }
        $response=array();
        $response['error']=true;
        $response['message']='Successful';
        $response['name']=$Name;
        echo json_encode($response);
    }
    else{
        $response=array();
        $response['error']=false;
        $response['message']='Unsuccessful';
        echo json_encode($response);
    }
}
//fetch the list of food details
function fetch_food_details(){
    $db=new DBConnect();
    $a=$db->connect();
   
    $stmt=$a->prepare("SELECT * FROM food ,restaurant where food.restaurant_id=restaurant.restaurant_id and (food.cuisine=? or food.title=?) and (restaurant.name=? or restaurant.city=? or restaurant.location=?)");
    $stmt->bind_param("sssss",$_POST['cuisine'],$_POST['cuisine'],$_POST['place'],$_POST['place'],$_POST['place']);
    $stmt->execute();
    
    $stmt->bind_result($food_id,$title,$restaurant_id,$cuisine,$price,$discount,$image,$restaurantid,$name,$location,$city);
    $responses=array();
    while($stmt->fetch()){
        $response =array();
        $response['food_id']=$food_id;
        $response['title']=$title;
        $response['restaurant_id']=$restaurant_id;
        $response['cuisine']=$cuisine;
        $response['price']=$price;
        $response['discount']=$discount;
        $response['image']=$image;
        $response['name']=$name;
        $response['location']=$location;
        $response['city']=$city; 
        array_push($responses,$response);
    }
    $response_data['food']=$responses;
    echo json_encode($response_data);
}
function fetch_items_by_restaurant(){
    $db=new DBConnect();
    $a=$db->connect();
    $stmt=$a->prepare("SELECT * FROM food  where restaurant_id=?");
    $stmt->bind_param("s",$_POST['restaurant_id']);
    $stmt->execute();
    $stmt->bind_result($food_id,$title,$restaurant_id,$cuisine,$price,$discount,$image,);
    $responses=array();
    while($stmt->fetch()){
        $response =array();
        $response['food_id']=$food_id;
        $response['title']=$title;
        $response['restaurant_id']=$restaurant_id;
        $response['cuisine']=$cuisine;
        $response['price']=$price;
        $response['discount']=$discount;
        $response['image']=$image;
        array_push($responses,$response);
    }
    $response_data['food']=$responses;
    echo json_encode($response_data); 
}

    