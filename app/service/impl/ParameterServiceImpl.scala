package service.impl

import scala.reflect.BeanInfo

import models.APIParameter
import models.APIResource
import service.AbstractService
import service.ParameterService

class ParameterServiceImpl extends ParameterService with AbstractService {
  def saveParameterObj(param:APIParameter,apiId:String){
    param.id = apiId + "__" + param.name
    param.apiId = apiId
    apiParameterDAO.save(param)
  }
  
  def buildAPIAndParameter(apiResource:APIResource){
	  if(apiResource.apis != None){
	    apiResource.apis.foreach(api =>{
	      if(api.operations != None){
	        api.operations.foreach(operation =>{
	          if(operation.parameters != None){
	            apiOperationDAO.save(operation)
	            operation.parameters.foreach(param =>{
	              	this.saveParameterObj(param,operation.id)
	            })
	          }
	        })
	      }
	    })
	  }
  }
}