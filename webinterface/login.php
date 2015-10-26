<?php
	
	if(isset($_REQUEST['login'])) {
		$username = $_REQUEST['username'];
		$password = $_REQUEST['password'];
		
		//Check if every formfield is filled with values
		if(!empty($username) AND !empty($password)) {
			
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
							<div class="large-4 small-4 columns">
								<input type="text" placeholder="Benutzername" />
							</div>
							<div class="large-4 small-4 columns">
								
								<input type="password" placeholder="Passwort" />
							</div>
							<div class="large-4 small-4 columns">
							<a href="#" class="alert button expand">Login</a>
							</div>
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
						echo $errorMessage;
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
						  <input type="text" name="passowrd"/>
						</div>
					  </div>
					  <div class="row">
						<div class="large-6 columns">
						</div>
						<div class="large-6 columns">
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
