package com.example.Api_Rest_Segura2.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("Usuario")
data class Usuario(
    @BsonId
    val _id : String?,
    val username: String,
    val password: String,
    val email: String,
    val roles: Rol? = Rol.USER,
    val fechacrea: Date ,
    val direccion: Direccion?

)
enum class Rol {
    USER,
    ADMIN
}



