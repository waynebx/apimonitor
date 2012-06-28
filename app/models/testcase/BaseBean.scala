package models.testcase
import scala.reflect.BeanProperty
import util.StringUtil
import com.novus.salat.annotations._
abstract class BaseBean(@Key("_id") id:String) extends Serializable{
	
}

object BaseBean{
  def getTableName = "BaseBean"
}