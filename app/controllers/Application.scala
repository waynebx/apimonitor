package controllers

import scala.annotation.target.field
import scala.reflect.BeanInfo

import dispatch.json.Js
import models.APIResource
import models.Res
import models.ResList
import play.api.mvc.Action
import play.api.mvc.Controller
import sjson.json.Serializer.SJSON
import sjson.json._
import util.APIRequestUtils

object Application extends AbstractController {

  def index = Action {

   /* val res = APIRequestUtils.getWS("http://api.sgcharo.com/mobion/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis

    var list = List[APIResource]()
    apis.foreach(api => {
      val path = api.path
      val res2 = APIRequestUtils.getWS("http://api.sgcharo.com/mobion" + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
      
      list ::= SJSON.in[APIResource](Js(res2))
    })*/

    Ok(views.html.index())
  }
  
  def getapi(url : String) = Action{
    var newUrl = url
    
    if(!newUrl.startsWith("http://")){
      newUrl += "http://"
    }
    
    println(newUrl)
    
    val res = APIRequestUtils.getWS(newUrl + "/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis

    var list = List[APIResource]()
    apis.foreach(api => {
      val path = api.path
      val res2 = APIRequestUtils.getWS(newUrl + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
      
      list ::= SJSON.in[APIResource](Js(res2))
    })

    
    Ok(views.html.resources_list(list))
    
  }

}