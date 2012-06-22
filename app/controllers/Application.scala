package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import util.APIRequestUtils
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import javax.transaction.Transaction
import javax.persistence.Persistence
import models.Dao.MobionTestcaseDAO
import com.mongodb.casbah.MongoCollection
import play.api.libs.json.JsObject

object Application extends AbstractController{
  def index = Action {
    
      var js: JsObject = JsObject(
			  Seq(
			      "email" -> JsString("ta2@yahoo.com"),
			      "password" -> JsString("123456")
			      )
			  )
    Ok(APIRequestUtils.postWSWithDefaultHost("auth/login",Map("params" -> Seq(js.toString())))).as("text/plain")
  }
  
  def getListAPI = Action {
    
     Ok(myService.getListApi).as("text/plain")
  }
   
  def getListAPIInRest(rest:String) = Action {
     Ok(myService.getListAPIInRes(rest)).as("text/plain")
  }
  
    def addTestCase(rest:String) = Action(parse.json) {request =>

     
     Ok(myService.getListAPIInRes(rest)).as("text/plain")
  }
}