package controllers

import play.api.modules.spring.Spring
import play.api.mvc.Action
import play.api.mvc.Controller
import service.MyService
import play.api.mvc.SimpleResult
import play.api.mvc.PlainResult
import play.api.mvc.Result

class AbstractController extends Controller{
  val myService = Spring.getBeanOfType(classOf[MyService])
  
    def filterResponse[T](ret: SimpleResult[T]): Result = {
    ret.withHeaders("Access-Control-Allow-Origin" -> "*")
  }
  
  def filterResponse(ret: PlainResult): Result = {
    ret.withHeaders("Access-Control-Allow-Origin" -> "*")
  }
}