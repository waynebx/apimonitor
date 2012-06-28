package service

import models.testcase.TestCase
import dispatch.json.JsValue

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	def addTestCase(testCase : JsValue): TestCase
	
	def deleteTestCase(id: String)
}