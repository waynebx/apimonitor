package models
import scala.reflect.BeanInfo

@BeanInfo case class FunctionJSON(var id: String,var param:String) {
  
  def this() = {
    this("","");
  }
}