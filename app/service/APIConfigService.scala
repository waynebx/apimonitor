package service
import models.APIOperation


trait APIConfigService {
  def getAPIConfigs(testCaseId: String): List[APIOperation]	
}