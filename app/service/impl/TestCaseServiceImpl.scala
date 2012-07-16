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
    	  apiConfigDAO.save(apiConfig)
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
    	testCaseDAO.pushToField(testCase.id, "apiConfigIds", apiConfig.id)
    	apiConfigDAO.save(apiConfig)
    	  
    	list += getAPIfromConfig(apiConfig)
    	})
    }
   
    return list.toList
  }
  
  def removeAPIfromTestCase(testCaseId : String, apiConfigIds : List[String]) {

    apiConfigIds.foreach(id => {
      testCaseDAO.pullFromField(testCaseId, "apiConfigIds", id)
      apiConfigDAO.deleteById(id)
    })

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
      api.apiConfigId = apiConfig.id;
      val map = apiConfig.parseParamToMap()
      var parameters = apiParameterDAO.getByAPIId(api.id);
      if (parameters != null) {
        parameters.foreach(param => {
        	val key = param.name
            if (map.get(key) != None) {
              param.value = map.get(key).get
            }
          }
        )
      }
      
      val expectedMap = apiConfig.parseExpertParamToMap();
      var expectedParamList = List[APIParameter]()
      if(expectedMap != null && expectedMap.keySet != null){
        expectedMap.keySet.foreach(expectedParamName => {
          var expectedParam = new APIParameter
          expectedParam.name = expectedParamName
          if(expectedMap.get(expectedParamName) != None){
            expectedParam.value = expectedMap.get(expectedParamName).get
            expectedParamList::=expectedParam
          }
        })
      }
      
      api.expectedParameters = expectedParamList
      api.parameters = parameters
      api.expert_params = apiConfig.expert_params
    }
    return api;
  }
  
  def getListMobionTestCase(start: String, size: String):List[TestCase] = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    var list = testCaseDAO.findLimit(istart, isize)
    list
  }

}
