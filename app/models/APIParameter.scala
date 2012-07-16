package models
import scala.reflect.BeanInfo
import models.testcase.BaseBean
import util.StringUtil
import com.novus.salat.annotations._

@BeanInfo case class APIParameter(@Key("_id") var id:BaseKey,
    var name: String = "",
    description : String = "",
    required : Boolean = false,
    dataType: String = "", 
    allowMultiple : Boolean = false,
    paramType : String = "",
    readOnly: Boolean = false,
    var apiId:BaseKey = new BaseKey("",""),
    var value:String = ""){
  
  def this() = {
    this(new BaseKey(),"","",false,"",false,"", false,new BaseKey("",""),"");
  }
}
object APIParameter{
  def getTableName = "Parameter"
}
