package service
import play.api.libs.json.JsValue
import models.testcase.TestCase

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	
}