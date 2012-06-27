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

}