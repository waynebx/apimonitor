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

object TestCaseApplication extends AbstractController {

  //return list test case with id and name
  def getTestcases(start: Int, size: Int) = Action {
    var list = testCaseService.getTestCaseList(start, size);
    Ok(views.html.testcase_list(list))
  }


  def getAPIsinTestCase(id: String) = Action { request =>

    Ok(views.html.apis_list_in_testcase(id, testCaseService.getAPIsinTestCase(id)))

  }

  def addTestCase = Action(parse.json) { request =>

    var testCase = SJSON.in[TestCase](Js(request.body.toString()))
    testCase = testCaseService.addTestCase(testCase)

    Ok(views.html.testcase(testCase.id, testCase.name))

  }

  def addAPI2TestCase = Action(parse.json) { request =>

    println("add api to test case : " + request.body.toString())
    var testCase = SJSON.in[TestCase](Js(request.body.toString()))

    Ok(views.html.apis_list_in_testcase(testCase.id, testCaseService.addAPI2TestCase(testCase)))

  }

  def deleteTestCase(id: String) = Action {
    testCaseService.deleteTestCase(id);
    Ok("OK");

  }

/*
  def removeFunctionInTestCase = Action(parse.text) { request =>
    var testCase = SJSON.in[TestCase](Js(request.body.toString()))
    testCaseService.removeFunctionInTestCase(testCase)
    filterResponse(Ok(Json.toJson(
      Map("status" -> "success"))))
  }
  
  def addFunctionInTestCase = Action(parse.text) { request =>
    var testCase = SJSON.in[TestCase](Js(request.body.toString()))
    testCaseService.addFunctionInTestCase(testCase)
    filterResponse(Ok(Json.toJson(
      Map("status" -> "success"))))
  }
  
  def editFunctionInTestCase = Action(parse.text) { request =>
    var testCase = SJSON.in[TestCase](Js(request.body.toString()))
    testCaseService.editFunctionInTestCase(testCase)
    filterResponse(Ok(Json.toJson(
      Map("status" -> "success"))))
  }
*/
    
  def buildAPIDatabase = Action {
    println(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH  + "/resources.json")
    val res = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH  + "/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis
    var list = List[APIResource]()
    apis.foreach(api => {
      val path = api.path
      println(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH +  path  + "/list_api?api_key=a3633f30bb4a11e18887005056a70023")
      val res2 = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH  + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
      list ::= SJSON.in[APIResource](Js(res2))
      parameterService.buildAPIAndParameter(SJSON.in[APIResource](Js(res2)))
    })
    filterResponse(Ok(Json.toJson(
      Map("status" -> "success"))))
  }
  
  def getListMobionTestCase(start: String, size: String) = Action { request =>
    var value = testCaseService.getListMobionTestCase(start, size)
    filterResponse(Ok("OK"))
  }
  

}

