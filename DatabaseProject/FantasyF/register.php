<?php
 session_start();
 if( isset($_SESSION['user'])!="" ){
  header("Location: home.php");
 }
 include_once 'config.php';

 $error = false;
 $nameError = '';
 $emailError =  '';
 $passError = '';
 $squadNameError = '';

$connection = mysql_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D"); 
  $db = mysql_select_db("madiyar_orazaly", $connection);

 if ( isset($_POST['btn-signup']) ) {
  

  // clean user inputs to prevent sql injections
  $name = trim($_POST['name']);
  $name = strip_tags($name);
  $name = htmlspecialchars($name);
  
  $email = trim($_POST['email']);
  $email = strip_tags($email);
  $email = htmlspecialchars($email);
  
  $pass = trim($_POST['pass']);
  $pass = strip_tags($pass);
  $pass = htmlspecialchars($pass);

  $squadName = trim($_POST['squadName']);
  $squadName = strip_tags($squadName);
  $squadName = htmlspecialchars($squadName);
  
  // basic name validation
  if (empty($name)) {
   $error = true;
   $nameError = "Please enter your full name.";
  } else if (strlen($name) < 3) {
   $error = true;
   $nameError = "Name must have atleast 3 characters.";
  } else if (!preg_match("/^[a-zA-Z ]+$/",$name)) {
   $error = true;
   $nameError = "Name must contain alphabets and space.";
  }
  
  //basic email validation
  if ( !filter_var($email,FILTER_VALIDATE_EMAIL) ) {
   $error = true;
   $emailError = "Please enter valid email address.";
  } else {
   // check email exist or not
   $query = mysql_query("SELECT Mail FROM Users WHERE Mail='$email'", $connection);
    $rows = mysql_num_rows($query);

   if($rows != 0){
    $error = true;
    $emailError = "Provided Email is already in use.";
   }
  }
  
  // password validation
  if (empty($pass)){
   $error = true;
   $passError = "Please enter password.";
  } else if(strlen($pass) < 6) {
   $error = true;
   $passError = "Password must have atleast 6 characters.";
  }

  // basic squad name validation
  if (empty($squadName)) {
   $error = true;
   $squadNameError = "Please enter your Squad name.";
  } else if (strlen($squadName) < 4) {
   $error = true;
   $squadNameError = "Name must have atleast 4 characters.";
  } else {
       // check squad name exist or not
   $query = mysql_query("SELECT SquadName FROM Squad WHERE SquadName='$squadName'", $connection);
    $rows = mysql_num_rows($query);

   if($rows != 0){
    $error = true;
    $squadNameError = "The squad name is already in use.";
    }
  }
  
  // if there's no error, continue to signup
  if( !$error ) {
   
   $query1 = "INSERT INTO Users(UserName,Mail,Password) VALUES('$name','$email','$pass')";
   $query2 = "INSERT INTO Squad(SquadName) VALUES('$squadName')";
   $res1 = mysql_query($query1, $connection);
   $res2 = mysql_query($query2, $connection);
    
   if ($res1 & $res2) {
    $errTyp = "success";
    $errMSG = "Successfully registered, you may login now";
    unset($name);
    unset($email);
    unset($pass);
    unset($squadName);
	header("location: login.php");
   } else {
    $errTyp = "danger";
    $errMSG = "Something went wrong, try again later..."; 
   } 
    
  }
  mysql_close($connection); 
  
 }
