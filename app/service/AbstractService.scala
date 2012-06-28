package service
import play.api.modules.spring.Spring
import models.dao.APIConfigDAO
import models.dao.APIDAO
import models.dao.TestCaseDAO
import models.dao.APIParameterDAO
import models.dao.APIOperationDAO

trait AbstractService {
  
	val apiDAO = Spring.getBeanOfType(classOf[APIDAO])
	val testCaseDAO = Spring.getBeanOfType(classOf[TestCaseDAO])
	val apiConfigDAO = Spring.getBeanOfType(classOf[APIConfigDAO])
	val apiParameterDAO = Spring.getBeanOfType(classOf[APIParameterDAO])
	val apiOperationDAO = Spring.getBeanOfType(classOf[APIOperationDAO])
}