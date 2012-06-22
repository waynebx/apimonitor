package models.Dao
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.TestCaseDetail
class TestCaseDetailDAO extends MongoSalatDAO[TestCaseDetail,String](TestCaseDetail.getTableName)