package com.example.tonjootestkotlin.models

data class LoginResponse (val success: Boolean,
                          val token: String,
                          val user_id: Int)