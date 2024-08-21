package com.yaabelozerov.core.util

import android.util.Log

class GlobalStateManager {
    var current = LoginState.LOGGEDOUT


    fun setState(state: LoginState) {
        Log.i("statemanager", "set" + state.name)
        current = state
    }

    enum class LoginState {
        LOGGEDOUT, LEADER, MEMBER, ADMIN
    }
}