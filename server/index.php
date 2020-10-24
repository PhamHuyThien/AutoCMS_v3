<?php

	include_once "view/header.html";

	$t = isset($_GET["t"]) ? $_GET["t"] : false;
	
	$all_view = scandir("view");	

	if($t === false){
		include_once "view/index.html";
	}
	else if(array_search($t.".html", $all_view)){
		include_once "view/".$t.".html";
	}else{
		include_once "view/404.html";
	}


	include_once "view/footer.html";
	

?>