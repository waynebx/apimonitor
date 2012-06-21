package models.Dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.MobionTestCase

class MobionTestcaseDAO extends MongoSalatDAO[MobionTestCase,String](MobionTestCase.getTableName)