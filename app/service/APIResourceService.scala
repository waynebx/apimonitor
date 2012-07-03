package service
import models.APIResource
trait APIResourceService {	
  def getAPIResources(start:Int,end:Int,path:String):List[APIResource]
}