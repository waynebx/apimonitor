package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import models.testcase.BaseBean
import com.novus.salat.annotations._

@BeanInfo case class APIOperation(@Key("_id") var id:String, @(JSONTypeHint @field)(value = classOf[APIParameter])@Ignore parameters : List[APIParameter] = null,
    apiParameterIds : List[String] = null,
    httpMethod: String, summary : String, nickname : String, path : String,
    notes: String, responseTypeInternal : String, responseClass : String, apiName : String) extends BaseBean(id){
  
  def this() = {
    this("",null,null,"","","","","","","","");
  }
}

object APIOperation{
  def getTableName = "Operation"
}
