package com.es.Api_Rest_Segura2.service

import com.es.Api_Rest_Segura2.error.exception.NotFoundException
import com.es.Api_Rest_Segura2.model.Tarea
import com.es.Api_Rest_Segura2.repository.TareaRepository
import com.es.Api_Rest_Segura2.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*

class TareaService {
    @Autowired
    private lateinit var tareaRepository: TareaRepository
    private lateinit var usuarioRepository: UsuarioRepository


    fun insertTarea(tareas: Tarea): Tarea {
        // comprobar que ningun campo esta vacio


        val tarea = Tarea(
            null,
            titulo = tareas.titulo,
            descripcion = tareas.descripcion,
            estado = tareas.estado,
            usuario = tareas.usuario ,
            fechaActualizacion =Date.from(Instant.now()) ,
            fechaCreacion =Date.from(Instant.now())



        )
        return tareaRepository.insert(tarea)


    }
    fun findAllTareas(): List<Tarea> {

        return tareaRepository.findAll() }


    fun findTareasByUser(username: String): List<Tarea> {
        if (!usuarioRepository.existsByUsername(username)) {
            throw NotFoundException("Usuario con id $username no encontrado")
        }
        return tareaRepository.findTareasByUsername(username)
    }
    fun updateTarea(id: String, tareaActualizada: Tarea): Tarea {


        val tarea = tareaRepository.findById(id)
            .orElseThrow { NotFoundException("Tarea con id $id no encontrada") }

        tarea.titulo= tareaActualizada.titulo
        tarea.estado= tareaActualizada.estado
        tarea.descripcion = tareaActualizada.descripcion
        tarea.usuario = tareaActualizada.usuario
        tarea.fechaActualizacion = Date.from(Instant.now())




        return tareaRepository.save(tarea)
    }

    fun deleteTarea(id: String) {
        if (!tareaRepository.existsById(id)) {
            throw NotFoundException("Tarea con id $id no encontrada")
        }
        tareaRepository.deleteById(id)
    }
    fun isUserOwner(tareaId:String, username: String): Boolean{
        return tareaRepository.findTareasBy_id(tareaId).first().usuario == username
    }
}

