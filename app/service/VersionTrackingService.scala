package service
import scala.reflect.BeanInfo

import models.APIResource
trait VersionTrackingService {	
    def buildAPIVersion()
}