package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field

@BeanInfo case class TestCaseJSON(var name: String,@(JSONTypeHint @field)(value = classOf[FunctionJSON])var functions: List[FunctionJSON]) {
  
  def this() = {
    this("",null);
  }
}