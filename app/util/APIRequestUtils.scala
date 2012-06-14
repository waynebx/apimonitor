package util

import dispatch._

object APIRequestUtils {

  def post(path: String, params: Map[String, String]): String = {
    var res = ""
    Http(:/(ConfigUtils.API_DEFAULT_HOST, ConfigUtils.API_DEFAULT_PORT)
      / ConfigUtils.API_DEFAULT_PATH / path
      << params >- { str =>
        res = str
      })
    return res
  }

  def get(path: String, params: Map[String, String]): String = {
    var res = ""
    Http(:/(ConfigUtils.API_DEFAULT_HOST, ConfigUtils.API_DEFAULT_PORT)
      / ConfigUtils.API_DEFAULT_PATH / path
      <<? params >- { str =>
        res = str
      })
    return res
  }
}