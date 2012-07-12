package service.impl

import service.APIResourceService
import service.AbstractService
import util.StringUtil
import models.APIResource
import sjson.json.Serializer.SJSON
import models.APISpec
import models.APIOperation
import models.APIParameter
import models.BaseKey
class APIResourceServiceImpl extends APIResourceService with AbstractService {

  def getAPIResources(start: Int, end: Int, path: String,currentVersion:String): List[APIResource] = {
    val id = StringUtil.basePath + path
    var resources = List[APIResource]()
    var lastestVersion =  currentVersion
    if(StringUtil.isBlank(currentVersion)){
      val listVersion = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
      print(listVersion)
      if (listVersion == null) {
        resources
      }
      lastestVersion = listVersion(0).id
    }

    val result = apiResourceDAO.findbyProperty("_id.version",lastestVersion)
    if (result != null) {
      result.foreach(item => {
        var resource = getAPIResource(item,"") 
        if (resource != null) {
          resources ::= resource
        }
      })
    }
    return resources
  }

  private def getAPIResource(apiResource: APIResource,keyword: String) = {
    var listSpec = List[APISpec]()
    if (apiResource == null || apiResource.specIds.isEmpty) {
      null
    }
    apiResource.specIds.foreach(specId => {
      val spec = apiSpecDAO.findById(specId)
      var listOpertation = List[APIOperation]()
      if (spec == null || spec.operationsId.isEmpty) {
        null
      }
      spec.operationsId.foreach(operationId => {
        val operation = apiOperationDAO.findById(operationId)
        if (operation == null || operation.apiParameterIds.isEmpty) {
          null
        }
        if(StringUtil.isBlank(keyword) || (StringUtil.isNotBlank(operation.nickname) && operation.nickname.contains(keyword))){
          var listParameter = List[APIParameter]()
          operation.apiParameterIds.foreach(parameterId => {
            val parameter = apiParameterDAO.findById(parameterId)
            listParameter ::= parameter
          })
          operation.parameters = listParameter
          listOpertation ::= operation
        }
      })
      spec.operations = listOpertation
      if(!spec.operations.isEmpty){
          listSpec ::= spec
      }
    })
    apiResource.apis = listSpec
    apiResource
  }

  def getAPIResource(id: String,keyword: String, version:String): APIResource = {
    var lastestVersion = version
    if(StringUtil.isBlank(lastestVersion)){
      val listVersion = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
      if (listVersion == null) {
        null
      }
      lastestVersion = listVersion(0).id
    }
    val key  = new BaseKey(id,lastestVersion)
    println("Base Key"  + key)
    val apiRes = apiResourceDAO.findById(key)
    if (apiRes != null) {
      return getAPIResource(apiRes,keyword)
    } else {
      return null
    }
  }
  
    
  def getParameterList(operation : APIOperation) = {
     var listParameter = List[APIParameter]()
          operation.apiParameterIds.foreach(parameterId => {
            val parameter = apiParameterDAO.findById(parameterId)
            listParameter ::= parameter
          })
     listParameter
  }
  
  def getListOperation(spec: APISpec) = {
    var listOpertation = List[APIOperation]()
    if (spec == null || spec.operationsId.isEmpty) {
      null
    }
    spec.operationsId.foreach(operationId => {
      val operation = apiOperationDAO.findById(operationId)
      if (operation == null || operation.apiParameterIds.isEmpty) {
        null
      }
      var listParameter = List[APIParameter]()
      operation.parameters = getParameterList(operation)
      listOpertation ::= operation
    })
    listOpertation
  }
  
  def getListSpec(apiResource: APIResource) = {
    var listSpec = List[APISpec]()
    if (apiResource == null || apiResource.specIds.isEmpty) {
      null
    }
    apiResource.specIds.foreach(specId => {
      val spec = apiSpecDAO.findById(specId)
      var listOpertation = List[APIOperation]()
      if (spec == null || spec.operationsId.isEmpty) {
        null
      }
      listOpertation = getListOperation(spec)
      spec.operations = listOpertation
      if (!spec.operations.isEmpty) {
        listSpec ::= spec
      }
    })
    listSpec
  }
  
  def getNameAPIResources(start: Int, end: Int, path: String,currentVersion:String): List[String] = {
    val id = StringUtil.basePath + path
    var resources = List[String]()
    var lastestVersion =  currentVersion
    if(StringUtil.isBlank(currentVersion)){
      val listVersion = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
      print(listVersion)
      if (listVersion == null) {
        resources
      }
      lastestVersion = listVersion(0).id
    }

    val result = apiResourceDAO.findbyProperty("_id.version",lastestVersion)
    if (result != null) {
      result.foreach(item => {
        var resource = getAPIResource(item,"") 
        if (resource != null) {
          resources ::= resource.resourcePath
        }
      })
    }
    return resources
  }

}
