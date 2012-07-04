package models.dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.VersionTracking
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.query
class VersionTrackingDAO extends MongoSalatDAO[VersionTracking,String](VersionTracking.getTableName)