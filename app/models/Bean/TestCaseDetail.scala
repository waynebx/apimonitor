package models.Bean
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import com.novus.salat.annotations.raw.Salat
import scala.reflect.BeanProperty
import com.novus.salat.annotations.raw.Salat
import com.novus.salat.annotations.raw.Key
case class TestCaseDetail(_id:String,_path:String,_idAPIRes:String,_status:String,_response:String) extends BaseBean{
  	id = _id
  	@BeanProperty
  	var path = _path
  	
  	@BeanProperty
  	var idAPIRes = _idAPIRes
  	
  	@BeanProperty
  	var status = _status
  	
  	@BeanProperty
  	var response = _response
  	
  	@BeanProperty
  	var params = Map[String,String]()
  	
  	def this() ={
  	  this("","","","","")
  	}
}

object TestCaseDetail{
  def getTableName = "TestCaseDetail"
}