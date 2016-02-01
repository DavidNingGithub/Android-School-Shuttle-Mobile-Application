<?php

function getMovies() {

   $conn = getDB();
   $sql = "SELECT * FROM Movies ORDER BY name ASC";


   if (!$result = $conn->query($sql)) {
       die('There was an error running the query [' . $conn->error . ']\n');
   }

   $return_arr = array();

   while($row = $result->fetch_assoc()){
       $row_array['id'] = $row['id'];
       $row_array['name'] = $row['name'];
       $row_array['description'] = $row['description'];
       $row_array['rating'] = $row['rating'];
       $row_array['url'] = $row['url'];

       array_push($return_arr,$row_array);
   }

   echo json_encode($return_arr);

   $conn->close();
}


// Get the details of a movie
function getMovieDetail($id)
{
   $conn = getDB();

   // Create a prepared statement 
   if ($stmt = $conn->prepare("SELECT id, name, year, stars,director,length,description, rating, url 
                               FROM Movies 
	                           WHERE  id = ? ")) {

        // Bind parameters to the query 
        $stmt->bind_param("s", $id);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();

        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
 	    	//echo $row['year'];//new create;
 	    	$row['year']= "".$row['year'];
   	   		$row_array['id'] = $row['id'];
       		$row_array['name'] = $row['name'];
       		$row_array['year']=$row['year'];
       		$row_array['stars']=$row['stars'];
       		$row_array['director']=$row['director'];
       		$row_array['length']=$row['length'];
       		$row_array['description'] = $row['description'];
       		$row_array['rating'] = $row['rating'];
       		$row_array['url'] = $row['url'];
       		array_push($return_arr,$row_array);
            
        }
		$stmt->close();
   }

   echo json_encode($return_arr);
   $conn->close();
}

// Get the movies that are above certain rating (unsafe version)
function getMoviesAboveRating_NotSafe($rating)
{
   $conn = getDB();

   // Don't do this, this is not safe against SQL injection attack
   $sql = "SELECT id, name, description, rating, url
           FROM Movies 
           WHERE rating >= '$rating'
           ORDER BY rating DESC";
	
   if (!$result = $conn->query($sql)) {
       die('There was an error running the query [' . $conn->error . ']\n');
   }

   $return_arr = array();
   while($row = $result->fetch_assoc()){
   	   $row_array['id'] = $row['id'];
       $row_array['name'] = $row['name'];
       $row_array['description'] = $row['description'];
       $row_array['rating'] = $row['rating'];
       $row_array['url'] = $row['url'];
       array_push($return_arr,$row_array);
   }

   echo json_encode($return_arr);

   $conn->close();	
}

// Get the movies that are above certain rating (safe version)
function getMoviesAboveRating($rating)
{
   $conn = getDB();

   // Create a prepared statement 
   if ($stmt = $conn->prepare("SELECT id, name, description, rating, url 
                               FROM Movies 
	                           WHERE  round(rating,1) >= round(?,1) 
                               ORDER BY rating DESC")) {

        // Bind parameters to the query 
        $stmt->bind_param("s", $rating);

        // Execute query
        $stmt->execute();

	    // Get query results, it may contain multiple rows
	    $res = $stmt->get_result();

        $return_arr = array();
 	    while ($row = $res->fetch_assoc()){
            array_push($return_arr, $row);
        }
		$stmt->close();
   }

   echo json_encode($return_arr);
   $conn->close();
}
?>


