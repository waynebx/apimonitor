package util

import dispatch._
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.ws._
import play.api.libs.concurrent.Promise
import play.api.libs.ws.WS.WSRequestHolder
import play.api.libs.json.JsValue



object APIRequestUtils {

  def post(path: String, params: Map[String, String]): String = {
    var res = ""
    Http(:/(ConfigUtils.API_DEFAULT_HOST, ConfigUtils.API_DEFAULT_PORT)
      / ConfigUtils.API_DEFAULT_PATH / path
      << params >- { str =>
        res = str
      })
    return res
  }

  def get(path: String, params: Map[String, String]): String = {
    var res = ""
    Http(:/(ConfigUtils.API_DEFAULT_HOST, ConfigUtils.API_DEFAULT_PORT)
      / ConfigUtils.API_DEFAULT_PATH / path
      <<? params >- { str =>
        res = str
      })
    return res
  }
  
  //    Play WS
  def postWS(path: String, params: Map[String, Seq[String]]): String = {
    var res = ""
    val result: Promise[Response] = {
    			WS.url(path).post(params)
    	}
    res = result.value.get.body
    return res;
  }
  
  def getWS(path: String, params: Map[String,String]): String = {
    var res = ""
    val result = {
    		var request:WSRequestHolder = WS.url(path)
    		request.withHeaders(("X-GNT-Auth-Token","a3633f30bb4a11e18887005056a70023"))
    		params.foreach { param: (String, String) => request.withQueryString((param._1, param._2)) }
    		request.get()
    	}
    result.value.get.json.toString()
    
  }
}