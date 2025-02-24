package com.es.Api_Rest_Segura2.repository

import com.es.Api_Rest_Segura2.model.Tarea
import com.es.Api_Rest_Segura2.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {

    fun findTareasByUsername(usuario: String): List<Tarea>
    fun findTareasBy_id(id: String?): List<Tarea>
}