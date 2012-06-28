package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import dispatch.json.Js
import sjson.json.Serializer.SJSON
import models.testcase.TestCase

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
    
    var testCase = SJSON.in[TestCase](Js(request.body.toString()))
    testCase = testCaseService.addTestCase(testCase)
    
    Ok(views.html.testcase(testCase.id,testCase.name))

  }
  
  def deleteTestCase(id : String) = Action {
    testCaseService.deleteTestCase(id);
    Ok("OK");
    
  }
  
/*  def addAPI2TestCase = Action(parse.json){request =>
    
  }
*/
}