?>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>login - Bootsnipp.com</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
      /*------------------------------------------------------------------
    [Master Stylesheet]
    
    Project        : Aether
    Version		: 1.0
    Last change	: 2015/03/27
    -------------------------------------------------------------------*/
    /*------------------------------------------------------------------
    [Table of contents]
    
    1. General Structure
    2. Anchor Link
    3. Text Outside the Box
    4. Main Form
    5. Login Button
    6. Form Invalid
    7. Form - Main Message
    8. Custom Checkbox & Radio
    9. Misc
    -------------------------------------------------------------------*/
    /*=== 1. General Structure ===*/
    html,
    body {
      background: #efefef;
      padding: 10px;
      font-family: 'Varela Round';
    }
    /*=== 2. Anchor Link ===*/
    a {
      color: #aaaaaa;
      transition: all ease-in-out 200ms;
    }
    a:hover {
      color: #333333;
      text-decoration: none;
    }
    /*=== 3. Text Outside the Box ===*/
    .etc-login-form {
      color: #919191;
      padding: 10px 20px;
    }
    .etc-login-form p {
      margin-bottom: 5px;
    }
    /*=== 4. Main Form ===*/
    .login-form-1 {
      max-width: 300px;
      border-radius: 5px;
      display: inline-block;
    }
    .main-login-form {
      position: relative;
    }
    .login-form-1 .form-control {
      border: 0;
      box-shadow: 0 0 0;
      border-radius: 0;
      background: transparent;
      color: #555555;
      padding: 7px 0;
      font-weight: bold;
      height:auto;
    }
    .login-form-1 .form-control::-webkit-input-placeholder {
      color: #999999;
    }
    .login-form-1 .form-control:-moz-placeholder,
    .login-form-1 .form-control::-moz-placeholder,
    .login-form-1 .form-control:-ms-input-placeholder {
      color: #999999;
    }
    .login-form-1 .form-group {
      margin-bottom: 0;
      border-bottom: 2px solid #efefef;
      padding-right: 20px;
      position: relative;
    }
    .login-form-1 .form-group:last-child {
      border-bottom: 0;
    }
    .login-group {
      background: #ffffff;
      color: #999999;
      border-radius: 8px;
      padding: 10px 20px;
    }
    .login-group-checkbox {
      padding: 5px 0;
    }
    /*=== 5. Login Button ===*/
    .login-form-1 .login-button {
      position: absolute;
      right: -25px;
      top: 50%;
      background: #ffffff;
      color: #999999;
      padding: 11px 0;
      width: 50px;
      height: 50px;
      margin-top: -25px;
      border: 5px solid #efefef;
      border-radius: 50%;
      transition: all ease-in-out 500ms;
    }
    .login-form-1 .login-button:hover {
      color: #555555;
      transform: rotate(450deg);
    }
    .login-form-1 .login-button.clicked {
      color: #555555;
    }
    .login-form-1 .login-button.clicked:hover {
      transform: none;
    }
    .login-form-1 .login-button.clicked.success {
      color: #2ecc71;
    }
    .login-form-1 .login-button.clicked.error {
      color: #e74c3c;
    }
    /*=== 6. Form Invalid ===*/
    label.form-invalid {
      position: absolute;
      top: 0;
      right: 0;
      z-index: 5;
      display: block;
      margin-top: -25px;
      padding: 7px 9px;
      background: #777777;
      color: #ffffff;
      border-radius: 5px;
      font-weight: bold;
      font-size: 11px;
    }
    label.form-invalid:after {
      top: 100%;
      right: 10px;
      border: solid transparent;
      content: " ";
      height: 0;
      width: 0;
      position: absolute;
      pointer-events: none;
      border-color: transparent;
      border-top-color: #777777;
      border-width: 6px;
    }
    /*=== 7. Form - Main Message ===*/
    .login-form-main-message {
      background: #ffffff;
      color: #999999;
      border-left: 3px solid transparent;
      border-radius: 3px;
      margin-bottom: 8px;
      font-weight: bold;
      height: 0;
      padding: 0 20px 0 17px;
      opacity: 0;
      transition: all ease-in-out 200ms;
    }
    .login-form-main-message.show {
      height: auto;
      opacity: 1;
      padding: 10px 20px 10px 17px;
    }
    .login-form-main-message.success {
      border-left-color: #2ecc71;
    }
    .login-form-main-message.error {
      border-left-color: #e74c3c;
    }
    /*=== 8. Custom Checkbox & Radio ===*/
    /* Base for label styling */
    [type="checkbox"]:not(:checked),
    [type="checkbox"]:checked,
    [type="radio"]:not(:checked),
    [type="radio"]:checked {
      position: absolute;
      left: -9999px;
    }
    [type="checkbox"]:not(:checked) + label,
    [type="checkbox"]:checked + label,
    [type="radio"]:not(:checked) + label,
    [type="radio"]:checked + label {
      position: relative;
      padding-left: 25px;
      padding-top: 1px;
      cursor: pointer;
    }
    /* checkbox aspect */
    [type="checkbox"]:not(:checked) + label:before,
    [type="checkbox"]:checked + label:before,
    [type="radio"]:not(:checked) + label:before,
    [type="radio"]:checked + label:before {
      content: '';
      position: absolute;
      left: 0;
      top: 2px;
      width: 17px;
      height: 17px;
      border: 0px solid #aaa;
      background: #f0f0f0;
      border-radius: 3px;
      box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.3);
    }
    /* checked mark aspect */
    [type="checkbox"]:not(:checked) + label:after,
    [type="checkbox"]:checked + label:after,
    [type="radio"]:not(:checked) + label:after,
    [type="radio"]:checked + label:after {
      position: absolute;
      color: #555555;
      transition: all .2s;
    }
    /* checked mark aspect changes */
    [type="checkbox"]:not(:checked) + label:after,
    [type="radio"]:not(:checked) + label:after {
      opacity: 0;
      transform: scale(0);
    }
    [type="checkbox"]:checked + label:after,
    [type="radio"]:checked + label:after {
      opacity: 1;
      transform: scale(1);
    }
    /* disabled checkbox */
    [type="checkbox"]:disabled:not(:checked) + label:before,
    [type="checkbox"]:disabled:checked + label:before,
    [type="radio"]:disabled:not(:checked) + label:before,
    [type="radio"]:disabled:checked + label:before {
      box-shadow: none;
      border-color: #8c8c8c;
      background-color: #878787;
    }
    [type="checkbox"]:disabled:checked + label:after,
    [type="radio"]:disabled:checked + label:after {
      color: #555555;
    }
    [type="checkbox"]:disabled + label,
    [type="radio"]:disabled + label {
      color: #8c8c8c;
    }
    /* accessibility */
    [type="checkbox"]:checked:focus + label:before,
    [type="checkbox"]:not(:checked):focus + label:before,
    [type="checkbox"]:checked:focus + label:before,
    [type="checkbox"]:not(:checked):focus + label:before {
      border: 1px dotted #f6f6f6;
    }
    /* hover style just for information */
    label:hover:before {
      border: 1px solid #f6f6f6 !important;
    }
    /*=== Customization ===*/
    /* radio aspect */
    [type="checkbox"]:not(:checked) + label:before,
    [type="checkbox"]:checked + label:before {
      border-radius: 3px;
    }
    [type="radio"]:not(:checked) + label:before,
    [type="radio"]:checked + label:before {
      border-radius: 35px;
    }
    /* selected mark aspect */
    [type="checkbox"]:not(:checked) + label:after,
    [type="checkbox"]:checked + label:after {
      content: '✔';
      top: 0;
      left: 2px;
      font-size: 14px;
    }
    [type="radio"]:not(:checked) + label:after,
    [type="radio"]:checked + label:after {
      content: '\2022';
      top: 0;
      left: 3px;
      font-size: 30px;
      line-height: 25px;
    }
    /*=== 9. Misc ===*/
    .logo {
      padding: 15px 0;
      font-size: 25px;
      color: #aaaaaa;
      font-weight: bold;
    }
    </style>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  </head><body>
    <!-- All the files that are required -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link href="http://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" type="text/css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<body>

