package com.es.Api_Rest_Segura2.service

import com.es.Api_Rest_Segura2.error.exception.BadRequestException
import com.es.Api_Rest_Segura2.error.exception.ConflictException
import com.es.Api_Rest_Segura2.error.exception.NotFoundException
import com.es.Api_Rest_Segura2.model.Tarea
import com.es.Api_Rest_Segura2.repository.TareaRepository
import com.es.Api_Rest_Segura2.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
@Service
class TareaService {
    @Autowired
    private lateinit var tareaRepository: TareaRepository
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository


    fun insertTarea(tareas: Tarea,): Tarea {
        // comprobar que ningun campo esta vacio
        if (tareas.titulo!!.isBlank()||
            tareas.descripcion!!.isBlank() ||
            tareas.estado.isBlank() ||
            tareas.usuario!!.isBlank()) {
            throw BadRequestException("uno o mas campos vacios")
        }
        // comprobar que no existe el nombre del usuario
        if(!usuarioRepository.findByUsername(tareas.usuario!!).isPresent) {
            throw ConflictException("Usuario ${tareas.usuario} no existe ")
        }
        // Comprobar el estado
        if(tareas.estado != null && tareas.estado != "PENDIENTE" && tareas.estado != "HECHA" ) {
            throw BadRequestException("Estado: ${tareas.estado} incorrecto")
        }

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
    fun insertTareaSelf(tareas: Tarea,username: String): Tarea {

        // comprobar que ningun campo esta vacio
        if (tareas.titulo!!.isBlank()||
            tareas.descripcion!!.isBlank() ||
            tareas.estado.isBlank() ) {
            throw BadRequestException("uno o mas campos vacios")
        }

        val tarea = Tarea(
            null,
            titulo = tareas.titulo,
            descripcion = tareas.descripcion,
            estado = tareas.estado,
            usuario = username ,
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
        return tareaRepository.findTareasByUsuario(username)
    }
    fun updateEstado(id: String, tareaActualizada: Tarea): Tarea {

        // Comprobar el estado
        if(tareaActualizada.estado != null && tareaActualizada.estado != "PENDIENTE" && tareaActualizada.estado != "HECHA" ) {
            throw BadRequestException("Estado: ${tareaActualizada.estado} incorrecto")
        }

        val tarea = tareaRepository.findById(id)
            .orElseThrow { NotFoundException("Tarea con id $id no encontrada") }


        tarea.estado= tareaActualizada.estado
        tarea.fechaActualizacion = Date.from(Instant.now())




        return tareaRepository.save(tarea)
    }

    fun deleteTarea(id: String) {
        if (!tareaRepository.existsById(id)) {
            throw NotFoundException("Tarea con id $id no encontrada")
        }
        tareaRepository.deleteById(id)
    }
    fun isUserOwner(tareaId:String, usuario: String): Boolean{
        return tareaRepository.findTareasById(tareaId).first().usuario == usuario
    }
}

