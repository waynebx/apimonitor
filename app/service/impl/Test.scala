package service.impl
import models.Bean.MobionTestCase
import models.Dao.MobionTestcaseDAO
import play.api.libs.json.JsNull
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import util.StringUtil
import play.api.modules.spring.Spring
import service.MyService
import models.TestCaseJSON
import sjson.json.Serializer.SJSON
import models.FunctionJSON
import sjson.json.Serializer
import models.Bean.TestCaseDetail
import models.Dao.TestCaseDetailDAO
import scala.collection.mutable.HashMap

class OptionalUserProfileInfo(
	location: String = "",
	age: Int= 0,
	webSite: String = ""
	  )


object TestService {

	def main(args: Array[String]) {
		val myService = new MyServiceImpl()
		var abc = new TestCaseJSON
		var hahah = new FunctionJSON
		hahah.id="/v2/auth/check__GET"
		hahah.param = "sasas"
		var hihihi = new FunctionJSON
		hihihi.id = "/v2/auth/list_api__GET"
		hihihi.param = "adas"
		var list = List[FunctionJSON]()
		list::=hihihi
		list::=hahah
		abc.name = "avxc"
		abc.functions = list
		var xyz = SJSON.toJSON(abc)
		print(xyz)

//		val jsonObject = Json.toJson(
//		  Map("test_case_id" -> "1340608378882","function_id" ->"1340608378882")
//		)
//		
//		print(jsonObject)
//		myService.addFunctionInTestCase(jsonObject.toString())
//		myService.removeFunctionInTestCase(jsonObject.toString())
		var optionalUser = new OptionalUserProfileInfo(age = 19)
		val intToStringMap2 = new HashMap[Integer, String]
  
	}
}