<div class="text-center" style="padding:50px 0">

       <div class="login-form-1">
        <div class="logo">Register</div>
    <form method="post" class='text-left' action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" autocomplete="off">
    
     <div class="main-login-form">          
            <?php
   if ( isset($errMSG) ) {
    
    ?>
                <?php
   }
   ?>
            <div class="login-group">
            <div class="form-group">
             <div class="input-group">
             <input type="text" name="name" class="form-control" placeholder="Enter Name" maxlength="50" />
                </div>
                <span class="text-danger"><?php echo $nameError; ?></span>
            </div>
            
            <div class="form-group">
             <div class="input-group">
             <input type="email" name="email" class="form-control" placeholder="Enter Your Email" maxlength="40"  />
                </div>
                <span class="text-danger"><?php echo $emailError; ?></span>
            </div>
            
            <div class="form-group">
             <div class="input-group">
             <input type="password" name="pass" class="form-control" placeholder="Enter Password" maxlength="15" />
                </div>
                <span class="text-danger"><?php echo $passError; ?></span>
            </div>

            <div class="form-group">
             <div class="input-group">
             <input type="text" name="squadName" class="form-control" placeholder="Enter your Squad Name" maxlength="50" />
                </div>
                <span class="text-danger"><?php echo $squadNameError; ?></span>
            </div>
            </div>
            
             <button type="submit" class="login-button" name="btn-signup">
              <i class="fa fa-chevron-right"></i>
            </button>

            
            
			<div class="etc-login-form">
            <p>already have an account?
              <a href="index.php">login here</a>
            </p>
          </div>
        
        </div>
   
    </form>
    </div> 

</div>

</body></html>
