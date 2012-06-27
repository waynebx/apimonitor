package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
case class APIConfig(_id:String,var apiId:String,var status:String="",var response:String="",var params:String="") extends BaseBean(_id){	
  	def this() ={
  	  this(StringUtil.generateStringTimeStamp(),"","","","")
  	}
}

object APIConfig{
  def getTableName = "APIConfig"
}