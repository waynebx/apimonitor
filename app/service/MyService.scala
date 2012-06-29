package service
import play.api.libs.json.JsValue
import models.testcase.TestCase
import models.APIResource

trait MyService {
	def go
	def getListApi:String
	def getListAPIInRes(rest:String):String
	def addTestCase(requestBody:String):String
	def getListTestCaseDetail(idMobionTestCase:String):JsValue
	def getListMobionTestCase(start:String,size:String):JsValue
	def removeTestCase(id:String)
	def getTestCaseDetailById(id:String):JsValue
	//Giang
	def getTestCaseList(start:String,size:String) : List[TestCase]
}