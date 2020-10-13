<?php

	date_default_timezone_set("Asia/Ho_Chi_Minh");

	$r = array("status"=>0, "msg" => "Có lỗi xảy ra!");
	$t = isset($_GET["t"]) ? $_GET["t"] : false;

	if($t!==false){
		switch($t){
			case "follow":
				$mail = isset($_GET["mail"]) ? $_GET["mail"] : false;
				$ip = isset($_GET["ip"]) ? $_GET["ip"] : false;
				$city = isset($_GET["city"]) ? $_GET["city"] : false;
				$region = isset($_GET["region"]) ? $_GET["region"] : false;
				$country = isset($_GET["country"]) ? $_GET["country"] : false;
				$timezone = isset($_GET["timezone"]) ? $_GET["timezone"] : false;
				$data = getTime()."|".$mail."|".$ip."|".$city."|".$region."|".$country."|".$timezone;
				if(appendFile("analysisCMS.txt", $data)){
					$r["status"] = 1;
					$r["msg"] = "Thêm thành công!";
				}else{
					$r["msg"] = "Thêm thất bại!";
				}
				break;
		}
	}

	die(json_encode($r));

	function appendFile($path, $data){
		$f = @fopen($path, "a+");
		if($f){
			$res = @fwrite($f, $data.PHP_EOL);
			@fclose($f);
			return $res;
		}
		return false;
	}

	function getTime(){
		return date('d-m-Y H:i:s');
	}

?>