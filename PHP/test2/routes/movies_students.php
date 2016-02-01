<?php


function getUsers() {

   $conn = getDB();
   $sql = "SELECT * FROM User";


   if (!$result = $conn->query($sql)) {
       die('There was an error running the query [' . $conn->error . ']\n');
   }

   $return_arr = array();

   while($row = $result->fetch_assoc()){
       // $row_array['UserID'] = $row['UserID'];
//        $row_array['UserPW'] = $row['UserPW'];
//        $row_array['Rating'] = $row['Rating'];
//        $row_array['Rating'] = $row['R'];
//        $row_array['url'] = $row['url'];

    	array_push($return_arr,$row);//_array);
   }

   echo json_encode($return_arr);

   $conn->close();
}

function getComment($mid) {

   $conn = getDB();
   $sql = "SELECT Comment.FPersonID, Comment.Description, Comment.CDate, User.Icon FROM Comment INNER JOIN User on Comment.FPersonID=User.UserID WHERE Comment.PersonID= '".$mid."'";
   //echo $sql;
 if (!$result = $conn->query($sql)) {
         die('There was an error running the query [' . $conn->error . ']\n');
    }
 
    $return_arr = array();
 
    while($row = $result->fetch_assoc()){

	array_push($return_arr,$row);//_array);
    } 
    echo json_encode($return_arr);
   $conn->close();
}

// Get the details of a movie


function Commend($id,$fid,$Des,$Rat) {
 	$conn = getDB();
 	$sqltemp2="INSERT INTO Comment(PersonID, FPersonID, Description) VALUES ('".$id."','".$fid."','".$Des."')";
 	if ($stmt = $conn->prepare($sqltemp2)){  		
          $stmt->execute();
 		  $stmt->close();
     }
 	//echo $sqltemp2;
 	$currentRating=0;
    $count=0;
    $currentrate=0;
 	if ($stmt = $conn->prepare("SELECT RatingCount,Rating 
                               FROM User 
	                           WHERE  UserID = ? ")) {

        // Bind parameters to the query 
        $stmt->bind_param("s", $id);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();
        
        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
 	    	$count=$row['RatingCount'];
 	    	$currentRating=$row['Rating'];
       		array_push($return_arr,$row);
            
        }
        $currentrate=($count*$currentRating+$Rat)/($count+1);
        $currentrate=round($currentrate,2);
		$stmt->close();
   }
   $count=$count+1;
   //echo "UPDATE User SET Rating= ".$currentrate." WHERE  UserID = '".$id."'";
     if ($stmt = $conn->prepare("UPDATE User SET Rating= ".$currentrate.", RatingCount= ".$count." WHERE  UserID = '".$id."'")){  		
          $stmt->execute();
 		  $stmt->close();
     }
   //echo json_encode($return_arr);
   $conn->close();
}
function getUserDetail($id)
{
   $conn = getDB();

   // Create a prepared statement 
   if ($stmt = $conn->prepare("SELECT * 
                               FROM User 
	                           WHERE  UserID = ? ")) {

        // Bind parameters to the query 
        $stmt->bind_param("s", $id);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();

        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
       		array_push($return_arr,$row);
            
        }
		$stmt->close();
   }
   
   echo json_encode($return_arr);
   $conn->close();
}

