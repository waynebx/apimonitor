package util
import play.Play

object ConfigUtils {
  var ENV: String = Play.application.configuration.getString("env");
  var API_DEFAULT_HOST: String = Play.application.configuration.getString(ENV + ".api.default.host");
  var API_DEFAULT_PORT: Int = Play.application.configuration.getInt(ENV + ".api.default.port");
  var API_DEFAULT_PATH: String = Play.application.configuration.getString(ENV + ".api.default.path");
}