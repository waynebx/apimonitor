package models
import scala.reflect.BeanInfo
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import com.novus.salat.annotations._

@BeanInfo case class VersionTracking(@Key("_id") var id:String,var paths:List[String]= List[String]()) {
  
  def this() = {
    this("",List[String]())
  }
}

object VersionTracking{
  def getTableName = "Version"
}