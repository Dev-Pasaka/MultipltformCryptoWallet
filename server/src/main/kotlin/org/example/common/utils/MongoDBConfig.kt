package org.example.common.utils

import com.mongodb.kotlin.client.coroutine.MongoClient


object MongoDBConfig {
    private val username:String = System.getenv("MONGODBUSERNAME") ?: ""
    private val password:String = System.getenv("MONGODBPASSWORD") ?: ""
    private val url = "mongodb+srv://$username:$password@coinxdevdb.poozy2d.mongodb.net/"
    private val databaseName = System.getenv("DATABASENAME") ?: ""
    private val mongoClient by lazy {  MongoClient.create(url)}
    val database = mongoClient.getDatabase(databaseName)
}