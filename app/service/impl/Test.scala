package service.impl
import dispatch._
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.ws._
import play.api.libs.concurrent.Promise

object path{
  var x:String =""
  def <<<<< (y:String) = {
    x = y
    this
  }
   def << (y:Map[String, Seq[String]])={
      	val result: Promise[Response] = {
    			WS.url(x).post(y)
    	}
      	val body = result.value.get.json\ "picture_big"
      	body
  }
}

object Test {
def main(args: Array[String]) {
//	  val h = new Http
	  var js: JsObject = JsObject(
			  Seq(
			      "email" -> JsString("ta2@yahoo.com"),
			      "password" -> JsString("123456")
			      )
	  )
	  val result = (path <<<<< "http://api.sgcharo.com:8080/mobion/auth/login" << Map("params" -> Seq(js.toString())))
	  print(result)
	  }
}