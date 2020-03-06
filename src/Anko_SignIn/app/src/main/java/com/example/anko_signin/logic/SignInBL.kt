package com.example.anko_signin.logic

import com.example.anko_signin.model.AuthCredentials

class SignInBL : ISignInBL {
    override fun checkUserCredentials(auth: AuthCredentials): Boolean {
        return ("user" == auth.username && "pass" == auth.password)
    }
}