package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field

@BeanInfo case class APISpec(@(JSONTypeHint @field)(value = classOf[APIOperation]) operations : List[APIOperation], path: String, description : String, resPath : String) {
  
  def this() = {
    this(List(),"","","");
  }
}
