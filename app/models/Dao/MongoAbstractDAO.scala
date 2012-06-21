package models.Dao
import com.mongodb.casbah.MongoConnection
import models.Bean.MobionTestCase
import models.Bean.MobionTestCase
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
abstract class MongoAbstractDAO[T <: AnyRef](_host:String,_port:Int,_db:String,_user:String,_pass:String,_col:String){
	var host = _host
	var port = _port
	var db = _db
	var username= _user
	var password = _pass

	def this(col:String) = {
		this("mongo01",27017,"mobion","mobion","mobion!life",col)
	}
	
	def toDBObject(t : T): DBObject = {null}	
	val mongoConn = MongoConnection(host, port)
	val mongoDB = mongoConn(db)
	mongoDB.authenticate(username,password);
	val collection = mongoDB.getCollection(_col);

	def save(item:T){
	  collection.save(toDBObject(item))
	}
	
	def delete(item:T){
	  collection.remove(toDBObject(item))
	}
	
	def find(item:T) = {
	  collection.find(toDBObject(item))
	}
}