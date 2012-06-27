package exception

case class MyException(errorCode : String = "", desc : String = "") extends Exception