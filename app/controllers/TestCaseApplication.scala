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

  //return list test case with id and name
  def getTestcases(start: Int, size: Int) = Action {
    var list = testCaseService.getTestCaseList(start, size);
    println(list);
    Ok(views.html.testcase_list(list))
  }
  
  //get test case detail (with apis)
  def getTestCase(id: String) = Action { request =>
    var value = myService.getTestCaseDetailById(id)
    Ok(value)
  }

}
