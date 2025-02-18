package com.example.Api_Rest_Segura2.dto

import com.example.Api_Rest_Segura2.model.Rol

data class UsuarioDTO(
    val username: String,
    val email: String,
    val rol: Rol
)
