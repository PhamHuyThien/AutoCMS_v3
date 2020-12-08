<?php
	include_once "query_database.php";


	$t = isset($_POST["t"]) ? $_POST["t"] : false;
	$r = array("status" => 0, "msg" => "Command not found!");

	switch($t){
		case "application":
		case "get-ver-new":
		case "get-open":
		case "get-title":
			$arrIni = parse_ini_file("../config/setting.ini");
			if($t=="application"){
				$r["msg"] = "get setting application ok!";
				$r["data"] = $arrIni;
			}else if($t=="get-ver-new"){
				$r["msg"] = "get version new ok!";
				$r["data"]["ver_new"] = $arrIni["application"]["ver_new"]; 
 			}else if($t=="get-open"){
 				$r["msg"] = "get open ok!";
 				$r["data"]["open"] = $arrIni["application"]["open"];
 			}else if($t=="get-title"){
				$r["msg"] = "get title ok!";
				$r["data"]["title"] = $arrIni["application"]["title"];
			}
 			$r["status"] = 1;
 			break;

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
			$r["msg"] = "query analysis success!";
			$r["data"] = _queryAnalysis();
			break;
		//==============================================================================
		case "all-record":
			$r["status"] = 1;
			$r["msg"] = "query get all record success!";
			$r["data"] = _queryAllRecord();
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