package util
import play.api.libs.json.JsObject
import models.testcase.APIConfig
import models.testcase.API
import play.api.libs.json.Json
import models.testcase.TestCase
import models.APIOperation


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
	
	def convertAPIConfig(testcaseDetail:APIConfig, api:APIOperation) = {
	  var map = Map[String,String]()
	  map += "id" -> testcaseDetail.id
	  map += "params" -> testcaseDetail.params
	  map += "path" -> api.path
	  map += "resource_path" -> api.resPath
	  map += "method" -> api.httpMethod
	  Json.toJson(map)
	}
	
	def convertTestCase(testCase:TestCase) = {
	  var map = Map[String,String]()
	  map += "id" -> testCase.id
	  map += "name" -> testCase.name
	  Json.toJson(map)
	}
}