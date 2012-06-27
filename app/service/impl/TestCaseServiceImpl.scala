package service.impl

import scala.reflect.BeanInfo

import models.Bean.TestCase
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.AbstractService
import service.TestCaseService
import util.JSONUtil

class TestCaseServiceImpl extends TestCaseService with AbstractService{
  

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

  def getTestCase(id: String) = {
    var function = apiConfigDAO.findByStringId(id)
    var jsonObj = Json.toJson("")
    if (function != None) {
      var apiRes = apiDAO.findByStringId(function.idAPIRes)
      if (apiRes != None) {
        jsonObj = JSONUtil.convertTestCaseDetail(function, apiRes)
      }
    }
    jsonObj
  }
}