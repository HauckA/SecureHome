<?php
	session_start();
	
	//Check if user is logged in, if not forward to index.php
	if($_SESSION['loggedIn'] != 1) {
		header("Location: index.php");
		exit;
	}
	
	//CALL API and get the IDs from the webcams connected to the user
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

?>

<!doctype html>
<html class="no-js" lang="de">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Webcam Monitor | Secure@Home</title>
    <link rel="stylesheet" href="css/main.css" />
    <script src="js/vendor/modernizr.js"></script>
	<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="img/favicon.ico" type="image/x-icon">
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
						<a href="<?php echo $_SERVER['PHP_SELF'];?>">
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
		<h1>Webcam Monitor</h1>
		<p>
		
		<b>Herzlich Willkommen, <?php echo $_SESSION['firstname']." ".$_SESSION['lastname'] ?></b>
		</p>
		
		<div class="row">
		
			<?php
					
					
				for($i=0; $i<$result['numberOfWebcams']; $i++) {
				
					$webcamID = $result['webcamID'][$i];
				
				
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
			
					
					?>
					
					
					
						<div class="large-6 small-6 columns">
							<div class="webcam">
								<h5><a href="webcam_detail.php?id=<?php echo "$webcamID"; ?>"><?php echo $result2['description'];?></a></h5>
								<div class="webcam_content">
									<div class="status_image">
										<?php
											if($result2['isActive'] == 0) {
												echo " <img src=\"img/webcam_offline.png\" alt=\"Webcam\" /> offline";
											} elseif($result2['isActive'] == 1) {
												echo " <img src=\"img/webcam_online.png\" alt=\"Webcam\" /> online";
											}
										?>
										
									</div>
									<div class="webcam_image">
										<a href="webcam_detail.php?id=<?php echo "$webcamID";?>">
											
											<?php
												$directory = "api/registratedUserhome/".$_SESSION['email']."/$webcamID";
												$files = @scandir($directory, SCANDIR_SORT_DESCENDING);
												
												$newest_file = $files[0];
												if(empty($newest_file)) {
													echo "<img src=\"img/placeholder.jpg\" />";
												} else {
													echo "<img src=\"$directory/$newest_file\"/>";
												}
											?>
										</a>
									</div>
								</div>
							</div>
						</div>
					
					<?php
					
					if($i % 2 != 0) {
						echo "
							</div>
							<div class=\"row\">
							";
					}
					
				}
				
				//close connection
				curl_close($ch);
			?>
		
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