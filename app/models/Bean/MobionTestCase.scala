package models.Bean
import scala.reflect.BeanProperty
import util.StringUtil
case class MobionTestCase(_id:String,_name:String) extends BaseBean{
  	id = _id
  	@BeanProperty
  	var name = _name
  	
  	@BeanProperty
  	var listFunction:List[String] = List[String]()
  	
  	def this(){
  	  this(StringUtil.generateStringTimeStamp(),"")
  	}
  	
  	def this(_name:String){
  	  this(null,_name)
  	}
}

object MobionTestCase{
  def getTableName = "TestCaseTable"
}