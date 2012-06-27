package service.impl

import scala.reflect.BeanInfo
import com.mongodb.casbah.commons.MongoDBObject
import dispatch.json.Js
import models.testcase.API
import models.testcase.APIConfig
import models.testcase.TestCase
import models.FunctionJSON
import models.TestCaseJSON
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import service.AbstractService
import service.MyService
import sjson.json.Serializer.SJSON
import util.APIRequestUtils
import util.ConfigUtils
import util.JSONUtil
import util.StringUtil
import service.ParameterService
import models.APIParameter

class ParameterServiceImpl extends ParameterService with AbstractService {
  def saveParameterObj(param:APIParameter,apiId:String){
    param.apiId = apiId
    apiParameterDAO.save(param)
  }
}