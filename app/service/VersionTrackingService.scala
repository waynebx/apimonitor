package service
import scala.reflect.BeanInfo

import models.APIResource
trait VersionTrackingService {	
    def buildAPIVersion()
    def getLastedVersion():String
    def getPathListOfVersion(version:String):List[String]
}