package service
import play.api.modules.spring.Spring
import models.dao.APIConfigDAO
import models.dao.APIDAO
import models.dao.TestCaseDAO

trait AbstractService {
  
	val apiDAO = Spring.getBeanOfType(classOf[APIDAO])
	val testCaseDAO = Spring.getBeanOfType(classOf[TestCaseDAO])
	val apiConfigDAO = Spring.getBeanOfType(classOf[APIConfigDAO])
}