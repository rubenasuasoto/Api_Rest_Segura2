package com.es.Api_Rest_Segura2.model




import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

import java.util.Date


@Document("collTareas")
data class Tarea(
    @BsonId
    val id: Long?,

    val titulo: String,

    val descripcion: String? = null,

    var estado: EstadoTarea = EstadoTarea.PENDIENTE,

    val usuario: Usuario,

    val fechaCreacion: Date,

    var fechaActualizacion: Date
)

enum class EstadoTarea {
    PENDIENTE,
    HECHA
}
