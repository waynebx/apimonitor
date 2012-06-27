package controllers

import play.api.modules.spring.Spring
import play.api.mvc.Action
import play.api.mvc.Controller
import service.MyService
import service.TestCaseService
import play.api.mvc.Result
import play.api.mvc.PlainResult
import play.api.mvc.SimpleResult

class AbstractController extends Controller {
  val myService = Spring.getBeanOfType(classOf[MyService])
  val testCaseService = Spring.getBeanOfType(classOf[TestCaseService])

  def filterResponse[T](ret: SimpleResult[T]): Result = {
    ret.withHeaders("Access-Control-Allow-Origin" -> "*")
  }
  
  def filterResponse(ret: PlainResult): Result = {
    ret.withHeaders("Access-Control-Allow-Origin" -> "*")
  }
}
