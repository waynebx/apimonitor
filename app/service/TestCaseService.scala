package service
import play.api.libs.json.JsValue
import models.Bean.MobionTestCase

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[MobionTestCase]
}