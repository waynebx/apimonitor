package models.dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import play.api.libs.json.JsObject
import models.APIOperation

class APIOperationDAO extends MongoSalatDAO[APIOperation,String](APIOperation.getTableName)
