package models.dao
import util.ConfigUtils
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import play.api.libs.json.JsObject
import models.APIOperation
import com.mongodb.casbah.commons.MongoDBObject
import models.BaseKey


class APIOperationDAO extends MongoSalatDAO[APIOperation, String](APIOperation.getTableName) {
  override def findById(id: String) = {
	 var key = new BaseKey(id,ConfigUtils.CURRENT_VERSION)
	 this.findById(key)
  }
}
