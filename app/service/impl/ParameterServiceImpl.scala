package service.impl

import scala.reflect.BeanInfo
import models.APIParameter
import models.APIResource
import service.AbstractService
import service.ParameterService
import util.StringUtil

class ParameterServiceImpl extends ParameterService with AbstractService {
  def saveParameterObj(param:APIParameter,apiId:String){
    param.id = apiId + StringUtil.separation + param.name
    param.apiId = apiId
    apiParameterDAO.save(param)
  }
  
  def buildAPIAndParameter(apiResource:APIResource){
	  if(apiResource.apis != None){
	    var listAPI = List[String]()
	    apiResource.id = apiResource.apiVersion + StringUtil.separation + apiResource.resourcePath 
	    apiResource.apis.foreach(api =>{
	      api.id = apiResource.id + api.path
          listAPI::=api.id
	      if(api.operations != None){
	        var listOperation = List[String]()
	        api.operations.foreach(operation =>{
	          listOperation::= operation.id
	          if(operation.parameters != None){
	            apiOperationDAO.save(operation)
	            operation.parameters.foreach(param =>{
	              	this.saveParameterObj(param,operation.id)
	            })
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