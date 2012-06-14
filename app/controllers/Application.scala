package controllers

import play.api.modules.spring.Spring
import play.api.mvc.Action
import play.api.mvc.Controller
import service.MyService

object Application extends Controller {
  
  def index = Action {
    val myService = Spring.getBeanOfType(classOf[MyService])
    myService.go
    
    Ok(views.html.index("Your new application is ready."))
  }
  
}