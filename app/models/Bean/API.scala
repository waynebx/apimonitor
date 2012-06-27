package models.Bean

import scala.reflect.BeanProperty
import play.api.libs.json.JsObject
import util.JSONUtil
import util.StringUtil
case class API(_id:String,var path:String="",var restPath:String="", var method:String="",var paramType:String="") extends BaseBean(_id){
	
  	def this() ={
  	  this("","","","","")
  	}
}

object API{
  def getTableName = "APIRes"
}