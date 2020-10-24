<?php
	include "../config/config.php";

	$sql_addUser = "INSERT INTO user(username, ip, city, region, country, timezone, time) VALUES('%s', '%s', '%s', '%s', '%s', '%s', %d)";
	//
	$sql_analysisTotalRecord = "SELECT COUNT(id) AS count FROM user"; // tổng bản ghi
	$sql_analysisTotalUser = "SELECT COUNT(DISTINCT username) AS count FROM user"; //tổng người sử dụng
	$sql_analysisNewUser = "SELECT username, time FROM user ORDER BY time DESC"; // người dùng mới nhất
	$sql_analysisMostUser = "SELECT username, COUNT(username) AS count FROM user GROUP BY username ORDER BY count DESC LIMIT 1"; //người dùng nhiều nhất
	$sql_analysisMostRegion = "SELECT region, COUNT(region) AS count, country FROM user GROUP BY region ORDER BY count DESC LIMIT 1"; //thành phố sử dụng nhiều nhất

	function _queryAnalysis(){
		global $sql_analysisTotalRecord, $sql_analysisTotalUser, $sql_analysisNewUser, $sql_analysisMostUser, $sql_analysisMostRegion;
		return array(
			"total_record" => __query_fetch($sql_analysisTotalRecord),
			"total_user" => __query_fetch($sql_analysisTotalUser),
			"new_user" => __query_fetch($sql_analysisNewUser),
			"most_user" => __query_fetch($sql_analysisMostUser),
			"most_region" => __query_fetch($sql_analysisMostRegion)
		);
	}

	function _queryAddUser($username, $ip, $city, $region, $country, $timezone){
		global $sql_addUser;
		$sql = sprintf($sql_addUser, $username, $ip, $city, $region, $country, $timezone, time());
		return __query($sql);
	}




	//===========================================================================================
	function __query($sql){
		global $conn; 
		return $conn->query($sql);
	}

	function __query_fetch($sql){
		global $conn;
		return $conn->query($sql)->fetch(PDO::FETCH_ASSOC);
	}

	function __query_fetchAll($sql){
		global $conn;
		return $conn->query($sql)->fetchAll(PDO::FETCH_ASSOC);
	}

?>