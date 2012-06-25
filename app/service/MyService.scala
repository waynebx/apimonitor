package service
import play.api.libs.json.JsValue

trait MyService {
	def go
	def getListApi:String
	def getListAPIInRes(rest:String):String
	def addTestCase(requestBody:String):String
	def getListTestCaseDetail(idMobionTestCase:String):JsValue
	def getListMobionTestCase(start:String,size:String):JsValue
}