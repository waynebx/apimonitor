package service

trait MyService {
	def go
	def getListApi:String
	def getListAPIInRes(rest:String):String
}