package service.impl

import service.MyService
import util.APIRequestUtils
import util.ConfigUtils
import util.StringUtil
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import models.Bean.MobionTestCase
import models.Bean.APIRes
import models.Bean.TestCaseDetail
import play.api.libs.json.JsValue
import play.api.libs.json.JsArray
import play.api.libs.json.JsObject


class MyServiceImpl extends MyService {

  def go {
    println("==== GO GO GO ====")
  }
  def getListApi:String = {
	 return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH +  StringUtil.slash + "resources.json",null);
  }
  
  def getListAPIInRes(rest:String):String = {
	 return APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + StringUtil.slash + rest + StringUtil.slash + "list_api",null);
  }

  def addTestCase(requestBody:JsValue){
     /* val request = Json.parse(strRequest).as[TestCaseJson]
      val mobionTestCase:MobionTestCase  = new MobionTestCase
      mobionTestCase.setName(request.name)
      var apiRes = new APIRes
      apiRes.setPath(request.path)
      apiRes.setMethod(request.method)
      apiRes.setRestPath(request.resPath)
      apiRes.setParamListFromJsObject(request.params)
      var params:JsObject = request.params
      var map = Map[String,String]()
	  if(params != null && params.keys != null){
	    for(key <- params.keys){
	      var value = (params \ key).as[String]
	      map += (key -> value)
	    }
	  }

      var detail = new TestCaseDetail
      detail.setIdAPIRes(apiRes.getId())*/
      
  }
}