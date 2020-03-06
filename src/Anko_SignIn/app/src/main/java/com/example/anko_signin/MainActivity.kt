package com.example.anko_signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anko_signin.logic.ISignInBL
import com.example.anko_signin.logic.SignInBL
import com.example.anko_signin.model.AuthCredentials
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private val signInBL: ISignInBL = SignInBL()
    private lateinit var signInUI: SignInUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInUI = SignInUI()
        signInUI.setContentView(this)
    }

    fun authorizeUser(username: String, password: String) {
        doAsync {
            val authorized = signInBL.checkUserCredentials(
                AuthCredentials(username = username, password = password)
            )
            activityUiThread {
                if (authorized) toast("Signed!") else signInUI.showAccessDeniedAlertDialog()
            }
        }
    }
}
