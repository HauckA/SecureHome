<?php
	session_start();
?>

<!doctype html>
<html class="no-js" lang="de">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Startseite | Secure@Home</title>
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
				<!--<h1><a href="#">Secure@Home</a></h1>-->
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
							<li><a href="#">Über dieses Projekt</a></li>
							<li><a href="#">Impressum</a></li>
						</ul>
					</li>
					<?php if(isset($_SESSION['loggedIn'])) { ?>
						<li class="has-dropdown">
							<a href="webcam_monitor.php">
								<?php echo $_SESSION['firstname']." ".$_SESSION['lastname']; ?>
							</a>
							<ul class="dropdown">
								<li><a href="logout.php">Logout</a></li>
							</ul>
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
		<div class="main_container">
			<div class="row">
			  <div class="large-8 medium-8 columns">
				<h1>Secure@Home</h1>
				<p>Smartphones verfügen heute über eine äusserst gute Aufnahmequalität. Aus diesem Grund können Sie auch bestens als 
				Webcam oder Überwachungskamera eingesetzt werden. So erhält das nicht mehr benötigte Smartphone eine
				sinnvolle Funktion.</p>
				
				<div class="row">
					<div class="large-2 columns">
						&nbsp;
					</div>
					<div class="large-8 columns">
						<img src="img/schema_website.png" alt="Übersicht" />
					</div>
					<div class="large-2 columns">
					</div>
				</div>
				
				<div class="row">
					<div class="large-6 columns">
						<h4>Dienstleistungen</h4>
						<p>Wir bieten Ihnen eine kostenlose App, mit der Sie Ihr Android-Smartphone bequem als Webcam einsetzen können. Über das Webinterface verwalten
						Sie die registrierten Geräte und schauen sich die übermittelten Bilder an.</p>
					</div>
					<div class="large-6 columns">
						<h4>Wie funktionierts?</h4>
						<p>Alles was Sie benötigen ist unsere Secure@Home-App aus dem Android Playstore sowie ein Benutzerkonto. Mehr erfahren Sie in der rechten Box.</p>
						<p>Beachten Sie auch unsere ausführliche Anleitung im <a href="#">Support-Bereich</a>.
					</div>
				</div>

				 
			  </div>     

			  <div class="large-4 medium-4 columns">
				
				<div class="panel">
					<h5>So einfach funktionierts</h5>
					<p>
						<ol>
							<li><b>Konto erstellen</b><br/> Mit Ihren persönlichen Konto melden Sie sich in der App und im Webinterface an.</li>
							<li><b>Gerät registrieren</b><br /> Über die Secure@Home-App registrieren Sie Ihr Smartphone als Webcam</li>
							<li><b>Datenübertragung starten</b><br /> Starten Sie über die App die Synchronisation mit dem Server.</li>
							<li><b>Sicher sein</b><br /> Über das Webinterface kontrollieren Sie alle aktiven Webcams. Auch können Sie 
							die aufgenomenen Bilder der Vergangeheit anschauen.</li>
						</ol>
					</p>
					<a href="create_account.php" class="small button alert fullWidth">Konto erstellen</a>          
				</div>
			  </div>
			</div>
		</div>
	</div>
	<div id="footer">
		<div class="row">
			<div class="small-3 large-3 columns">
			Secure@Home<br />
			Zentralstrasse 9 <br />
			6002 Luzern
			
			</div>
			<div class="small-9 large-9 columns">
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
