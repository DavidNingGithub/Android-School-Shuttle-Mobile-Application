<?php
/**
 * Step 1: Require the Slim Framework
 *
 * If you are not using Composer, you need to require the
 * Slim Framework and register its PSR-0 autoloader.
 *
 * If you are using Composer, you can skip this step.
 */
require 'Slim/Slim.php';
require 'routes/db.php';
require 'routes/movies_students.php';
\Slim\Slim::registerAutoloader();

/**
 * Step 2: Instantiate a Slim application
 *
 * This example instantiates a Slim application using
 * its default settings. However, you will usually configure
 * your Slim application now by passing an associative array
 * of setting names and values into the application constructor.
 */
$app = new \Slim\Slim();

/**
 * Step 3: Define the Slim application routes
 *
 * Here we define several Slim application routes that respond
 * to appropriate HTTP request methods. In this example, the second
 * argument for `Slim::get`, `Slim::post`, `Slim::put`, `Slim::patch`, and `Slim::delete`
 * is an anonymous function.
 */

// GET route
$app->get(
    '/',
    function () {
        echo "Hello1 world";
    }
);


$app->get(
	'/Users/',
	function(){
		getUsers();
	}
);

$app->get(
	'/Users/id/:mid',
	function($mid){
		getUserDetail($mid);
	}
);

$app->get(
    '/Signup/id/:id/pw/:pw/Des/:Des/BDes/:BDes/Loc/:Loc/Icon/:Icon',
    function () {
		$uri = $_SERVER['REQUEST_URI'];
		$arr_items = split('/', $uri);
		$Icon=$arr_items[count($arr_items) - 1];
		$Loc = $arr_items[count($arr_items) - 3];
		$BDes = $arr_items[count($arr_items) - 5];
		$Des = $arr_items[count($arr_items) - 7];
		$pw = $arr_items[count($arr_items) - 9];
		$id = $arr_items[count($arr_items) - 11];
		//echo $id." ".$pw." ".$Des." ".$BDes." ".$Loc." ".$Icon;
        Signup($id, $pw, $Des, $BDes, $Loc, $Icon);
    }
);
$app->get(
    '/Update/oid/:oid/id/:id/pw/:pw/Des/:Des/BDes/:BDes/Loc/:Loc/Icon/:Icon',
    function () {
		$uri = $_SERVER['REQUEST_URI'];
		$arr_items = split('/', $uri);
		$Icon=$arr_items[count($arr_items) - 1];
		$Loc = $arr_items[count($arr_items) - 3];
		$BDes = $arr_items[count($arr_items) - 5];
		$Des = $arr_items[count($arr_items) - 7];
		$pw = $arr_items[count($arr_items) - 9];
		$id = $arr_items[count($arr_items) - 11];
		$oid = $arr_items[count($arr_items) - 13];
		//echo $id." ".$pw." ".$Des." ".$BDes." ".$Loc." ".$Icon." ".$oid;
        UpdateInf($oid,$id, $pw, $Des, $BDes, $Loc, $Icon);
    }
);

$app->get(
	'/Comment/id/:mid/fid/:fid/Des/:Des/Rating/:rat',
	function(){
	$uri = $_SERVER['REQUEST_URI'];
		$arr_items = split('/', $uri);
		$Rat=$arr_items[count($arr_items) - 1];
		$Des = $arr_items[count($arr_items) - 3];
		$fid = $arr_items[count($arr_items) - 5];
		$id=$arr_items[count($arr_items) - 7];
		echo $id." ".$fid." ".$Des." ".$Rat;
		Commend($id,$fid,$Des,$Rat);
	}
);
$app->get(
	'/Comment/id/:mid',
	function($mid){
	//echo $mid;
	getComment($mid);
	}
);

$app->get(
	'/Login/id/:mid/pw/:pw',
	function(){
	$uri = $_SERVER['REQUEST_URI'];
	$arr_items = split('/', $uri);
	$pw=$arr_items[count($arr_items) - 1];
	$id = $arr_items[count($arr_items) - 3];
	//echo $id." ".$pw;
	Login($id,$pw);
	}
);
$app->get(
	'/Check/id/:mid',
	function(){
	$uri = $_SERVER['REQUEST_URI'];
	$arr_items = split('/', $uri);
	$id=$arr_items[count($arr_items) - 1];
	//$id = $arr_items[count($arr_items) - 3];
	//echo $id;
	CheckName($id);
	}
);

// POST route
$app->post(
    '/post',
    function () {
        echo 'This is a POST route';
    }
);

// PUT route
$app->put(
    '/put',
    function () {
        echo 'This is a PUT route';
    }
);

// PATCH route
$app->patch('/patch', function () {
    echo 'This is a PATCH route';
});

// DELETE route
$app->delete(
    '/delete',
    function () {
        echo 'This is a DELETE route';
    }
);

/**
 * Step 4: Run the Slim application
 *
 * This method should be called last. This executes the Slim application
 * and returns the HTTP response to the HTTP client.
 */
$app->run();
