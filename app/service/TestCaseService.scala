package service

import models.testcase.TestCase
import models.APIOperation

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	def addTestCase(testCase : TestCase): TestCase
	
	def deleteTestCase(id: String)
	
	def getAPIsinTestCase(testCaseId: String): List[APIOperation]
	
	def addAPI2TestCase(testCase : TestCase): List[APIOperation]
	
	def removeAPIfromTestCase(testCaseId : String, apiConfigIds : List[String])

/*	def removeFunctionInTestCase(testCase: TestCase)
	
	def addFunctionInTestCase(testCase: TestCase)
	
    def editFunctionInTestCase(testCase: TestCase)
*/	
	def getListMobionTestCase(start: String, size: String):List[TestCase]

}