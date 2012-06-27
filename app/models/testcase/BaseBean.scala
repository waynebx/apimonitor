package models.testcase
import scala.reflect.BeanProperty
import com.novus.salat.annotations.raw.Key
import util.StringUtil
abstract class BaseBean(@Key("_id") _id:String) extends Serializable{
	
}

object BaseBean{
  def getTableName = "BaseBean"
}