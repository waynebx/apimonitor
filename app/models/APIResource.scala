package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

@BeanInfo case class APIResource(@Key("_id") var id:String,@(JSONTypeHint @field)(value = classOf[APISpec])@Ignore apis: List[APISpec], resourcePath : String, apiVersion : String, basePath : String,specIds:List[String]) {
  def this() = { this("",List(),"","","",List()) }
  
}

object APIResource{
  def getTableName = "APIResource"
}