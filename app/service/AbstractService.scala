package service
import models.dao.APIConfigDAO
import models.dao.APIOperationDAO
import models.dao.APIParameterDAO
import models.dao.APIResourceDAO
import models.dao.APISpecDAO
import models.dao.TestCaseDAO
import play.api.modules.spring.Spring
import models.dao.VersionTrackingDAO

trait AbstractService {
  
//	val apiDAO = Spring.getBeanOfType(classOf[APIDAO])
	val testCaseDAO = Spring.getBeanOfType(classOf[TestCaseDAO])
	val apiConfigDAO = Spring.getBeanOfType(classOf[APIConfigDAO])
	val apiParameterDAO = Spring.getBeanOfType(classOf[APIParameterDAO])
	val apiOperationDAO = Spring.getBeanOfType(classOf[APIOperationDAO])
	val apiResourceDAO = Spring.getBeanOfType(classOf[APIResourceDAO])
    val apiSpecDAO = Spring.getBeanOfType(classOf[APISpecDAO])
    val apiVersionTrackingDAO = Spring.getBeanOfType(classOf[VersionTrackingDAO])
}