package models.Bean
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import com.novus.salat.annotations.raw.Salat
import scala.reflect.BeanProperty
import com.novus.salat.annotations.raw.Salat
import com.novus.salat.annotations.raw.Key
case class APIRes(_id:String,_path:String,_restPath:String,_method:String,_paramType:String) extends BaseBean{
  	id = _id
  	@BeanProperty
  	var path = _path
  	
  	@BeanProperty
  	var restPath = _restPath
  	
  	@BeanProperty
  	var method = _method
  	
  	@BeanProperty
  	var paramType = _paramType
  	
  	@BeanProperty
  	var paramsList = List[String]()
  	
  	def this() ={
  	  this("","","","","")
  	}
}

object APIRes{
  def getTableName = "APIRes"
}