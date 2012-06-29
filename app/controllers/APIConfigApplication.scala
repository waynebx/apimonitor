package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import dispatch.json.Js
import sjson.json.Serializer.SJSON
import models.testcase.TestCase
import util.StringUtil
import util.ConfigUtils
import util.APIRequestUtils
import models.Res
import models.ResList
import models.APIResource

object APIConfigApplication extends AbstractController {
  def getAPIConfigs(id: String) = Action { 
    val value = apiConfigService.getAPIConfigs(id)
    filterResponse(Ok("OK"))
  }
}

