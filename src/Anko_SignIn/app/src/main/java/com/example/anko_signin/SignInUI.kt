package com.example.anko_signin

import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignInUI : AnkoComponent<MainActivity> {
    private lateinit var ankoContext: AnkoContext<MainActivity>

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {

        ankoContext = ui

        verticalLayout {
            padding = dip(20)
            lparams(width = matchParent, height = matchParent)

            val username = editText {
                id = R.id.edit_username
                hintResource = R.string.sign_in_username
            }.lparams(width = matchParent, height = wrapContent)

            val password = editText {
                id = R.id.edit_password
                hintResource = R.string.sign_in_password
            }.lparams(width = matchParent, height = wrapContent)

            button {
                id = R.id.btn_sign_in
                textResource = R.string.sign_in_button
                onClick {
                    handleOnSignIn(
                        ui = ui,
                        username = username.text.toString(),
                        password = password.text.toString()
                    )
                }
            }.lparams(width = matchParent, height = wrapContent)
        }.applyRecursively { view ->
            when (view) {
                is EditText -> view.textSize = 24f
            }
        }
    }

    private fun handleOnSignIn(ui: AnkoContext<MainActivity>, username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            with(ui) {
                alert(title = R.string.invalid_user_title,
                    message = R.string.invalid_user_message) {
                    positiveButton(R.string.button_close) {}
                }.show()
            }
        } else {
            ui.owner.authorizeUser(username, password)
        }
    }

    fun showAccessDeniedAlertDialog() {
        with(ankoContext) {
            alert(title = R.string.access_denied_title,
                message = R.string.access_denied_msg) {
                positiveButton(R.string.button_close) {}
            }.show()
        }
    }
}