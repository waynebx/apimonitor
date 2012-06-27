package service.impl

import scala.reflect.BeanInfo

import com.mongodb.casbah.commons.MongoDBObject

import dispatch.json.Js
import models.testcase.API
import models.testcase.APIConfig
import models.testcase.TestCase
import models.FunctionJSON
import models.TestCaseJSON
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.AbstractService
import service.MyService
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.ConfigUtils
import util.JSONUtil
import util.StringUtil

class MyServiceImpl extends MyService with AbstractService{
  
  def go {
    println("==== GO GO GO ====")
  }
  def getListApi:String = {
	 return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH +  StringUtil.slash + "resources.json",null);
  }
  
  def getListAPIInRes(rest:String):String = {
	 return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + StringUtil.slash + rest + StringUtil.slash + "list_api",null);
  }

  def addTestCase(requestBody:String):String = {	
	var testCaseJSON = SJSON.in[TestCaseJSON](Js(requestBody))
	var testCase = new TestCase
	if(StringUtil.isNotBlank(testCase.name)){
	  testCase.name =  testCaseJSON.name
	}else{
	   testCase.name = ""
	}
	var listIdDetail = List[String]()
	if(testCaseJSON.functions != null && testCaseJSON.functions.length > 0){
	  testCaseJSON.functions.foreach(function => {
	  	listIdDetail::=saveAPIConfig("",function,StringUtil.TestCaseOperation.ADD)
	  })	  
	}
	testCase.apiConfigIds = listIdDetail
	testCaseDAO.save(testCase)
    testCase._id
  }
  
  def getListTestCaseDetail(idMobionTestCase:String) = {
    var testCase = testCaseDAO.findById(idMobionTestCase)
    var set = Set[JsValue]()
    if (testCase != None) {
      var functions = testCase.apiConfigIds
      functions.foreach(id => {
        var function = apiConfigDAO.findById(id)
        if (function != None) {
          var api = apiDAO.findById(function.apiId)
          if(api != None){
        	  set += JSONUtil.convertAPIConfig(function,api)            
          }
        }
      })
    }
    Json.toJson(set)
  }
  
  def callOneAPI(testCaseDetail:APIConfig,api:API){
    var fullPath = StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + api.restPath + api.path
    val json: JsValue = Json.parse(testCaseDetail.params)
    val jsonObj  = (json \ "params").asInstanceOf[JsObject]
    var map = Map[String,String]()
    if(jsonObj != null && jsonObj.keys != null){
      jsonObj.keys.foreach(key =>
      {
           map += key -> (jsonObj \key).as[String]
      })
    }
    if(StringUtil.WebMethod.GET.equals(api.method)){
      APIRequestUtils.getWS(fullPath,map)
    }
    if(StringUtil.WebMethod.POST.equals(api.method)){
      APIRequestUtils.post(fullPath,map)
    }
  }
  
  def getListMobionTestCase(start:String,size:String) = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    var list = testCaseDAO.findLimit(istart, isize)
    var set = Set[JsValue]()
    list.foreach(item =>
      {
        set += JSONUtil.convertTestCase(item)
      })
    Json.toJson(set)
  }
  
  def getTestCaseList(start:String,size:String) : List[TestCase]= {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    return testCaseDAO.findLimit(istart, isize)
  }

  def removeTestCase(body: String) {
    val json = Json.parse(body)
    val id = (json \ "id").as[String]
    var testCase = testCaseDAO.findById(id)
    if (testCase != None) {
      testCaseDAO.remove(testCase)
    }
  }
  
  private def saveAPIRes(idAPI:String){
    var index = idAPI.lastIndexOf("/")
    var pathMethod: String = idAPI.substring(index)
    var restPath = idAPI.substring(0, index)
    var indexMethod = pathMethod.lastIndexOf("__")
    var path = pathMethod.substring(0, indexMethod)
    var method = pathMethod.substring(indexMethod + 2)
    var apiResNew = new API(idAPI, path, restPath, method, "")
    apiDAO.save(apiResNew)
  }
  
  private def saveAPIConfig(testCaseId:String,function:FunctionJSON,operartion:Int) = {
    var idAPI: String = function.id
    var param: String = function.param
    var testCaseDetail = new APIConfig
    testCaseDetail.apiId = idAPI
    testCaseDetail.params = param
    if(StringUtil.TestCaseOperation.REMOVE.equals(operartion)){
        testCaseDAO.pullFromField(testCaseId, "functions", testCaseDetail._id)
    }
    if(StringUtil.TestCaseOperation.EDIT.equals(operartion)){
        testCaseDAO.pushToField(testCaseId, "functions", testCaseDetail._id)
    }
    var api = apiDAO.findOne(MongoDBObject("_id" -> idAPI))
    if (api.isEmpty) {
      saveAPIRes(idAPI)
    }
    apiConfigDAO.save(testCaseDetail)
    testCaseDetail._id
  }
  
  def removeFunctionInTestCase(body: String) {
	var testCaseJSON = SJSON.in[TestCaseJSON](Js(body))
	val testCaseId = testCaseJSON.id
	if(StringUtil.isBlank(testCaseId)){
	  return
	}
	var testCase = testCaseDAO.findById(testCaseId)
	if(testCase == None){
	  return
	}
    
	if(testCase.apiConfigIds != null && testCase.apiConfigIds.length > 0){
	  testCaseJSON.functions.foreach(function => {
		  saveAPIConfig(testCaseId,function,StringUtil.TestCaseOperation.REMOVE)
	  })	  
	}
  }
  
  def addFunctionInTestCase(body: String) {
    val json = Json.parse(body)
    val testCaseId = (json \ "test_case_id").as[String]
    val functionId = (json \ "function_id").as[String]
    var testCase = testCaseDAO.findById(testCaseId)
    if (testCase != None) {
      testCaseDAO.pushToField(testCaseId,"functions",functionId)
    }
  }
  
  def getTestCaseDetailById(id:String) = {
    var function = apiConfigDAO.findById(id)
    var jsonObj = Json.toJson("")
    if (function != None) {
      var api = apiDAO.findById(function.apiId)
      if (api != None) {
         jsonObj = JSONUtil.convertAPIConfig(function, api)
      }
    }
    jsonObj
  }
  
}