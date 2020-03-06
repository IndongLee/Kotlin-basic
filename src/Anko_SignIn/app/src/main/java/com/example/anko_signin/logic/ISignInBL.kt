package com.example.anko_signin.logic

import com.example.anko_signin.model.AuthCredentials

interface ISignInBL {
    fun checkUserCredentials(credentials: AuthCredentials): Boolean
}