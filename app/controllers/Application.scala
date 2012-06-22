package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import util.APIRequestUtils
import sjson.json._
import sjson.json.Serializer.SJSON
import dispatch.json.Js
import scala.annotation.target.field
import scala.reflect.BeanInfo
import views.html.index$
import models.ResList
import models.APIResource
import models.APISpec
import models.APISpec
import models.Res
import scala.collection.mutable.ListBuffer

object Application extends AbstractController {

  def index = Action {

    val res = APIRequestUtils.getWS("http://api.sgcharo.com/mobion/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis

    var list = List[APIResource]()
    apis.foreach(api => {
      val path = api.path
      val res2 = APIRequestUtils.getWS("http://api.sgcharo.com/mobion" + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
      
      list ::= SJSON.in[APIResource](Js(res2))
    })

    Ok(views.html.index(list))
  }

}