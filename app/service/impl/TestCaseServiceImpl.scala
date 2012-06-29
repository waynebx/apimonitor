package service.impl

import scala.collection.mutable.ListBuffer
import com.mongodb.casbah.commons.MongoDBObject
import models.testcase.API
import models.testcase.APIConfig
import models.testcase.TestCase
import models.FunctionJSON
import play.api.libs.json.Json
import service.AbstractService
import service.TestCaseService
import sjson.json.Serializer.SJSON
import util.StringUtil
import dispatch.json.JsValue
import models.APIOperation
import com.mongodb.WriteConcern
import dispatch.json.Js


  object TestCaseOperation{
	val NEW = 0;  
	val ADD_APICONFIG = 1
	val REMOVE_APICONFIGS = 2
  }

class TestCaseServiceImpl extends TestCaseService with AbstractService {

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

  def addTestCase(testCase : TestCase): TestCase = {

    var apiConfigIds = new ListBuffer[String]
    
    if(testCase.apiConfigs != null && !testCase.apiConfigs.isEmpty){
    	testCase.apiConfigs.foreach(apiConfig => {
    	  saveAPIConfig(testCase.id, apiConfig, TestCaseOperation.NEW);
    	  apiConfigIds += apiConfig.id;
    	})
    }
    
    testCase.apiConfigIds = apiConfigIds.toList
    testCaseDAO.save(testCase)
    return testCase
  }
  
  def addAPI2TestCase(testCase : TestCase): TestCase = {
    
    var apiConfigIds = new ListBuffer[String]
    if(testCase.apiConfigs != null && !testCase.apiConfigs.isEmpty){
    	testCase.apiConfigs.foreach(apiConfig => {
    	  saveAPIConfig(testCase.id, apiConfig, TestCaseOperation.ADD_APICONFIG);
    	})
    }
   
    null;
  }

  def deleteTestCase(id: String) {
    testCaseDAO.removeById(id,WriteConcern.SAFE)
  }
  
  private def saveAPIConfig(testCaseId: String, apiConfig: APIConfig, operartion: Int) = {
    var api = apiOperationDAO.findById(apiConfig.apiId)
    if (api == null) {
      
    }
    
    operartion match {
    	case TestCaseOperation.NEW => {
    		apiConfigDAO.save(apiConfig)
    	}
 
    	case TestCaseOperation.ADD_APICONFIG => {
    		testCaseDAO.pushToField(testCaseId, "apiConfigIds", apiConfig.id)
    		apiConfigDAO.save(apiConfig)
    	}
    	
    	case TestCaseOperation.REMOVE_APICONFIGS => {
    		testCaseDAO.pullFromField(testCaseId, "apiConfigIds", apiConfig.id)
    	}
    }
  }
  
   def removeFunctionInTestCase(testCase: TestCase) {
    if (testCase.apiConfigIds != null && testCase.apiConfigIds.length > 0) {
      testCase.apiConfigIds.foreach(apiConfigId => {
        var function = apiConfigDAO.findById(apiConfigId)
        saveAPIConfig(testCase.id, function, StringUtil.TestCaseOperation.REMOVE)
      })
    }
  }

  def addFunctionInTestCase(testCase: TestCase) {
    if (testCase.apiConfigIds != null && testCase.apiConfigIds.length > 0) {
      testCase.apiConfigs.foreach(apiConfig => {
        saveAPIConfig(testCase.id, apiConfig, StringUtil.TestCaseOperation.ADD)
      })
    }
  }
  
  def editFunctionInTestCase(testCase: TestCase) {
    if (testCase.apiConfigIds != null && testCase.apiConfigIds.length > 0) {
      testCase.apiConfigs.foreach(apiConfig => {
        apiConfigDAO.save(apiConfig)
      })
    }
  }

}
