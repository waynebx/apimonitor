package models.Bean
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import com.novus.salat.annotations.raw.Salat
import scala.reflect.BeanProperty
import com.novus.salat.annotations.raw.Salat
import com.novus.salat.annotations.raw.Key
abstract class BaseBean extends Serializable{
  @BeanProperty
  @Key("_id")
  var id:String = ""
}

object BaseBean{
  def getTableName = "BaseBean"
}