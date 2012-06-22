package models
import scala.reflect.BeanInfo

@BeanInfo case class APIParameter(
    name: String, description : String, required : Boolean,
    dataType: String, allowMultiple : Boolean, paramType : String,
    readOnly: Boolean) {
  
  def this() = {
    this("","",false,"",false,"", false);
  }
  
  

}
