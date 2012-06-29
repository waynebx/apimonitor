package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

case class TestCase(
    @Key("_id") id:String,
    var name:String="", 
    var apiConfigIds:List[String]=null, 
    @Ignore @(JSONTypeHint @field)(value = classOf[APIConfig]) var apiConfigs:List[APIConfig] = null) extends BaseBean(id){

  
  def this(){
  	  this(StringUtil.generateStringTimeStamp(),"",null,null)
  }
}

object TestCase{
  def getTableName = "TestCase"
}