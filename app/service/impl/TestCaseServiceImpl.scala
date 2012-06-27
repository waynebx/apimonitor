package service.impl

import scala.reflect.BeanInfo
import com.mongodb.casbah.commons.MongoDBObject
import dispatch.json.Js
import models.Bean.APIRes
import models.Bean.MobionTestCase
import models.Bean.TestCaseDetail
import models.Dao.APIResDAO
import models.Dao.MobionTestcaseDAO
import models.Dao.TestCaseDetailDAO
import models.FunctionJSON
import models.TestCaseJSON
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.MyService
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.ConfigUtils
import util.JSONUtil
import util.StringUtil
import service.TestCaseService

class TestCaseServiceImpl extends TestCaseService {
  var mobionTestCaseDao = new MobionTestcaseDAO
  var testCaseDetailDao = new TestCaseDetailDAO
  var apiRestDao        = new APIResDAO
  
  def getTestCaseList(start:Int,size:Int) : List[MobionTestCase]= {
    
    return mobionTestCaseDao.findLimit(start, size)
  }
}