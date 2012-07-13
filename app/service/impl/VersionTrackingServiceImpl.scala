package service.impl

import scala.reflect.BeanInfo
import models.APIResource
import models.BaseKey
import service.AbstractService
import service.VersionTrackingService
import util.DateUtil
import util.StringUtil
import util.APIRequestUtils
import util.ConfigUtils
import models.Res
import sjson.json.Serializer.SJSON
import models.ResList
import dispatch.json.Js
import models.VersionTracking
import models.BaseKey


class VersionTrackingServiceImpl extends VersionTrackingService with AbstractService {
  def getLastedVersion():String = {
    var lastItem = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
    if (lastItem != null) {
      return lastItem(0).id
    }
    return null
  }

  def buildAPIAndParameter(apiResource: APIResource,now:String) {
    if (apiResource.apis != None) {
      var listAPI = List[BaseKey]()
      apiResource.id = new BaseKey(apiResource.resourcePath, now)
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

  def buildAPIVersion() {
    val now = DateUtil.getNow()
    var version = new VersionTracking
    version.id = now
    val res = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + ":" + ConfigUtils.API_DEFAULT_PORT + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + "/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis
    var pathList = List[String]()
    apis.foreach(api => {
      val path = api.path
      val res2 = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + ":" + ConfigUtils.API_DEFAULT_PORT + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH  + "/api_by_rest_path?rest_path="  + path, Map())
      this.buildAPIAndParameter(SJSON.in[APIResource](Js(res2)),now)
      pathList::=path
    })
    version.paths = pathList
    apiVersionTrackingDAO.save(version)
  }
  
  def getPathListOfVersion(version:String):List[String] = {
    var versionString = version
    if(StringUtil.isBlank(versionString)){
      versionString = getLastedVersion()
    }
    val versionTracking = apiVersionTrackingDAO.findById(versionString)
    return versionTracking.paths
  }
  
  def getListVersion(start:Int,size:Int):List[VersionTracking] = {
    apiVersionTrackingDAO.findLimit(start,size)
  }
  
  def deleteVersion(version:String){
    apiVersionTrackingDAO.removeByCondition("_id",version)
    apiResourceDAO.removeByCondition("_id.version",version)
    apiSpecDAO.removeByCondition("_id.version",version)
    apiOperationDAO.removeByCondition("_id.version",version)
    apiParameterDAO.removeByCondition("_id.version",version)
  }
  
  def getAPIREsourceListOfVersion(version:String):List[APIResource] = {
    var versionString = version
    var listResource = List[APIResource]()
    if(StringUtil.isBlank(versionString)){
      versionString = getLastedVersion()
    }
    val versionTracking = apiVersionTrackingDAO.findById(versionString)
    if(versionTracking != null && versionTracking.paths != null && versionTracking.paths.length > 0){
      versionTracking.paths.foreach( path =>{
        var resource = new APIResource
        var baseKey = new BaseKey  
        baseKey.path = path
        baseKey.version = version
        resource.id = baseKey
        resource.resourcePath = path
        listResource::=resource
      })
    }
    return listResource
  }
}