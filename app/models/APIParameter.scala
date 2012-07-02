package models
import scala.reflect.BeanInfo
import models.testcase.BaseBean
import util.StringUtil
import com.novus.salat.annotations._

@BeanInfo case class APIParameter(@Key("_id") var id:String,
    name: String = "",
    description : String = "",
    required : Boolean = false,
    dataType: String = "", 
    allowMultiple : Boolean = false,
    paramType : String = "",
    readOnly: Boolean = false,
    var apiId:String = "",
    var value:String = ""){
  
  def this() = {
    this(StringUtil.generateStringTimeStamp(),"","",false,"",false,"", false,"","");
  }
}
object APIParameter{
  def getTableName = "Parameter"
}
