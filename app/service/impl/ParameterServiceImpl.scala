package service.impl

import scala.reflect.BeanInfo
import models.APIParameter
import models.APIResource
import service.AbstractService
import service.ParameterService
import util.StringUtil
import util.DateUtil
import models.BaseKey


class ParameterServiceImpl extends ParameterService with AbstractService {

  
  def buildAPIAndParameter(apiResource:APIResource){
    val now = DateUtil.getNow()
    if (apiResource.apis != None) {
      var listAPI = List[BaseKey]()
      apiResource.id = new BaseKey(apiResource.apiVersion + StringUtil.separation + apiResource.resourcePath, now)
      apiResource.apis.foreach(api => {
        api.id = new BaseKey(apiResource.id + api.path, now)
        listAPI ::= api.id
        if (api.operations != None) {
          var listOperation = List[BaseKey]()
          api.operations.foreach(operation => {
            operation.versionId = new BaseKey(operation.id, now)
            listOperation ::= operation.versionId
            if (operation.parameters != None) {
              var listParams = List[BaseKey]()
              operation.parameters.foreach(param => {
                param.id = new BaseKey(operation.id + StringUtil.separation + param.name, now)
                param.apiId = operation.versionId
                apiParameterDAO.save(param)
                listParams ::= param.id
              })
              operation.apiParameterIds = listParams
              apiOperationDAO.save(operation)
            }
          })
          api.operationsId = listOperation
          apiSpecDAO.save(api)
        }
      })
      apiResource.specIds = listAPI
      apiResourceDAO.save(apiResource)
    }
  }
}