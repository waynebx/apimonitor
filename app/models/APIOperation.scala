package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field

@BeanInfo case class APIOperation(@(JSONTypeHint @field)(value = classOf[APIParameter]) parameters : List[APIParameter],
    httpMethod: String, summary : String, nickname : String, path : String,
    notes: String, responseTypeInternal : String, responseClass : String, apiName : String) {
  
  def this() = {
    this(List(),"","","","","","","","");
  }
  

}
