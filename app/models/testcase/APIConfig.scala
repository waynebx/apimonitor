package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
import util.JSONUtil
import com.novus.salat.annotations._

case class APIConfig(
  @Key("_id") id: String = StringUtil.generateStringTimeStamp(),
  var apiId: String = "",
  var status: String = "",
  var response: String = "",
  var params: String = "",
  var expert_params: String = "",
  @Ignore var api: API = null) extends BaseBean(id) {

  def this() = {
    this(StringUtil.generateStringTimeStamp(), "", "", "", "", null)
  }
  def parseParamToMap() = {
    JSONUtil.parseParamInApiConfig(this.params)
  }
  def parseExpertParamToMap() = {
    JSONUtil.parseParamInApiConfig(this.expert_params)
  }
}

object APIConfig {
  def getTableName = "APIConfig"
}