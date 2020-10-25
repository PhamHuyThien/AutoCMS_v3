<?php
session_start();
date_default_timezone_set("Asia/Ho_Chi_Minh");
//------------------> config DB <------------------------//
$dbhost ="localhost";
$dbname ="fplautocms_analysis";
$dbuser ="thiendepzaii";
$dbpass	="thien1107";
//--------------------------------------------------------//

// $dbhost ="localhost";
// $dbname ="mfbbuzz_cmspoly";
// $dbuser ="mfbbuzz_thiendz";
// $dbpass	="thien1107";

try{
	$conn = new PDO("mysql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);
	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}catch(PDOException $e){
	die($e->getMessage());
}


?>