package service
import models.APIResource
trait APIResourceService {	
  def getAPIResource(id : String,keyword: String, version:String): APIResource
  def getAPIResources(start: Int, end: Int, path: String,currentVersion:String): List[APIResource]
  def getNameAPIResources(start: Int, end: Int, path: String,currentVersion:String): List[String]
}