function Signup($id, $pw, $Des, $BDes, $Loc, $Icon) {
 	$conn = getDB();
 	$sqltemp2="INSERT INTO User(UserID, UserPW, Description, BrifeDP, Location, Icon) VALUES ('".$id."','".$pw."','".$Des."','".$BDes."','".$Loc."','".$Icon."')";
 	echo $sqltemp2;
 	$check="SELECT COUNT(DISTINCT(UserID)) AS Result FROM User WHERE UserID = '".$id."'";
 	$count=0;
 	if($stmt = $conn->prepare($check)) {
 		$stmt->execute();
 		$res = $stmt->get_result();
        
        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
 	    	$count=$row['Result'];
 		}
 		$stmt->close();
 	}
 	echo $count;
 	if($count==0){
 	if($stmt = $conn->prepare($sqltemp2)) {
 		$stmt->execute();
 		$stmt->close();
 		}
 		}
 	else
 		echo "  Can't signup";
 	$conn->close();
}
function UpdateInf($oid,$id, $pw, $Des, $BDes, $Loc, $Icon) {
 	$conn = getDB();
 	//echo $id." ".$pw." ".$Des." ".$BDes." ".$Loc." ".$Icon." ".$oid;
 	$sqltemp2="UPDATE User SET UserID= '".$id."', UserPW= '".$pw."',  Description= '".$Des."',  BrifeDP= '".$BDes."',  Location= '".$Loc."',  Icon= '".$Icon."' WHERE  UserID = '".$oid."'";//"UPDATE INTO User(UserID, UserPW, Description, BrifeDP, Location, Icon) VALUES ('".$id."','".$pw."','".$Des."','".$BDes."','".$Loc."','".$Icon."')";
 	//"UPDATE User SET Rating= ".$currentrate.", RatingCount= ".$count." WHERE  UserID = '".$id."'"
 	echo $sqltemp2;
 	$check="SELECT COUNT(DISTINCT(UserID)) AS Result FROM User WHERE UserID = '".$oid."'";
 	$count=0;
 	if($stmt = $conn->prepare($check)) {
 		$stmt->execute();
 		$res = $stmt->get_result();
        
        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
 	    	$count=$row['Result'];
 		}
 		$stmt->close();
 	}
 	if($oid==$id)
 		echo" ";
 	else
 	{
 		$sqltemp3="UPDATE Comment SET PersonID= '".$id."' WHERE PersonID='".$oid."'";
 		$sqltemp4="UPDATE Comment SET FPersonID= '".$id."' WHERE FPersonID='".$oid."'";
 		echo $sqltemp3;
 		if($stmt = $conn->prepare($sqltemp3)) {
 		$stmt->execute();
 		$stmt->close();
 		}
 		if($stmt = $conn->prepare($sqltemp4)) {
 		$stmt->execute();
 		$stmt->close();
 		}
 	}
 	echo $count;
 	if($count==0)
 		echo "  Can't Update";
 	else{
 	if($stmt = $conn->prepare($sqltemp2)) {
 		$stmt->execute();
 		$stmt->close();
 		}
 		}
 	$conn->close();
}
function Login($id, $pw)
{
   $conn = getDB();
	//echo "select distinct Result from ( select case when 1 = (SELECT 1 AS Result FROM User WHERE UserID = 'TEST' AND UserPW = 'TID') then @count := 1 else @count := 0 end Result from (select @count := 0) r ) a";
   	$temp="select distinct Result from ( select case when 1 = (SELECT 1 AS Result FROM User WHERE UserID = '".$id."' AND UserPW = '".$pw."') then @count := 1 else @count := 0 end Result from (select @count := 0) r ) a";
   	
   // Create a prepared statement 
   if ($stmt = $conn->prepare($temp)) {

        // Bind parameters to the query 
        //$stmt->bind_param("s", $id);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();

        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
       		array_push($return_arr,$row);
            
        }
		$stmt->close();
   }
   
   echo json_encode($return_arr);
   $conn->close();
}

function CheckName($id)
{
   $conn = getDB();
	//echo "select distinct Result from ( select case when 1 = (SELECT 1 AS Result FROM User WHERE UserID = 'TEST' AND UserPW = 'TID') then @count := 1 else @count := 0 end Result from (select @count := 0) r ) a";
   	$temp="select distinct Result from ( select case when 1 = (SELECT 1 AS Result FROM User WHERE UserID = '".$id."') then @count := 1 else @count := 0 end Result from (select @count := 0) r ) a";
   	
   // Create a prepared statement 
   if ($stmt = $conn->prepare($temp)) {

        // Bind parameters to the query 
        //$stmt->bind_param("s", $id);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();

        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
       		array_push($return_arr,$row);
            
        }
		$stmt->close();
   }
   
   echo json_encode($return_arr);
   $conn->close();
}

// Get the movies that are above certain rating (unsafe version)

?>


