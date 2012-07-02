package util
import java.util.UUID

object StringUtil{
  val http = "http://" 
  val slash = "/"
    
  object WebMethod{
	  val GET = "GET"
	  val POST = "POST"
  }
  
  object TestCaseOperation{
	  val ADD = 0
	  val REMOVE = 1
	  val EDIT = 2
  }
  
  object ParamsType{
    val QueryParamV1 = "QueryParamV1"
    val QueryParamV2 = "QueryParamV2"
    val PostParamV1 = "PostParamV1"
    val PostParamV2 = "PostParamV2"
  }
  def generateStringTimeStamp() = {
    
    val x = UUID.randomUUID().toString()
    println("=========================================")
    x
  }
  
  def isBlank(str:String):Boolean = {
    if(str == null || str.equals("")){
      return true
    }
    return false
  }
  
  def isNotBlank(str:String):Boolean = {
	return !isBlank(str)
  }
}