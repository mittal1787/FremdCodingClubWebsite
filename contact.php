<?php
  
$name = $_POST['Name'];
$email = $_POST['Email'];
$subject = $_POST['Subject'];
$message = $_POST['Message'];
$formcontent="From: $name \n Message: $message";
$recipient = "mittal1787@students.d211.org";
$mailheader = "From: $email \r\n";
mail($recipient, $subject, $formcontent, $mailheader) or die("Error!");
echo "Thank You!";

// Code From https://1stwebdesigner.com/php-contact-form-html/

?>