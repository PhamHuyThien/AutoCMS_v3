<?php
	include_once "query_database.php";


	$t = isset($_POST["t"]) ? $_POST["t"] : false;
	$r = array("status" => 0, "msg" => "Command not found!");

	switch($t){
		//==============================================================================
		case "new-uses":
			$user = isset($_POST["user"]) ? $_POST["user"] : false;
			$ip = isset($_POST["ip"]) ? $_POST["ip"] : false;
			$city = isset($_POST["city"]) ? $_POST["city"] : false;
			$region = isset($_POST["region"]) ? $_POST["region"] : false;
			$country = isset($_POST["country"]) ? $_POST["country"] : false;
			$timezone = isset($_POST["timezone"]) ? $_POST["timezone"] : false;
			if(_checkMissing(array($user, $ip, $region, $country, $timezone))){
				if(_queryAddUser($user, $ip, $city, $region, $country, $timezone)){
					$r["status"] = 1;
					$r["msg"] = "Add $user success!";
				}else{
					$r["msg"] = "Add $user error!";
				}
			}else{
				$r["msg"] = "Missing parameter!";
			}
			break;
		//==============================================================================
		case "analysis":
			$r["status"] = 1;
			$r["msg"] = "query analysis success";
			$r["data"] = _queryAnalysis();
			break;
	}	

	die(json_encode($r));


	function _checkMissing($arrVariable){
		if(!is_array($arrVariable)){
			return false;
		}
		for($i=0; $i<count($arrVariable); $i++){
			if($arrVariable[$i] === false){
				return false;
			}
		}
		return true;
	}
?> 