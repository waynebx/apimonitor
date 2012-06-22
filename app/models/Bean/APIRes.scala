package models.Bean

import scala.reflect.BeanProperty

import play.api.libs.json.JsObject
import util.JSONUtil
case class APIRes(_id:String,_path:String,_restPath:String,_method:String,_paramType:String) extends BaseBean{
  	
	id = _id
  	@BeanProperty
  	var path = _path
  	
  	@BeanProperty
  	var restPath = _restPath
  	
  	@BeanProperty
  	var method = _method
  	
  	@BeanProperty
  	var paramType = _paramType
  	
  	@BeanProperty
  	var paramsList = List[String]()
  	
  	def this() ={
  	  this("","","","","")
  	}
	
	def this(_id:String){
	  this(_id,"","","","")
	}
	
	def setParamListFromJsObject(js:JsObject){
	  paramsList =  JSONUtil.getListKeyOfJsonObject(js)
	}
   
}

object APIRes{
  def getTableName = "APIRes"
}