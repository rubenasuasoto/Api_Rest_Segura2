package com.example.Api_Rest_Segura2.dto


import com.example.Api_Rest_Segura2.model.Direccion
import com.example.Api_Rest_Segura2.model.Rol

data class UsuarioRegisterDTO(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: Rol?,
    val direccion: Direccion?
)
