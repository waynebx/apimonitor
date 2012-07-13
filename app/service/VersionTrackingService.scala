package service
import scala.reflect.BeanInfo
import models.APIResource
import models.VersionTracking
trait VersionTrackingService {	
    def buildAPIVersion()
    def getLastedVersion():String
    def getPathListOfVersion(version:String):List[String]
    def getListVersion(start:Int,size:Int):List[VersionTracking]
    def deleteVersion(version:String)
    def getAPIREsourceListOfVersion(version:String):List[APIResource]
}