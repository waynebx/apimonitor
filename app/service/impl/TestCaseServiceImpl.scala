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
    //testCaseDAO.save(testCase)
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

  def getAPIConfigs(testCaseId: String): List[API] = {
    var testCase = testCaseDAO.findById(testCaseId)
    var list = new ListBuffer[API]();

    if (testCase != null) {
      var apiConfigIds = testCase.apiConfigIds

      apiConfigIds.foreach(id => {
        var apiConfig = apiConfigDAO.findById(id)
        if (apiConfig != null) {
          var api = apiDAO.findById(apiConfig.apiId)
          if (api != null) {
            list += api;
          }
        }
      })
    }

    return list.toList;
  }

  private def saveAPIRes(idAPI: String) {
    var index = idAPI.lastIndexOf("/")
    var pathMethod: String = idAPI.substring(index)
    var restPath = idAPI.substring(0, index)
    var indexMethod = pathMethod.lastIndexOf("__")
    var path = pathMethod.substring(0, indexMethod)
    var method = pathMethod.substring(indexMethod + 2)
    var apiResNew = new API(idAPI, path, restPath, method, "")
    apiDAO.save(apiResNew)
  }
  
  private def saveAPIConfig(testCaseId: String, apiConfig: APIConfig, operartion: Int) = {
    var api = apiDAO.findOne(MongoDBObject("_id" -> apiConfig.apiId))
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