package util
import play.api.libs.json.JsObject
import models.Bean.TestCaseDetail
import models.Bean.APIRes
import play.api.libs.json.Json
import models.Bean.MobionTestCase


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
	
	def convertTestCaseDetail(testcaseDetail:TestCaseDetail, apiRes:APIRes) = {
	  var map = Map[String,String]()
	  map += "id" -> testcaseDetail._id
	  map += "params" -> testcaseDetail.params
	  map += "path" -> apiRes.path
	  map += "resource_path" -> apiRes.restPath
	  map += "method" -> apiRes.method
	  Json.toJson(map)
	}
	
	def convertMobionTestCase(mobionTestCase:MobionTestCase) = {
	  var map = Map[String,String]()
	  map += "id" -> mobionTestCase._id
	  map += "name" -> mobionTestCase.name
	  Json.toJson(map)
	}
}