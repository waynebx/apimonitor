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
    println("version" + lastestVersion)
    println("start" + start)
    val result = apiResourceDAO.findbyProperty("_id.version",lastestVersion)
    if (result != null) {
      result.foreach(item => {
        var resource = getAPIResource(item) // will modify
        if (resource != null) {
          resources ::= resource
        }
      })
    }
    return resources
  }

  private def getAPIResource(apiResource: APIResource) = {
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
        var listParameter = List[APIParameter]()
        operation.apiParameterIds.foreach(parameterId => {
          val parameter = apiParameterDAO.findById(parameterId)
          listParameter ::= parameter
        })
        operation.parameters = listParameter
        listOpertation ::= operation
      })
      spec.operations = listOpertation
      listSpec ::= spec
    })
    apiResource.apis = listSpec
    apiResource
  }

  def getAPIResource(id: String): APIResource = {
    val listVersion = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
      if (listVersion == null) {
        null
      }
    val lastestVersion = listVersion(0).id
    val key  = new BaseKey(id,lastestVersion)
    val apiRes = apiResourceDAO.findById(key)
    if (apiRes != null) {
      return getAPIResource(apiRes)
    } else {
      return null
    }
  }

}
