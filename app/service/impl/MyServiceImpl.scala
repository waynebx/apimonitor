package service.impl

import service.MyService
import util.APIRequestUtils
import util.ConfigUtils
import util.StringUtil

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

}