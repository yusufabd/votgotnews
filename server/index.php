<?php

error_reporting(E_ALL);

$_PUT = array(); 
if ($_SERVER['REQUEST_METHOD'] == 'PUT') { 
    $putdata = file_get_contents('php://input'); 
    $exploded = explode('&', $putdata);  

    foreach($exploded as $pair) { 
        $item = explode('=', $pair); 
        if(count($item) == 2) { 
            $_PUT[urldecode($item[0])] = urldecode($item[1]); 
        } 
    } 
}
$_DELETE = array(); 
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') { 
    $putdata = file_get_contents('php://input'); 
    $exploded = explode('&', $putdata);  

    foreach($exploded as $pair) { 
        $item = explode('=', $pair); 
        if(count($item) == 2) { 
            $_DELETE[urldecode($item[0])] = urldecode($item[1]); 
        } 
    } 
}

class Controller
{
    protected $response;
    protected $responseStatus;

    public function __construct()
    {
        $this->response = [];
        $this->responseStatus = -1;
    }

    
    public function actionList()
    {
        return $this->printResponse();
    }
    public function actionCreate()
    {
        return $this->printResponse();
    }
    public function actionUpdate()
    {
        return $this->printResponse();
    }
    public function actionDelete()
    {
        return $this->printResponse();
    }


    public function setResponse($key, $value)
    {
        $this->response[$key] = $value;
    }

    public function getResponse($status)
    {
        $this->response['status'] = is_null($status) ? $this->responseStatus : $status;
        return $this->response;
    }

    public function printResponse($status = null)
    {
        header('Content-Type', 'application/json');
        die(json_encode($this->getResponse($status), JSON_UNESCAPED_UNICODE));
    }

    public function runAction()
    {
        switch ($this->getRequestMethod()) {
            case 'get':
                return $this->actionList();
                
            case 'post':
                return $this->actionCreate();

            case 'put':
            case 'patch':
                return $this->actionUpdate();
            
            case 'delete':
                return $this->actionDelete();
            default:
                die("unknown request method");
                break;
        }
    }

    protected function getRequestMethod()
    {
        return strtolower($_SERVER['REQUEST_METHOD']);
    }
}

class ErrorController extends Controller
{
    public function actionList(){
        $this->response['message'] = "ErrorController-list";
        return $this->printResponse();
    }
    public function actionCreate(){
        $this->response['message'] = "ErrorController-create";
        return $this->printResponse();
    }
    public function actionUpdate(){
        $this->response['message'] = "ErrorController-update";
        return $this->printResponse();
    }
    public function actionDelete(){
        $this->response['message'] = "ErrorController-delete";
        return $this->printResponse();
    }
}

class PostController extends Controller
{
    public function actionList()
    {
        $posts = [];
        foreach(App::instance()->db->query('SELECT * from posts') as $row) {
            $posts[] = [
                'id' => $row['id'],
                'title' => $row['title'],
                'url' => $row['url'],
                'duration' => $row['duration'],
                'start_second' => $row['start_second'],
                'artist' => $row['artist'],
                'created_at' => $row['created_at'],
            ];
        }
        $this->response['models'] = $posts;
        return $this->printResponse(0);
    }

    public function actionCreate()
    {
        $userId = isset($_POST['user_id']) ? $_POST['user_id'] : 0;
        $title = isset($_POST['title']) ? $_POST['title'] : "DefaultTitle";
        $url = isset($_POST['url']) ? $_POST['url'] : "DefaultUrl";
        $duration = isset($_POST['duration']) ? $_POST['duration'] : 0;
        $startSecond = isset($_POST['start_second']) ? $_POST['start_second'] : 0;
        $artist = isset($_POST['artist']) ? $_POST['artist'] : 0;


        $stmt = App::instance()->db->prepare("INSERT INTO posts (title, user_id, url, duration, start_second, artist) VALUES (:title, :user_id, :url, :duration, :start_second, :artist)");
        $stmt->bindParam(':title', $title);
        $stmt->bindParam(':user_id', $userId);
        $stmt->bindParam(':url', $url);
        $stmt->bindParam(':duration', $duration);
        $stmt->bindParam(':start_second', $startSecond);
        $stmt->bindParam(':artist', $artist);
        $stmt->execute();

        $this->response['model_id'] = App::instance()->db->lastInsertId();

        return $this->printResponse(0);
    }
}
class UserController extends Controller
{
    public function actionCreate()
    {
        $userName = isset($_POST['name']) ? $_POST['name'] : 'DefaultName';

        $stmt = App::instance()->db->prepare("INSERT INTO users (name) VALUES (:name)");
        $stmt->bindParam(':name', $userName);
        $stmt->execute();

        $this->response['model_id'] = App::instance()->db->lastInsertId();

        return $this->printResponse(0);
    }
}

class App 
{
    public static $_app;
    public $db;

    public static function instance()
    {
        if (is_null(static::$_app)) {
            static::$_app = new App();
        }
        return static::$_app;
    }


    public function __construct()
    {
        $this->db = require_once("protect/db.php");
    }

    public function run()
    {
        $ctrlName = isset($_GET['controller'])
            ? ucfirst(strtolower($_GET['controller']))
            : "Error";
        $ctrlName = $ctrlName . "Controller";

        if (!class_exists($ctrlName))
            $ctrlName = "ErrorController";

        $controller = new $ctrlName();
        return $controller->runAction();
    }
}

App::instance()->run();
