package util
import java.util.Date
import java.text.SimpleDateFormat
import java.util.TimeZone


object DateUtil{
  val FORMAT_DATE_DEFAULT = "yyyyMMddHHmmss"
  val GMT_TIME_ZONE_ID = "GMT"
  def getNow(dateFormat: String =this.FORMAT_DATE_DEFAULT,timezone: String=this.GMT_TIME_ZONE_ID) = {
    var currentTime = new Date();
    var sdf: SimpleDateFormat =
      new SimpleDateFormat(dateFormat);

    sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    sdf.format(currentTime)
  }
}