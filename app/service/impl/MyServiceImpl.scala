package service.impl

import dispatch.json.Js
import models.Bean.MobionTestCase
import models.TestCaseJSON
import service.MyService
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.ConfigUtils
import util.StringUtil
import models.Bean.TestCaseDetail
import models.Bean.APIRes
import models.Dao._
import com.mongodb.casbah.commons.MongoDBObject
import util.JSONUtil
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsArray
import scala.collection.mutable.Seq
import scala.collection.mutable.HashSet

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
	    var idAPI:String = function.id
	    var param:String = function.param
	    var testCaseDetail = new TestCaseDetail
	    testCaseDetail.idAPIRes = idAPI
	    testCaseDetail.params = param
	    listIdDetail::=testCaseDetail._id
	    var apiRes =  apiRestDao.findOne(MongoDBObject("_id" -> idAPI))
	    if(apiRes.isEmpty){
	      var index = idAPI.lastIndexOf("/")
	      var pathMethod:String = idAPI.substring(index)
	      var restPath = idAPI.substring(0,index)
	      var indexMethod = pathMethod.lastIndexOf("__")
	      var path = pathMethod.substring(0,indexMethod)
	      var method = pathMethod.substring(indexMethod+2)
	      var apiResNew = new APIRes(idAPI,path,restPath,method,"")
	      apiRestDao.save(apiResNew)
	    }
	  	testCaseDetailDao.save(testCaseDetail)
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
  
}