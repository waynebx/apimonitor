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

class TestCaseServiceImpl extends TestCaseService with AbstractService {

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

  def addTestCase(testCaseJSON : JsValue): TestCase = {
    var testCase = SJSON.in[TestCase](testCaseJSON)
    println(testCase.name);
    var apiConfigIds = new ListBuffer[String]
    
    if(testCase.apiConfigs != null && !testCase.apiConfigs.isEmpty){
    	testCase.apiConfigs.foreach(apiConfig => {
    	  apiConfigIds += saveAPIConfig("", apiConfig, StringUtil.TestCaseOperation.ADD)
    	})
    }
    
    testCase.apiConfigIds = apiConfigIds.toList
    testCaseDAO.save(testCase)
    return testCase
  }

  def removeTestCase(body: String) {
    val json = Json.parse(body)
    val id = (json \ "id").as[String]
    var testCase = testCaseDAO.findById(id)
    if (testCase != None) {
      testCaseDAO.remove(testCase)
    }
  }

  def getAPIConfigs(testCaseId: String): List[APIOperation] = {
    var testCase = testCaseDAO.findById(testCaseId)
    var list = new ListBuffer[APIOperation]();

    if (testCase != null) {
      var apiConfigIds = testCase.apiConfigIds
    		  
      apiConfigIds.foreach(id => {
        var apiConfig = apiConfigDAO.findById(id)
        if (apiConfig != null) {
          var api = apiOperationDAO.findById(apiConfig.apiId)
          if (api != null) {
            list += api
          }
        }
      })
    }

    return list.toList;
  }
  
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