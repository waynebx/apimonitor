package models.Bean
import scala.reflect.BeanProperty
import util.StringUtil
case class TestCaseDetail(_id:String,_idAPIRes:String,_status:String,_response:String) extends BaseBean{
  	id = _id  	
  	@BeanProperty
  	var idAPIRes = _idAPIRes
  	
  	@BeanProperty
  	var status = _status
  	
  	@BeanProperty
  	var response = _response
  	
  	@BeanProperty
  	var params = Map[String,String]()
  	
  	def this() ={
  	  this(StringUtil.generateStringTimeStamp(),"","","")
  	}
}

object TestCaseDetail{
  def getTableName = "TestCaseDetail"
}