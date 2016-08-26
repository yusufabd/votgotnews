<?php

$dbUser = "{{DB_NAME}}";
$dbPassword = "{{DB_PASSWORD}}";

try {
    $db = new PDO(
        'mysql:host=localhost;dbname=androidstudio_vkmusic;charset=utf8', 
        $dbUser, 
        $dbPassword
    );
} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>";
    die();
}

return $db;