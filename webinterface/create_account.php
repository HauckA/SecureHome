<?php
	
	session_start();
	
	if(isset($_REQUEST['absenden'])) {
		$tag = $_REQUEST['tag'];
		$firstname = $_REQUEST['firstname'];
		$lastname = $_REQUEST['lastname'];
		$email = $_REQUEST['email'];
		$username = $_REQUEST['username'];
		$password1 = $_REQUEST['password1'];
		$password2 = $_REQUEST['password2'];
		
		//Check if every formfield is filled with values
		if(!empty($firstname) AND !empty($lastname) AND !empty($email) AND !empty($username) AND !empty($password1) AND !empty($password2)) {
			if($password1 == $password2) {
				
				
				//CALL API
				$url = $_SERVER['HTTP_HOST'].dirname($_SERVER['REQUEST_URI'])."/api/index.php";
				
				$data = array(
					"tag" => $tag,
					"firstname" => $firstname,
					"lastname" => $lastname,
					"email" => $email,
					"username" => $username,
					"password" => $password1
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
					$successMessage = "Konto erfolgreich erstellt.";
				} else if ($result["success"] == 0) {
					$errorMessage = "Fehler bei der Registrierung: Diese Mailadress ist bereits registriert.";					
				}	else {
					$errorMessage ="Fehler bei der Registrierung. Bitte später erneut versuchen.";
				}
				
			} else {
				$errorMessage = "Die eingegebenen Passwörter stimmen nicht überein!";
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
    <title>Mitglied werden | Secure@Home</title>
    <link rel="stylesheet" href="css/main.css" />
    <script src="js/vendor/modernizr.js"></script>
  </head>
  <body>
    
	<div id="header">
		<nav class="top-bar" data-topbar role="navigation">
			<ul class="title-area">
				<li class="name">
				<h1><a href="index.php">Secure@Home</a></h1>
				</li>
				<!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
				<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
			</ul>

			<section class="top-bar-section">
				<!-- Right Nav Section -->
				<ul class="right">
					<li  class="active">
						<a href="<?php echo $_SERVER['PHP_SELF'];?>">Mitglied werden</a>
					</li>
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
							<li><a href="#">Über dieses Projekt</a></li>
							<li><a href="#">Impressum</a></li>
						</ul>
					</li>
					<?php if(isset($_SESSION['loggedIn'])) { ?>
						<li>
							<a href="webcam_monitor.php">
								<?php echo $_SESSION['firstname']." ".$_SESSION['lastname']; ?>
							</a>
						</li>
						
					<?php } else { ?>
					
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
					
					<?php } ?>
					
				</ul>
			</section>
		</nav>
	</div>
	<div id="maincontent">
		<div class="row">
		  <div class="large-8 medium-8 columns">
			<h1>Kostenloses Konto erstellen</h1>
			<p>
				Registrieren Sie sich hier, um sämtliche Funktionen von Secure@Home zu nutzen.
			</p>
			<p class="error">
				<?php
					if(isset($errorMessage)) {
						
						echo "<div class=\"alert\">
								$errorMessage
							</div>";
					} else if(isset($successMessage)) {
						echo "<div class=\"success\">
								$successMessage
							</div";
					}
				?>
			</p>
			<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
					  <div class="row">
						<div class="large-6 columns">
						  <label>Vorname</label>
						  <input type="text" name="firstname" value="<?php if(isset($firstname)) {echo $firstname; } ?>"/>
						</div>
						<div class="large-6 columns">
							<label>Name</label>
						  <input type="text" name="lastname" value="<?php if(isset($lastname)) {echo $lastname;}?>"/>
						</div>
					  </div>
					   <div class="row">
						<div class="large-6 columns">
						  <label>E-Mail</label>
						  <input type="text" name="email" value="<?php if(isset($email)) {echo $email;} ?>"/>
						</div>
						<div class="large-6 columns">
							<label>Gewünschter Benutzername</label>
						  <input type="text" name="username" value="<?php if(isset($username)) {echo $username;} ?>"/>
						</div>
					  </div>
					   <div class="row">
						<div class="large-6 columns">
						  <label>Passwort</label>
						  <input type="password" name="password1"/>
						</div>
						<div class="large-6 columns">
							<label>Passwort wiederholen</label>
						  <input type="password" name="password2" />
						</div>
					  </div>

					  <div class="row">
						<div class="large-6 columns">

						  
						</div>
						<div class="large-6 columns">
							<input type="hidden" name="tag" value="register" />
							<input type="submit" value="Konto erstellen" name="absenden" />
						</div>
					  </div>
					</form>
		  </div>     

		  <div class="large-4 medium-4 columns">
			<div class="panel">
				<h5>Ein Konto - viele Vorteile</h5>
				<p>
					<ul>
						<li><b>Einfache Verwaltung</b><br />Sowohl das Registrieren neuer Webcams wie auch die Verwaltung über das Webinterface sind sehr einfach gehalten.</li>
						<li><b>Ein Konto für alles</b><br /> Mit Ihrem persönlichen Konto melden Sie sich sowohl bei der App als auch im Webinterface an.</li>
					</ul>
				</p>
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
