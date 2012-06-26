package controllers

import dispatch.json.Js
import models.APIResource
import models.Res
import models.ResList
import play.api.libs.json.Json
import play.api.mvc.Action
import sjson.json.Serializer.SJSON
import util.APIRequestUtils

object Application extends AbstractController {

  def index = Action {

    Ok(views.html.index())
  }
  
  def getapi(url: String) = Action {
    var newUrl = url

    if (!newUrl.startsWith("http://")) {
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

  def getListAPI = Action {

    Ok(myService.getListApi).as("text/plain")
  }

  def getListAPIInRest(rest: String) = Action {
    Ok(myService.getListAPIInRes(rest)).as("text/plain")
  }

  def addTestCase = Action(parse.text) { request =>
    var id = myService.addTestCase(request.body)
    Ok(Json.toJson(
      Map("status" -> "success", "id" -> id)))
  }

  def getListTestCaseDetail(id: String) = Action { request =>
    var value = myService.getListTestCaseDetail(id)
    Ok(value)
  }

  def getListMobionTestCase(start: String, size: String) = Action { request =>
    var value = myService.getListMobionTestCase(start, size)
    Ok(value)
  }
  
  def removeTestCase = Action(parse.text) {request =>
  	 myService.removeTestCase(request.body)
     Ok(Json.toJson(
      Map("status" -> "success")))
  }
  
  def addFunctionInTestCase = Action(parse.text) {request =>
  	 var id =myService.addFunctionInTestCase(request.body)
     Ok(Json.toJson(
      Map("status" -> "success")))
  }
  
  def removeFunctionInTestCase = Action(parse.text) {request =>
  	 var id =myService.removeFunctionInTestCase(request.body)
     Ok(Json.toJson(
      Map("status" -> "success")))
  }

  def getTestCaseDetailById(id: String) = Action { request =>
    var value = myService.getTestCaseDetailById(id)
    Ok(value)
  }
}
