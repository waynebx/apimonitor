package service
import models.APIResource
trait APIResourceService {	
  def getAPIResource(id : String): APIResource
  def getAPIResources(start:Int,end:Int,path:String):List[APIResource]
}