package models.Dao
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.MongoConnection
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import models.Bean.MobionTestCase

class MongoSalatDAO[ObjectType <: AnyRef, ID <: Any](collectionName: String)(implicit mot: Manifest[ObjectType], mid: Manifest[ID], ctx: Context) extends 
	SalatDAO[ObjectType, ID](collection = MongoConnection("mongo01",27017)("mobion")(collectionName))(mot, mid, ctx){
  collection.getDB().authenticate("mobion","mobion!life")
}
