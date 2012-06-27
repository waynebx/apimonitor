package service.impl

import scala.collection.mutable.ListBuffer
import models.testcase.TestCase
import play.api.libs.json.Json
import service.AbstractService
import service.TestCaseService
import util.JSONUtil
import play.api.libs.json.JsValue
import models.testcase.APIConfig
import models.testcase.API
import sjson.json.Serializer.SJSON
import util.StringUtil
import models.TestCaseJSON
import dispatch.json.Js
import com.mongodb.casbah.commons.MongoDBObject
import models.FunctionJSON

class TestCaseServiceImpl extends TestCaseService with AbstractService {

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

  def getAPIConfigs(testCaseId : String) : List[API]= {
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
  private def saveAPIConfig(testCaseId: String, function: FunctionJSON, operartion: Int) = {
    var apiId: String = function.id
    var param: String = function.param
    var testCaseDetail = new APIConfig
    
    var api = apiDAO.findOne(MongoDBObject("_id" -> apiId))
    if (api.isEmpty) {
      "Error"  // Throw Error Code
//      saveAPIRes(apiId)
    }
    
    testCaseDetail.apiId = apiId
    testCaseDetail.params = param
    if (StringUtil.TestCaseOperation.REMOVE.equals(operartion)) {
      testCaseDAO.pullFromField(testCaseId, "functions", testCaseDetail._id)
    }
    if (StringUtil.TestCaseOperation.EDIT.equals(operartion)) {
      testCaseDAO.pushToField(testCaseId, "functions", testCaseDetail._id)
    }
    apiConfigDAO.save(testCaseDetail)
    testCaseDetail._id
  }
  
  def addTestCase(requestBody: String): String = {
    var testCaseJSON = SJSON.in[TestCaseJSON](Js(requestBody))
    var testCase = new TestCase
    if (StringUtil.isNotBlank(testCase.name)) {
      testCase.name = testCaseJSON.name
    } else {
      testCase.name = ""
    }
    var listIdDetail = List[String]()
    if (testCaseJSON.functions != null && testCaseJSON.functions.length > 0) {
      testCaseJSON.functions.foreach(function => {
        listIdDetail ::= saveAPIConfig("", function, StringUtil.TestCaseOperation.ADD)
      })
    }
    testCase.apiConfigIds = listIdDetail
    testCaseDAO.save(testCase)
    testCase._id
  }
  
  def removeTestCase(body: String) {
    val json = Json.parse(body)
    val id = (json \ "id").as[String]
    var testCase = testCaseDAO.findById(id)
    if (testCase != None) {
      testCaseDAO.remove(testCase)
    }
  }
  
  def getListMobionTestCase(start: String, size: String) = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    var list = testCaseDAO.findLimit(istart, isize)
    list
  }

}