package com.example.Api_Rest_Segura2.repository


import com.example.Api_Rest_Segura2.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {

    fun findByUsername(username: String) : Optional<Usuario>
}