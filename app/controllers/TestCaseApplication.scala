package controllers

import dispatch.json.Js
import models.APIResource
import models.Res
import models.ResList
import play.api.libs.json.Json
import play.api.mvc.Action
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import views.html.testcase_list

object TestCaseApplication extends AbstractController {

  def getTestcases(start: Int, size: Int) = Action {
	 var list = testCaseService.getTestCaseList(start, size);
    println(list);
	 Ok(views.html.testcase_list(list))
  }
  
}
