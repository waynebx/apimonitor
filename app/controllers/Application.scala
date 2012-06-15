package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import util.APIRequestUtils

object Application extends AbstractController{
  
  def index = Action {
    myService.go
    APIRequestUtils.post("accounts/login", Map("email"->"saf", "password"->"sdf"))
    Ok(APIRequestUtils.post("accounts/login", Map("email"->"saf", "password"->"sdf")))
  }
  
}