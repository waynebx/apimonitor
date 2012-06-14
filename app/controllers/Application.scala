package controllers

import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends AbstractController{
  
  def index = Action {
    myService.go
    Ok(views.html.index("Your new application is ready."))
  }
  
}