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
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

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
            .roles(usuario.roles)
            .build()
    }

    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO) : UsuarioDTO? {

        if (usuarioInsertadoDTO.username.isBlank() ||
            usuarioInsertadoDTO.email.isBlank() ||
            usuarioInsertadoDTO.password.isBlank() ||
            usuarioInsertadoDTO.passwordRepeat.isBlank()) {
            throw BadRequestException("uno o mas campos vacios")
        }
        val userExist = usuarioRepository.findByUsername(usuarioInsertadoDTO.username).getOrNull()
        if (userExist!= null){

        }
        if (usuarioInsertadoDTO.password != usuarioInsertadoDTO.passwordRepeat){
            throw BadRequestException("Las contraseñas no coinciden ")
        }
        if (usuarioInsertadoDTO.rol !=null && usuarioInsertadoDTO.rol != "User"){
            throw BadRequestException("Rol:"+usuarioInsertadoDTO.rol+"incorrecto")
        }


        val usuario = Usuario(
            null,
            username = usuarioInsertadoDTO.username,
            email = usuarioInsertadoDTO.email,
            password = passwordEncoder.encode(usuarioInsertadoDTO.password),
            roles = usuarioInsertadoDTO.rol ?: "",
            fechaCrea = Date.from(Instant.now()),
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