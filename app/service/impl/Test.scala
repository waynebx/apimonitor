package service.impl
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.JsNull
import play.api.libs.json.JsObject

import sjson.json.JSONTypeHint
import scala.annotation.target.field
import scala.reflect.BeanInfo

object TestService {

	def main(args: Array[String]) {
			
	        val x = System.currentTimeMillis
	        print(x)
			val jsonObject = Json.toJson(
			  Map(
			    "users" -> Seq(
			      Json.toJson(
			        Map(
			          "name" -> Json.toJson("Bob"),
			          "age" -> Json.toJson(31),
			          "email" -> Json.toJson("bob@gmail.com")
			        )
			      ),
			     Json.toJson(
			        Map(
			          "name" -> Json.toJson("Kiki"),
			          "age" -> Json.toJson(25),
			          "email" -> JsNull
			        )
			      )
			    )
			  )
			)
			val users = (jsonObject \ "users").as[List[JsObject]]
	        for(user <- users){
	        	
//	            val testcase = user.as[UserJson]
	            print(user\ "name")
	        }
	        
			
			
			
	}
}