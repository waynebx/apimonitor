package models.Bean
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import com.novus.salat.annotations.raw.Salat
import scala.reflect.BeanProperty
import com.novus.salat.annotations.raw.Salat
import com.novus.salat.annotations.raw.Key
case class MobionTestCase(_id:String,_name:String) extends BaseBean{
  	id = _id
  	@BeanProperty
  	var name = _name
  	
  	@BeanProperty
  	var listFunction:List[APIRes] = null
}

object MobionTestCase{
  def getTableName = "TestCaseTable"
}