<?php
	
	session_start();
	
	//Check if the user is already logged in
	if(isset($_SESSION['loggedIn']) && $_SESSION["loggedIn"] == 1) {
		header("Location: webcam_monitor.php");
		exit;
	}
	
	
	if(isset($_REQUEST['login'])) {
		$username = $_REQUEST['username'];
		$password = $_REQUEST['password'];
		$tag = $_REQUEST['tag'];
		
		//Check if every formfield is filled with values
		if(!empty($username) AND !empty($password)) {
			
			//CALL API
			$url = $_SERVER['HTTP_HOST'].dirname($_SERVER['REQUEST_URI'])."/api/index.php";

			$data = array(
				"tag" => $tag,
				"username" => $username,
				"password" => $password
			);
			
			
			//open connection
			$ch = curl_init();
			
			curl_setopt($ch, CURLOPT_POST, sizeof($data));
			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($ch, CURLOPT_URL, $url);

			//execute post
			$result = curl_exec($ch);
			//close connection
			curl_close($ch);

			//decode JSON String
			$result = (json_decode($result, true));
			
			if($result["success"] == 1) {
				//Login successfull
				$_SESSION["loggedIn"] = 1;
				$_SESSION["uid"] = $result["uid"];
				$_SESSION["firstname"] = $result["user"]["firstname"];
				$_SESSION["lastname"] = $result["user"]["lastname"];
				$_SESSION["email"] = $result["user"]["email"];
				header("Location: webcam_monitor.php");
				
			} else if ($result["success"] == 0) {
				$errorMessage = "Username oder Passwort ist falsch.";					
			}	else {
				$errorMessage ="Fehler beim Login. Bitte später erneut versuchen.";
			}
			
		} else {
			$errorMessage = "Bitte alle Felder ausfüllen!";
		}
	}
	
?>
<!doctype html>
<html class="no-js" lang="de">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login | Secure@Home</title>
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
					<li>
						<a href="create_account.php">Mitglied werden</a>
					</li>
					<li class="has-dropdown">
						<a href="#">Support</a>
						<ul class="dropdown">
							<li><a href="#">Einrichtung</a></li>
							<li class="active"><a href="#">Kontakt</a></li>
						</ul>
					</li>
					<li class="has-dropdown">
						<a href="#">About</a>
						<ul class="dropdown">
							<li><a href="#">Über dieses Projekt</a></li>
							<li class="active"><a href="#">Impressum</a></li>
						</ul>
					</li>
					<li class="has-form">
						<div class="row">
							<form action="login.php" method="post">
								<div class="large-4 small-4 columns">
									<input type="text" placeholder="Benutzername" name="username" />
								</div>
								<div class="large-4 small-4 columns">
									
									<input type="password" placeholder="Passwort" name="password" />
								</div>
								<div class="large-4 small-4 columns">
									<input type="hidden" name="tag" value="login" />
									<input type="submit" value="Login" name="login" />
								
								</div>
							</form>
						</div>
					</li>
				</ul>
			</section>
		</nav>
	</div>
	<div id="maincontent">
		<div class="row">
		  <div class="large-8 medium-8 columns">
			<h1>Login</h1>
			<p>
				Melden Sie sich mit Ihren Benutzerdaten an, um Ihre Webcams zu nutzen.
			</p>
			<p class="error">
				<?php
					if(isset($errorMessage)) {
						echo " 
							<div class=\"alert\">
								$errorMessage
							</div>";
					}else if(isset($successMessage)) {
						echo"
							<div class=\"success\">
								$successMessage
							</div>";
					}
				?>
			</p>
			<form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
					  <div class="row">
						<div class="large-6 columns">
						  <label>Benutzername</label>
						  <input type="text" name="username"/>
						</div>
						<div class="large-6 columns">
							<label>Passwort</label>
						  <input type="password" name="password"/>
						</div>
					  </div>
					  <div class="row">
						<div class="large-6 columns">
						</div>
						<div class="large-6 columns">
							<input type="hidden" name="tag" value="login" />
							<input type="submit" value="Login" name="login" />
						</div>
					  </div>
					</form>
		  </div>     

		  <div class="large-4 medium-4 columns">
			<div class="panel">
				<h5>Passwort vergessen</h5>
				Haben Sie Ihr Passwort vergessen? <a href="#">Neues Passwort anfordern</a>        
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
