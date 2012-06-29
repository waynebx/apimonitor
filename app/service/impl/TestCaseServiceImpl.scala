package service.impl

import scala.collection.mutable.ListBuffer
import scala.reflect.BeanInfo

  import com.mongodb.WriteConcern

  import models.testcase.APIConfig
import models.testcase.TestCase
import models.APIOperation
import models.APIParameter
import service.AbstractService
import service.TestCaseService
import util.StringUtil



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
  
  def addAPI2TestCase(testCase : TestCase): List[APIOperation] = {
    
    var apiConfigIds = new ListBuffer[String]
    var list = new ListBuffer[APIOperation]();
    if(testCase.apiConfigs != null && !testCase.apiConfigs.isEmpty){
    	testCase.apiConfigs.foreach(apiConfig => {
    	  saveAPIConfig(testCase.id, apiConfig, TestCaseOperation.ADD_APICONFIG);
    	  
    	  list += getAPIfromConfig(apiConfig)
    	})
    }
   
    return list.toList
  }

  def deleteTestCase(id: String) {
    testCaseDAO.removeById(id,WriteConcern.SAFE)
  }
  
  def getAPIsinTestCase(testCaseId: String): List[APIOperation] = {
    var testCase = testCaseDAO.findById(testCaseId)

    if (testCase != null) {
      return getAPIsfromConfigs(testCase.apiConfigIds);
    }

    return List[APIOperation]()
  }
  
  private def getAPIsfromConfigs(apiConfigIds : List[String]) : List[APIOperation] = {
    var list = new ListBuffer[APIOperation]();
    apiConfigIds.foreach(id => {
        var apiConfig = apiConfigDAO.findById(id)
        if (apiConfig != null) {
          list += getAPIfromConfig(apiConfig)
        }
      })
      
      return list.toList;
  }
  private def getAPIfromConfig(apiConfig : APIConfig) : APIOperation = {
    var api = apiOperationDAO.findById(apiConfig.apiId)
    if (api != null) {
      var map = apiConfig.parseParamToMap()
      var keySet = map.keySet
      var parameters = List[APIParameter]()
      if (!keySet.isEmpty) {
        keySet.foreach(key => {
          var parameter = apiParameterDAO.findById(api.id + "__" + keySet)
          if (parameter != null) {
            if (map.get(key) != None) {
              parameter.value = map.get(key).get
              parameters ::= parameter
            }
          }
        })
      }
      api.parameters = parameters
    }
    return api;
  }
  
  private def saveAPIConfig(testCaseId: String, apiConfig: APIConfig, operartion: Int) = {
    var api = apiOperationDAO.findById(apiConfig.apiId)
    if (api != null) {
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
  
  def getListMobionTestCase(start: String, size: String):List[TestCase] = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    var list = testCaseDAO.findLimit(istart, isize)
    list
  }

}
