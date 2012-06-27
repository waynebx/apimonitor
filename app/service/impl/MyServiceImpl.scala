package service.impl

import scala.reflect.BeanInfo
import com.mongodb.casbah.commons.MongoDBObject
import dispatch.json.Js
import models.Bean.APIRes
import models.Bean.MobionTestCase
import models.Bean.TestCaseDetail
import models.Dao.APIResDAO
import models.Dao.MobionTestcaseDAO
import models.Dao.TestCaseDetailDAO
import models.FunctionJSON
import models.TestCaseJSON
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.MyService
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.ConfigUtils
import util.JSONUtil
import util.StringUtil

class MyServiceImpl extends MyService {
  var mobionTestCaseDao = new MobionTestcaseDAO
  var testCaseDetailDao = new TestCaseDetailDAO
  var apiRestDao        = new APIResDAO
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
	var testCase = SJSON.in[TestCaseJSON](Js(requestBody))
	var mobionTestCase = new MobionTestCase
	if(StringUtil.isNotBlank(testCase.name)){
	  mobionTestCase.name =  testCase.name
	}else{
	   mobionTestCase.name = ""
	}
	var listIdDetail = List[String]()
	if(testCase.functions != null && testCase.functions.length > 0){
	  testCase.functions.foreach(function => {
	  	listIdDetail::=saveTestCaseDetail("",function,StringUtil.TestCaseOperation.ADD)
	  })	  
	}
	mobionTestCase.functions = listIdDetail
	mobionTestCaseDao.save(mobionTestCase)
    mobionTestCase._id
  }
  
  def getListTestCaseDetail(idMobionTestCase:String) = {
    var mobionTestCase = mobionTestCaseDao.findByStringId(idMobionTestCase)
    var set = Set[JsValue]()
    if (mobionTestCase != None) {
      var functions = mobionTestCase.get.functions
      functions.foreach(id => {
        var function = testCaseDetailDao.findByStringId(id)
        if (function != None) {
          var apiRes = apiRestDao.findByStringId(function.get.idAPIRes)
          if(apiRes != None){
        	  set += JSONUtil.convertTestCaseDetail(function.get,apiRes.get)            
          }
        }
      })
    }
    Json.toJson(set)
  }
  
  def callOneAPI(testCaseDetail:TestCaseDetail,apiRes:APIRes){
    var fullPath = StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + apiRes.restPath + apiRes.path
    val json: JsValue = Json.parse(testCaseDetail.params)
    val jsonObj  = (json \ "params").asInstanceOf[JsObject]
    var map = Map[String,String]()
    if(jsonObj != null && jsonObj.keys != null){
      jsonObj.keys.foreach(key =>
      {
           map += key -> (jsonObj \key).as[String]
      })
    }
    if(StringUtil.WebMethod.GET.equals(apiRes.method)){
      APIRequestUtils.getWS(fullPath,map)
    }
    if(StringUtil.WebMethod.POST.equals(apiRes.method)){
      APIRequestUtils.post(fullPath,map)
    }
  }
  
  def getListMobionTestCase(start:String,size:String) = {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    var list = mobionTestCaseDao.findLimit(istart, isize)
    var set = Set[JsValue]()
    list.foreach(item =>
      {
        set += JSONUtil.convertMobionTestCase(item)
      })
    Json.toJson(set)
  }
  
  def getTestCaseList(start:String,size:String) : List[MobionTestCase]= {
    var istart: Int = start.toInt
    var isize: Int = size.toInt
    return mobionTestCaseDao.findLimit(istart, isize)
  }

  def removeTestCase(body: String) {
    val json = Json.parse(body)
    val id = (json \ "id").as[String]
    var mobionTestCase = mobionTestCaseDao.findByStringId(id)
    if (mobionTestCase != None) {
      mobionTestCaseDao.remove(mobionTestCase.get)
    }
  }
  
  private def saveAPIRes(idAPI:String){
    var index = idAPI.lastIndexOf("/")
    var pathMethod: String = idAPI.substring(index)
    var restPath = idAPI.substring(0, index)
    var indexMethod = pathMethod.lastIndexOf("__")
    var path = pathMethod.substring(0, indexMethod)
    var method = pathMethod.substring(indexMethod + 2)
    var apiResNew = new APIRes(idAPI, path, restPath, method, "")
    apiRestDao.save(apiResNew)
  }
  
  private def saveTestCaseDetail(testCaseId:String,function:FunctionJSON,operartion:Int) = {
    var idAPI: String = function.id
    var param: String = function.param
    var testCaseDetail = new TestCaseDetail
    testCaseDetail.idAPIRes = idAPI
    testCaseDetail.params = param
    if(StringUtil.TestCaseOperation.REMOVE.equals(operartion)){
        mobionTestCaseDao.pullFromField(testCaseId, "functions", testCaseDetail._id)
    }
    if(StringUtil.TestCaseOperation.EDIT.equals(operartion)){
        mobionTestCaseDao.pushToField(testCaseId, "functions", testCaseDetail._id)
    }
    var apiRes = apiRestDao.findOne(MongoDBObject("_id" -> idAPI))
    if (apiRes.isEmpty) {
      saveAPIRes(idAPI)
    }
    testCaseDetailDao.save(testCaseDetail)
    testCaseDetail._id
  }
  
  def removeFunctionInTestCase(body: String) {
	var testCase = SJSON.in[TestCaseJSON](Js(body))
	val testCaseId = testCase.id
	if(StringUtil.isBlank(testCaseId)){
	  return
	}
	var mobionTestCase = mobionTestCaseDao.findByStringId(testCaseId)
	if(mobionTestCase == None){
	  return
	}
    
	if(testCase.functions != null && testCase.functions.length > 0){
	  testCase.functions.foreach(function => {
		  saveTestCaseDetail(testCaseId,function,StringUtil.TestCaseOperation.REMOVE)
	  })	  
	}
  }
  
  def addFunctionInTestCase(body: String) {
    val json = Json.parse(body)
    val testCaseId = (json \ "test_case_id").as[String]
    val functionId = (json \ "function_id").as[String]
    var mobionTestCase = mobionTestCaseDao.findByStringId(testCaseId)
    if (mobionTestCase != None) {
      mobionTestCaseDao.pushToField(testCaseId,"functions",functionId)
    }
  }
  
  def getTestCaseDetailById(id:String) = {
    var function = testCaseDetailDao.findByStringId(id)
    var jsonObj = Json.toJson("")
    if (function != None) {
      var apiRes = apiRestDao.findByStringId(function.get.idAPIRes)
      if (apiRes != None) {
         jsonObj = JSONUtil.convertTestCaseDetail(function.get, apiRes.get)
      }
    }
    jsonObj
  }
  
}