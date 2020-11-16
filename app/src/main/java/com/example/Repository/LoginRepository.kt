package com.example.Repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class LoginRepository(context: Context) {
    private lateinit var pref: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private val NAME = "UserLogin"
    private val MODE = Context.MODE_PRIVATE

    private lateinit var prefAct: SharedPreferences
    private lateinit var editAct: SharedPreferences.Editor
    private val ACTNAME = "ActiveUser"

    init{
        Log.e("context", context.toString())
        pref = context.getSharedPreferences(NAME, MODE)
        edit = pref.edit()

        prefAct = context.getSharedPreferences(ACTNAME, MODE)
        editAct = prefAct.edit()
    }

    fun saveUser(user: String, name: String, password: String, type: Int = 1){
        edit.putString("name$user", name)
        edit.putString("user$user", user)
        edit.putString("password$user", password)
        edit.putInt("type$user", type)

        edit.commit()
        saveActUser(user, name, type)
    }

    private fun saveActUser(user: String, name: String, type: Int) {
        editAct.putString("name", name)
        editAct.putString("user", user)
        editAct.putInt("type", type)

        editAct.commit()
    }

    fun saveAnon(type: Int = 0){
        edit.putString("name", "Anonimo")
        edit.putString("user", "Anonimo")
        edit.putString("password", "null")
        edit.putInt("type", type)

        edit.commit()
        saveActUser("Anonimo", "Anonimo", type)
    }

    fun logout(){
        editAct.clear()
        editAct.commit()
    }

    fun getCurrentUser(): String{
        return prefAct.getString("user","-1").toString()
    }
    fun getCurrentUserName(): String{
        return prefAct.getString("name","-1").toString()
    }

    fun confirmLogin(user: String, password: String): Int{
        var pass = (pref.getString("password"+user,"-1").toString())
        if (pass.equals("-1")){
            //user not exist
            return -1
        }
        else if (pass.equals(password)){
            //correct data to login
            var name: String = (pref.getString("name"+user, "-1").toString())
            saveActUser(user, name, 1)
            return 1
        }
        //incorrect password
        return 0
    }

}