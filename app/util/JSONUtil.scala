package util
import play.api.libs.json.JsObject
import models.testcase.APIConfig
import models.testcase.API
import play.api.libs.json.Json
import models.testcase.TestCase


object JSONUtil {
	def getListKeyOfJsonObject(js:JsObject) = {
	  var list:List[String] = List[String]()
	  if(js != null && js.keys != null){
	    for(key <- js.keys){
	      list ::= key
	    }
	  }
	  list
	}
	
	def convertAPIConfig(testcaseDetail:APIConfig, api:API) = {
	  var map = Map[String,String]()
	  map += "id" -> testcaseDetail._id
	  map += "params" -> testcaseDetail.params
	  map += "path" -> api.path
	  map += "resource_path" -> api.restPath
	  map += "method" -> api.method
	  Json.toJson(map)
	}
	
	def convertTestCase(testCase:TestCase) = {
	  var map = Map[String,String]()
	  map += "id" -> testCase._id
	  map += "name" -> testCase.name
	  Json.toJson(map)
	}
}