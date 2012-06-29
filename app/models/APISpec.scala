package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

@BeanInfo case class APISpec(@Key("_id") var id:String,@(JSONTypeHint @field)(value = classOf[APIOperation]) operations : List[APIOperation], path: String, description : String, resPath : String,operationsId:List[String]) {
  
  def this() = {
    this("",List(),"","","",List());
  }
}
