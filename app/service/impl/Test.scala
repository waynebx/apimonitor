package service.impl
import play.api.libs.concurrent.Promise
import play.api.libs.ws.Response
import play.api.libs.ws.WS
import org.squeryl.Table
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import models.Bean.MobionTestCase
import models.Dao.MongoAbstractDAO
import models.Dao.MobionTestcaseDAO
import models.Dao.MongoSalatDAO
import models.Dao.MobionTestcaseDAO
import models.Bean.APIRes



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
	/*//	  val h = new Http
		  var js: JsObject = JsObject(
				  Seq(
				      "email" -> JsString("ta2@yahoo.com"),
				      "password" -> JsString("123456")
				      )
		  )
		  val result = (path <<<<< "http://api.sgcharo.com:8080/mobion/auth/login" << Map("params" -> Seq(js.toString())))
		  print(result)*/	
		var item:MobionTestCase = new MobionTestCase("7","123")
		var dao = new MobionTestcaseDAO
		var item1:APIRes = new APIRes

		dao.save(item)
	}
}