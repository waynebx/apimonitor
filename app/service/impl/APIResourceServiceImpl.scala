package service.impl

import service.APIResourceService
import service.AbstractService
import util.StringUtil
import models.APIResource
import sjson.json.Serializer.SJSON
import models.APISpec
import models.APIOperation
import models.APIParameter
class APIResourceServiceImpl extends APIResourceService with AbstractService {

  def getAPIResources(start: Int, end: Int, path: String): List[APIResource] = {
    val id = StringUtil.basePath + path
    var resources = List[APIResource]()
    val listVersion = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
    if (listVersion == null) {
      resources
    }
    val lastestVersion = listVersion(0)
    val result = apiResourceDAO.findbyProperty("_id.version", lastestVersion.id, start, end)
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
      val spec = apiSpecDAO.findOneByKey(specId)
      var listOpertation = List[APIOperation]()
      if (spec == null || spec.operationsId.isEmpty) {
        null
      }
      spec.operationsId.foreach(operationId => {
        val operation = apiOperationDAO.findOneByKey(operationId)
        if (operation == null || operation.apiParameterIds.isEmpty) {
          null
        }
        var listParameter = List[APIParameter]()
        operation.apiParameterIds.foreach(parameterId => {
          val parameter = apiParameterDAO.findOneByKey(parameterId)
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

    val apiRes = apiResourceDAO.findById(id)
    println(apiRes)
    if (apiRes != null) {
      return getAPIResource(apiRes)
    } else {
      return null
    }
  }

}
