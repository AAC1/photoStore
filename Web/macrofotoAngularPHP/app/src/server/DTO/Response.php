<?PHP
class Response{
    private $message;
    private $status;
    private $code;

    public function getMessage(){
        return $this->message;
    }
    public function setMessage($message){
        $this->message=$message;
    }
    
    public function getStatus(){
        return $this->status;
    }
    public function setStatus($status){
        $this->status=$status;
    }

    public function getCode(){
        return $this->code;
    }
    public function setCode($code){
        $this->code=$code;
    }
}
?>