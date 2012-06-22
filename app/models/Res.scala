package models
import scala.reflect.BeanInfo

@BeanInfo case class Res(path: String) {
  
  def this() = {
    this("");
  }
}