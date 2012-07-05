package models.dao
import util.StringUtil
import util.ConfigUtils
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import play.api.libs.json.JsObject
import models.APIParameter
import models.BaseKey
class APIParameterDAO extends MongoSalatDAO[APIParameter,String](APIParameter.getTableName){
  def getByAPIId(apiId : String) = {
    findbyProperty("apiId", new BaseKey(apiId,ConfigUtils.CURRENT_VERSION))
  }
  
  override def findById(id: String) = {
	 var key = new BaseKey(id,ConfigUtils.CURRENT_VERSION)
	 this.findById(key)
  }
}
