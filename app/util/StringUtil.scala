package util

object StringUtil{
  val http = "http://" 
  val slash = "/"
  val separation = "__"
  val basePath = StringUtil.http + ConfigUtils.API_DEFAULT_HOST + StringUtil.slash + ConfigUtils.API_DEFAULT_PATH;
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
    System.currentTimeMillis.toString()
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