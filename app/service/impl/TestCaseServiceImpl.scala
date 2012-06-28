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


class TestCaseServiceImpl extends TestCaseService with AbstractService {

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

  def addTestCase(testCase : TestCase): TestCase = {
    
    println(testCase.name);
    var apiConfigIds = new ListBuffer[String]
    
    if(testCase.apiConfigs != null && !testCase.apiConfigs.isEmpty){
    	testCase.apiConfigs.foreach(apiConfig => {
    	  apiConfigDAO.save(apiConfig);
    	  apiConfigIds += apiConfig.id;
    	})
    }
    
    testCase.apiConfigIds = apiConfigIds.toList
    testCaseDAO.save(testCase)
    return testCase
  }

  def deleteTestCase(id: String) {
    testCaseDAO.removeById(id,WriteConcern.SAFE)
  }
  
 /*def addAPIConfigs2TestCase(testCaseId: String, apiConfigs: APIConfig, operartion: Int) = {
    var api = apiDAO.findById(apiConfig.apiId);
    if (api) {
      "Error" // Throw Error Code
      //      saveAPIRes(apiId)
    }

    
    if (StringUtil.TestCaseOperation.REMOVE.equals(operartion)) {
      testCaseDAO.pullFromField(testCaseId, "functions", apiConfig.id)
    }
    if (StringUtil.TestCaseOperation.EDIT.equals(operartion)) {
      testCaseDAO.pushToField(testCaseId, "functions", apiConfig.id)
    }
    apiConfigDAO.save(apiConfig)
    
    apiConfig.id
  }*/


  
  private def saveAPIConfig(testCaseId: String, apiConfig: APIConfig, operartion: Int) = {
    var api = apiOperationDAO.findOne(MongoDBObject("_id" -> apiConfig.apiId))
    if (api.isEmpty) {
      "Error" // Throw Error Code
      //      saveAPIRes(apiId)
    }

    
    if (StringUtil.TestCaseOperation.REMOVE.equals(operartion)) {
      testCaseDAO.pullFromField(testCaseId, "functions", apiConfig.id)
    }
    if (StringUtil.TestCaseOperation.EDIT.equals(operartion)) {
      testCaseDAO.pushToField(testCaseId, "functions", apiConfig.id)
    }
    apiConfigDAO.save(apiConfig)
    
    apiConfig.id
  }

}
