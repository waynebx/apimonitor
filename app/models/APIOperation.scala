package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import models.testcase.BaseBean
import com.novus.salat.annotations._

@BeanInfo case class APIOperation(
  @Key("_id") var versionId:BaseKey,
  var id: String,
  @(JSONTypeHint @field)(value = classOf[APIParameter])@Ignore var parameters: List[APIParameter] = List[APIParameter](),
  var apiParameterIds: List[BaseKey] = List[BaseKey](),
  httpMethod: String = null,
  summary: String = null,
  nickname: String = null,
  path: String = null,
  notes: String = null,
  responseTypeInternal: String = null,
  responseClass: String = null,
  apiName: String = null,
  resPath: String = null,
  var expert_params:String = null,
  @Ignore var expectedParameters: List[APIParameter] = List[APIParameter](),
  @Ignore var apiConfigId:String="") extends BaseBean(id) {


  def this() = {
    this(new BaseKey("",""),"", null, null, "", "", "", "", "", "", "", "", "","");
  }
}

object APIOperation {
  def getTableName = "Operation"
}
