package com.example.Api_Rest_Segura2.service


import com.example.Api_Rest_Segura2.dto.UsuarioDTO
import com.example.Api_Rest_Segura2.dto.UsuarioRegisterDTO
import com.example.Api_Rest_Segura2.error.exception.BadRequestException
import com.example.Api_Rest_Segura2.error.exception.UnauthorizedException
import com.example.Api_Rest_Segura2.model.Usuario
import com.example.Api_Rest_Segura2.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    override fun loadUserByUsername(username: String?): UserDetails {
        var usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow {
                UnauthorizedException("$username no existente")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles.toString())
            .build()
    }

    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO) : UsuarioDTO? {

        if (usuarioInsertadoDTO.username.isBlank() ||
            usuarioInsertadoDTO.email.isBlank() ||
            usuarioInsertadoDTO.password.isBlank() ||
            usuarioInsertadoDTO.passwordRepeat.isBlank()) {
            throw BadRequestException("uno o mas campos vacios")
        }
        if(usuarioRepository.findByUsername(usuarioInsertadoDTO.username).isPresent) {
            throw Exception("Usuario ${usuarioInsertadoDTO.username} ya está registrado")
        }

        // comprobar que ambas passwords sean iguales
        if(usuarioInsertadoDTO.password != usuarioInsertadoDTO.passwordRepeat) {
            throw BadRequestException("Las contrasenias no coinciden")
        }

        // Comprobar el ROL
        if(usuarioInsertadoDTO.rol != null && usuarioInsertadoDTO.rol != "USER" && usuarioInsertadoDTO.rol != "ADMIN" ) {
            throw BadRequestException("ROL: ${usuarioInsertadoDTO.rol} incorrecto")
        }
        //Comprobar el email
        if(usuarioRepository.findByEmail(usuarioInsertadoDTO.email).isPresent) {
            throw Exception("El email ${usuarioInsertadoDTO.email} ya está registrado")
        }

        val usuario = Usuario(
            null,
            username = usuarioInsertadoDTO.username,
            email = usuarioInsertadoDTO.email,
            password = passwordEncoder.encode(usuarioInsertadoDTO.password),
            roles = usuarioInsertadoDTO.rol,
            fechacrea = Date.from(Instant.now()),
            direccion = usuarioInsertadoDTO.direccion



        )
        usuarioRepository.insert(usuario)

      return UsuarioDTO(
          email = usuario.email,
          username = usuario.username,
          rol = usuario.roles
      )





    }
}