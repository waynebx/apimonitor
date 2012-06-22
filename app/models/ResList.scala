package models
import sjson.json.JSONTypeHint
import scala.annotation.target.field
import scala.reflect.BeanInfo

@BeanInfo case class ResList(@(JSONTypeHint @field)(value = classOf[Res]) apis: List[Res]) {
  def this() = { this(null) }
}
