<?php
	session_start();
	
	//Check if user is logged in, if not forward to index.php
	if($_SESSION['loggedIn'] != 1) {
		header("Location: index.php");
		exit;
	}
	
	//Check if ID is set
	if(empty($_REQUEST['id'])) {
		header("Location: index.php");
		exit;
	} else {
		$webcamID = $_REQUEST['id'];
	}
	
	//Check if this webcam belongs to the user
	$url = $_SERVER['HTTP_HOST'].dirname($_SERVER['REQUEST_URI'])."/api/index.php";
	
	$data = array(
		"tag" => "get_webcams_from_user",
		"uid" => $_SESSION['uid']
	);
	
	//open connection
	$ch = curl_init();
	
	curl_setopt($ch, CURLOPT_POST, sizeof($data));
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_URL, $url);

	//execute post
	$result = curl_exec($ch);
	
	//Connection gets closed below!!
	//decode JSON String
	$result = (json_decode($result, true));
	

	$checkIDs = $result["webcamID"];
	
	$belongsToUser = false;
	foreach($checkIDs AS $id) {
		if($id == $webcamID) {
			$belongsToUser = true;
			break;
		}
	}
	
	if($belongsToUser == false) {
		header("Location: index.php");
		exit;
	}
	
	//Get Webcam Infos
	$data2 = array(
		"tag" => "getWebcamInfos",
		"webcamID" => $webcamID
	);
	
						
	curl_setopt($ch, CURLOPT_POST, sizeof($data2));
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data2);

	//execute post
	$result2 = curl_exec($ch);
	
	//decode JSON String
	$result2 = (json_decode($result2, true));
	
	//close connection
	curl_close($ch);
	
	
	
	/*
	 * DATE AND TIME HANDLING
	*/
	$directory = "api/registratedUserhome/".$_SESSION['email']."/$webcamID";
	$files = @scandir($directory, SCANDIR_SORT_DESCENDING);
	$start_of_cut = 3+strlen($webcamID)+1; //Bsp.: cam5_228383828.jpg => 228383828.jpg
	
	if(isset($_REQUEST['date'])) {
		$date_active = $_REQUEST['date'];
	} else {
		$timestamp = substr($files[0], $start_of_cut, -4);
		$date_active = date("dFY", $timestamp); //Value-Wert generieren//get date of the youngest file
	}

	$dates = array();
	$times = array();
	$date_before = null;
	
	foreach($files AS $file) {
		$timestamp = substr($file, $start_of_cut, -4); //Bsp: 228383828.jpg => 228383828
		if(strlen($timestamp) == 10) {
			$date = date("d. F Y", $timestamp); //Datum generieren, z.B. 18. November 2015
			if($date != $date_before) { //Wenn datum noch nicht vorhanden, dem Dropdown hinzufügen
				array_push($dates, $timestamp);
				$date_before = $date;
			} 
			if(date("dFY",$timestamp) == $date_active) { //If the date is the same as the active date, add the timestamp to an array which is processed below
				array_push($times, $timestamp);
			}

		}
	}

?>

