package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
import com.novus.salat.annotations._

case class APIConfig(@Key("_id") id:String,var apiId:String="",var status:String="",var response:String="",var params:String="") extends BaseBean(id){	
  	def this() ={
  	  this(StringUtil.generateStringTimeStamp(),"","","","")
  	}
}

object APIConfig{
  def getTableName = "APIConfig"
}