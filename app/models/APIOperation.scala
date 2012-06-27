package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import models.testcase.BaseBean

@BeanInfo case class APIOperation(_id:String, @(JSONTypeHint @field)(value = classOf[APIParameter]) parameters : List[APIParameter] = null,
    apiParameterIds : List[String] = null,
    httpMethod: String, summary : String, nickname : String, path : String,
    notes: String, responseTypeInternal : String, responseClass : String, apiName : String) extends BaseBean(_id){
  
  def this() = {
    this("",null,null,"","","","","","","","");
  }
}

object APIOperation{
  def getTableName = "API"
}
