package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import dispatch.json.Js

object TestCaseApplication extends AbstractController {

  //return list test case with id and name
  def getTestcases(start: Int, size: Int) = Action {
    var list = testCaseService.getTestCaseList(start, size);
    Ok(views.html.testcase_list(list))
  }

  //get test case detail (with apis)
  def getTestCase(id: String) = Action { request =>
    var value = myService.getTestCaseDetailById(id)
    Ok(value)
  }

  def addTestCase = Action(parse.json) { request =>
    
    println("---------" + request.body)
    var testCase = testCaseService.addTestCase(Js(request.body.toString()))
    
    
    Ok(views.html.template1(testCase._id,testCase.name,"testcase"))
    
    
  }

}

