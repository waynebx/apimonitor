package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil

case class TestCase(_id:String,var name:String="", var apiConfigIds:List[String]=null, var apiConfigs:List[APIConfig]) extends BaseBean(_id){
  def this(){
  	  this(StringUtil.generateStringTimeStamp(),"",null,null)
  }
}

object TestCase{
  def getTableName = "TestCase"
}