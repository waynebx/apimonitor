package models.Bean
import scala.reflect.BeanProperty

import com.novus.salat.annotations.raw.Key
abstract class BaseBean extends Serializable{
  @BeanProperty
  @Key("_id")
  var id:String = null
}

object BaseBean{
  def getTableName = "BaseBean"
}