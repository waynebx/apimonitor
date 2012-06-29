package service
import play.api.libs.json.JsValue
import models.testcase.TestCase
import models.APIResource

trait MyService {
	def go
	def getListApi:String
	def getListAPIInRes(rest:String):String
	def getListTestCaseDetail(idMobionTestCase:String):JsValue
	//Giang
	def getTestCaseList(start:String,size:String) : List[TestCase]
}