package controllers

import dispatch.json.Js
import models.APIResource
import models.Res
import models.ResList
import play.api.mvc.Action
import sjson.json.Serializer.SJSON
import util.APIRequestUtils

object Application extends AbstractController {

  def index = Action {
	  
    filterResponse(Ok(views.html.index()))
    
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

    filterResponse(Ok(myService.getListApi).as("text/plain"))
  }

  def getListAPIInRest(rest: String) = Action {
    filterResponse(Ok(myService.getListAPIInRes(rest)).as("text/plain"))
  }

  def getListTestCaseDetail(id: String) = Action { request =>
    var value = myService.getListTestCaseDetail(id)
    filterResponse(Ok(value))
  }

  def getListMobionTestCase(start: String, size: String) = Action { request =>
    var value = myService.getListMobionTestCase(start, size)
    filterResponse(Ok(value))
  }
  
  def getTestCaseDetailById(id: String) = Action { request =>
    var value = myService.getTestCaseDetailById(id)
    filterResponse(Ok(value))
  }
}
