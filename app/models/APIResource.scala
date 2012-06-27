package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field

@BeanInfo case class APIResource(@(JSONTypeHint @field)(value = classOf[APISpec]) apis: List[APISpec], resourcePath : String, apiVersion : String, basePath : String) {
  def this() = { this(List(),"","","") }
  
}

object APIResource{
  def getTableName = "APIResource"
}