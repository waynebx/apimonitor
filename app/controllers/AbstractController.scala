package controllers

import play.api.modules.spring.Spring
import play.api.mvc.Action
import play.api.mvc.Controller
import service.MyService

class AbstractController extends Controller{
  val myService = Spring.getBeanOfType(classOf[MyService])
  
}