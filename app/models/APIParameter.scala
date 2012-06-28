package models
import scala.reflect.BeanInfo
import models.testcase.BaseBean
import util.StringUtil
import com.novus.salat.annotations._

@BeanInfo case class APIParameter(@Key("_id") id:String=StringUtil.generateStringTimeStamp(),
    name: String, description : String, required : Boolean,
    dataType: String, allowMultiple : Boolean, paramType : String,
    readOnly: Boolean,var apiId:String) extends BaseBean(id){
  
  def this() = {
    this(StringUtil.generateStringTimeStamp(),"","",false,"",false,"", false,"");
  }
}
object APIParameter{
  def getTableName = "Parameter"
}
