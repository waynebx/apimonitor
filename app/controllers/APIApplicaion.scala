package controllers
import dispatch.json.Js
import models.APIResource
import models.Res
import models.ResList
import play.api.mvc.Action
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.StringUtil

object APIApplication extends AbstractController {
  
  def getapi(url: String) = Action {
    var newUrl = url
    
    if (!newUrl.startsWith("http://")) {
      newUrl += "http://"
    }
    newUrl = "http://api.sgcharo.com/mobion"
    println(newUrl + "/resources.json")
    val res = APIRequestUtils.getWS(newUrl + "/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis

    var list = List[APIResource]()

    apis.foreach(api => {
      val path = "/v2" + api.path
//      val res2 = APIRequestUtils.getWS(newUrl + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
//
//      list ::= SJSON.in[APIResource](Js(res2))
      val id = path;
    	println(id);
    	list ::= apiResourceService.getAPIResource(id);
    })

    Ok(views.html.resources_list(list))
  }
  
  def getResources(start: String, size: String, rest: String) {
    var iStart = 0
    var iSize = 10
    if (StringUtil.isNotBlank(start)) {
      iStart = start.toInt
    }
    if (StringUtil.isNotBlank(size)) {
      iSize = size.toInt
    }
    
    apiResourceService.getAPIResources(iStart, iSize, rest,"")
    
  }
}
