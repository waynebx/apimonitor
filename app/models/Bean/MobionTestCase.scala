package models.Bean
import scala.reflect.BeanProperty
import util.StringUtil
case class MobionTestCase(_id:String,var name:String="", var functions:List[String]=null) extends BaseBean(_id){
  def this(){
  	  this(StringUtil.generateStringTimeStamp(),"",null)
  }
}

object MobionTestCase{
  def getTableName = "TestCaseTable"
}