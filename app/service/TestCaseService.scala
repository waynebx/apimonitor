package service

import models.testcase.TestCase
import models.APIOperation

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	def addTestCase(testCase : TestCase): TestCase
	
	def deleteTestCase(id: String)
	
	def getAPIsinTestCase(testCaseId: String): List[APIOperation]
	
	def addAPI2TestCase(testCase : TestCase): List[APIOperation]
}