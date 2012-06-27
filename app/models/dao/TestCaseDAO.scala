package models.dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.TestCase
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.query
class TestCaseDAO extends MongoSalatDAO[TestCase,String](TestCase.getTableName)