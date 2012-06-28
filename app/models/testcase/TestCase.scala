package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

case class TestCase(
    _id:String,
    var name:String="", 
    var apiConfigIds:List[String]=null, 
    @(JSONTypeHint @field())(value = classOf[APIConfig]) @Ignore var apiConfigs:List[APIConfig] = null) extends BaseBean(_id){
  
  def this(){
  	  this(StringUtil.generateStringTimeStamp(),"",null,null)
  }
}

object TestCase{
  def getTableName = "TestCase"
}