package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

@BeanInfo case class APIResource(
    @Key("_id") var id:BaseKey = null,
    @(JSONTypeHint @field)(value = classOf[APISpec])@Ignore var apis: List[APISpec]=List[APISpec](), 
    var resourcePath : String, 
    apiVersion : String, 
    basePath : String,
    var specIds:List[BaseKey]=List[BaseKey]()) {
  def this() = { this(null,List(),"","","",List()) }
  
}

object APIResource{
  def getTableName = "APIResource"
}