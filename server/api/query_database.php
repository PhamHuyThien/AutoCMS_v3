<?php
	include "../config/config.php";

	$sql_addUser = "INSERT INTO user(username, ip, city, region, country, timezone, time) VALUES('%s', '%s', '%s', '%s', '%s', '%s', %d)";
	//
	$sql_analysisTotalRecord = "SELECT COUNT(id) AS count FROM user"; // tổng bản ghi
	$sql_analysisTotalUser = "SELECT COUNT(DISTINCT username) AS count FROM user"; //tổng người sử dụng
	$sql_analysisNewUser = "SELECT username, time FROM user ORDER BY time DESC"; // người dùng mới nhất
	$sql_analysisMostUser = "SELECT username, COUNT(username) AS count FROM user GROUP BY username ORDER BY count DESC LIMIT 1"; //người dùng nhiều nhất
	$sql_analysisMostRegion = "SELECT region, COUNT(region) AS count, country FROM user GROUP BY region ORDER BY count DESC LIMIT 1"; //thành phố sử dụng nhiều nhất
	//
	$sql_allRecord = "SELECT * FROM user";
	//
	$__sSQLGetCourse = "SELECT * FROM course WHERE id='%s';";
	$__sSQLAddCourse = "INSERT INTO course VALUES('%s', %d, 1, %d, %d);";
	$__sSQLUpdateCourse = "UPDATE course SET total_quiz=%d, safety=%d, time_update=%d WHERE id='%s';";
	
	function _queryUpdateCourse($__sIdCourse, $__nTotalQuiz){
		global $__sSQLUpdateCourse;
		$__sCourses = _queryGetCourse($__sIdCourse);
		if($__sCourses==false){
			return false;
		}
		$__nServerTotalQuiz = $__sCourses["total_quiz"];
		if($__nTotalQuiz>=$__nServerTotalQuiz){
			$__sSQL = sprintf($__sSQLUpdateCourse, $__nTotalQuiz, $__sCourses["safety"]+1, time(), $__sIdCourse);
			return __query($__sSQL);
		}
		return true;
	}
	
	function _queryAddCourse($__sIdCourse, $__nTotalQuiz){
		global $__sSQLAddCourse;
		$__nCurrentTime = time();
		$__sSQL = sprintf($__sSQLAddCourse, $__sIdCourse, $__nTotalQuiz, $__nCurrentTime, $__nCurrentTime);
		return __query($__sSQL);
	}
	

	function _queryGetCourse($__sIdCourse){
		global $__sSQLGetCourse;
		$__sSQL = sprintf($__sSQLGetCourse, $__sIdCourse);
		return __query_fetch($__sSQL);
	}
	
	function _queryAllRecord(){
		global $sql_allRecord;
		return __query_fetchAll($sql_allRecord);
	}

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