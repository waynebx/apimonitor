package service

import models.testcase.TestCase

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	def addTestCase(testCase : TestCase): TestCase
	
	def deleteTestCase(id: String)
	
	def removeFunctionInTestCase(testCase: TestCase)
	
	def addFunctionInTestCase(testCase: TestCase)
	
    def editFunctionInTestCase(testCase: TestCase)
	
	def getListMobionTestCase(start: String, size: String):List[TestCase]
}