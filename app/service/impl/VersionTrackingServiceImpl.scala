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


class VersionTrackingServiceImpl extends VersionTrackingService with AbstractService {
  def getLastedVersion() = {
    var lastItem = apiVersionTrackingDAO.findAndOrder(StringUtil.Order.DESC, 0, StringUtil.MAXINT)
    if (lastItem != null) {
      lastItem(0).id
    }
    null
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
    val res = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH + "/resources.json", Map())
    val apis: List[Res] = SJSON.in[ResList](Js(res)).apis
    var pathList = List[String]()
    apis.foreach(api => {
      val path = api.path
      val res2 = APIRequestUtils.getWS(StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH  + path + "/list_api?api_key=a3633f30bb4a11e18887005056a70023", Map())
      this.buildAPIAndParameter(SJSON.in[APIResource](Js(res2)),now)
      pathList::=path
    })
    version.paths = pathList
  }

}