package models.Dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.MobionTestCase
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.query
class MobionTestcaseDAO extends MongoSalatDAO[MobionTestCase,String](MobionTestCase.getTableName)