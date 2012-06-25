package models.Dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.APIRes
import play.api.libs.json.JsObject

class APIResDAO extends MongoSalatDAO[APIRes,String](APIRes.getTableName)
