package service

import models.testcase.TestCase

trait TestCaseService {
	def getTestCaseList(start:Int,size:Int) : List[TestCase]
	
	def addTestCase(testCase : TestCase): TestCase
	
	def deleteTestCase(id: String)
}