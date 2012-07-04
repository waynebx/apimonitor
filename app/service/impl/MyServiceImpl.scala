package service.impl

import scala.reflect.BeanInfo

import models.testcase.API
import models.testcase.APIConfig
import models.testcase.TestCase
import models.APIOperation
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.AbstractService
import service.MyService
import util.APIRequestUtils
import util.ConfigUtils
import util.JSONUtil
import util.StringUtil

class MyServiceImpl extends MyService with AbstractService {

  def go {
    var op : APIOperation = null;
    
    println("==== GO GO GO ====")
  }
  def getListApi: String = {
    return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + StringUtil.slash + "resources.json", null);
  }

  def getListAPIInRes(rest: String): String = {
    return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + StringUtil.slash + rest + StringUtil.slash + "list_api", null);
  }

  def getListTestCaseDetail(idMobionTestCase: String) = {
    var testCase = testCaseDAO.findById(idMobionTestCase)
    var set = Set[JsValue]()
    if (testCase != None) {
      var functions = testCase.apiConfigIds
      functions.foreach(id => {
        var function = apiConfigDAO.findById(id)
        if (function != None) {
          var api = apiOperationDAO.findById(function.apiId)
          if (api != None) {
            set += JSONUtil.convertAPIConfig(function, api)
          }
        }
      })
    }
    Json.toJson(set)
  }

  def callOneAPI(testCaseDetail: APIConfig, api: API) {
    var fullPath = StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + api.restPath + api.path
    val json: JsValue = Json.parse(testCaseDetail.params)
    val jsonObj = (json \ "params").asInstanceOf[JsObject]
    var map = Map[String, String]()
    if (jsonObj != null && jsonObj.keys != null) {
      jsonObj.keys.foreach(key =>
        {
          map += key -> (jsonObj \ key).as[String]
        })
    }
    if (StringUtil.WebMethod.GET.equals(api.method)) {
      APIRequestUtils.getWS(fullPath, map)
    }
    if (StringUtil.WebMethod.POST.equals(api.method)) {
      APIRequestUtils.post(fullPath, map)
    }
  }

  def getTestCaseList(start: String, size: String): List[TestCase] = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    return testCaseDAO.findLimit(istart, isize)
  }
}