<!doctype html>
<html class="no-js" lang="de">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Webcam | Secure@Home</title>
    <link rel="stylesheet" href="css/main.css" />
    <script src="js/vendor/modernizr.js"></script>
	<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="img/favicon.ico" type="image/x-icon">
	<script type="text/javascript">

		function changeDate(value) {
			var url = window.location;
			window.location.href = url + "&date=" + value;
		}
	
	   function changePicture(value) {
		  document.getElementById("webcam_img").src = value;
	   }
	</script>
  </head>
  <body>
    <div id="header" class="row">
		<div class="header_logo columns">
			<a href="index.php"><img src="img/header_logo_r.png"/></a>
		</div>
		<div class="header_text columns">
			<div class="topline"><a href="index.php">Secure@Home</a></div>
			<div class="subline">Use your Smartphone as a webcam</div>
		</div>
	</div>
	<div id="mainnavi">
		<nav class="top-bar" data-topbar role="navigation">
			<ul class="title-area">
				<li class="name">
				</li>
				<!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
				<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
			</ul>

			<section class="top-bar-section">
				<!-- Right Nav Section -->
				<ul class="right">
					<?php if(!isset($_SESSION['loggedIn'])) { ?>
					<li>
						<a href="create_account.php">Mitglied werden</a>
					</li>
					<?php } ?>
					<li class="has-dropdown">
						<a href="#">Support</a>
						<ul class="dropdown">
							<li><a href="#">Einrichtung</a></li>
							<li><a href="#">Kontakt</a></li>
						</ul>
					</li>
					<li class="has-dropdown">
						<a href="#">About</a>
						<ul class="dropdown">
							<li><a href="#">&Uuml;ber dieses Projekt</a></li>
							<li><a href="#">Impressum</a></li>
						</ul>
					</li>
					<li class="has-dropdown active">
						<a href="webcam_monitor.php">
						<?php
							echo $_SESSION['firstname']." ".$_SESSION['lastname'];
						?>
						</a>
						<ul class="dropdown">
							<li><a href="logout.php">Logout</a></li>
						</ul>
					</li>
				</ul>
			</section>
		</nav>
	</div>
	<div id="maincontent">
		<div class="main_container">
			<h1><?php echo $result2["description"]; ?></h1>
			<p>
			<div class="webcam row">
				<div class="webcam_content small-12 medium-9 large-9 columns">
					<div class="status_image">
						<?php
							if($result2["isActive"] == 0) {
								echo "<img src=\"img/webcam_offline.png\" alt=\"Webcam\" /> offline";
							} else if($result2["isActive"] == 1) {
								echo "<img src=\"img/webcam_online.png\" alt=\"Webcam\" /> online";
							}
						?>
					</div>
					<div class="webcam_image">
						<?php									
							$newest_file = $times[0];
							if(empty($newest_file)) {
								echo "<img src=\"img/placeholder.jpg\" />";
							} else {
								echo "<img src=\"$directory/cam".$id."_".$newest_file.".jpg\" id=\"webcam_img\"/>";
							}
						?>
					</div>
				</div>
				<div class="webcam_timehandler small-12 medium-3 large-3 columns">
					<form action="<?php echo $_SERVER['PHP_SELF'];?>">
						<select class="timehandler_date" onchange="changeDate(this.value)">
							<?php
								
								foreach($dates as $date_timestamp) {
									$date = date("d. F Y", $date_timestamp); //Datum generieren, z.B. 18. November 2015
									$date_for_value = date("dFY", $date_timestamp); //Value-Wert generieren
									
									if($date_for_value == $date_active) {
										$selected = "selected=\"selected\"";
									} else {
										$selected = "";
									}
									
									echo "<option value=\"$date_for_value\" $selected>$date</option>";								
								}
								
								
								echo $active_date;
							?>
						</select>
						
						<select id="timehandler_time" size="10" onchange="changePicture(this.value)">
							<?php
								$i = 0;
								foreach($times as $time) {
									if($i == 0) {
										$selected = "selected=\"selected\"";
									} else {
										$selected = "";
									}
									echo "<option value=\"$directory/cam".$id."_$time.jpg\" $selected>".date("H:i:s", $time)."</option>";
									$i++;
								}
							?>
						</select>
					</form>
				</div>
			</div>
		</div>
	</div>	
	<div id="footer">
		<div class="row">
			<div class="large-3 columns">
			Secure@Home<br />
			Zentralstrasse 9 <br />
			6002 Luzern
			
			</div>
			<div class="large-9 columns">
			T +41 41 333 25 25<br />
			F +41 41 333 25 26 <br />
			<a href="#">contact@securehome.ch</a>
			
			</div>
		</div>
	</div>
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
      $(document).foundation();
    </script>
  </body>
</html>