package com.es.Api_Rest_Segura2.repository

import com.es.Api_Rest_Segura2.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByUsername(username: String) : Optional<Tarea>
}