package service.impl

import scala.collection.mutable.ListBuffer

import models.APIOperation
import models.APIParameter
import service.APIConfigService
import service.AbstractService

class APIConfigServiceImpl extends  APIConfigService with AbstractService {
  def getAPIConfigs(testCaseId: String): List[APIOperation] = {
    var testCase = testCaseDAO.findById(testCaseId)
    var list = new ListBuffer[APIOperation]();

    if (testCase != null) {
      var apiConfigIds = testCase.apiConfigIds
    		  
      apiConfigIds.foreach(id => {
        var apiConfig = apiConfigDAO.findById(id)
        if (apiConfig != null) {
          var api = apiOperationDAO.findById(apiConfig.apiId)
          if (api != null) {
            var map = apiConfig.parseParamToMap()
            var keySet = map.keySet
            var parameters = List[APIParameter]()
            if(!keySet.isEmpty){
                keySet.foreach(key =>{
                  var parameter = apiParameterDAO.findById(api.id +"__" + keySet)
                  if(parameter != null){
                    if(map.get(key)!= None ){
                      parameter.value = map.get(key).get
                      parameters::=parameter
                    }
                  }
                })
            }
            api.parameters = parameters
            list += api
          }
        }
      })
    }

    return list.toList;
  }
}