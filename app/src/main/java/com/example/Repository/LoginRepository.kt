package com.example.Repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.Model.database.AppDatabase
import com.example.dto.User
import com.google.firebase.database.DatabaseReference

class LoginRepository(context: Context) {

    private lateinit var pref: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private val NAME = "UserLogin"
    private val MODE = Context.MODE_PRIVATE

    private lateinit var prefAct: SharedPreferences
    private lateinit var editAct: SharedPreferences.Editor
    private val ACTNAME = "ActiveUserEnd"

    var database: AppDatabase = AppDatabase()
    private var ref: DatabaseReference

    init{
        ref = database.getReference()
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

        val key = ref.child("users").push().key ?: return
        edit.putString("userID$user", key)

        val newUser = User(user, user, name, type)
        ref.child("users").child(key).setValue(newUser)
        //ref.child("users").child(user).setValue(newUser)

        edit.commit()
        saveActUser(user, name, type, key)
    }

    private fun saveActUser(user: String, name: String, type: Int, key: String) {
        editAct.putString("name", name)
        editAct.putString("user", user)
        editAct.putInt("type", type)
        editAct.putString("userID", key)

        editAct.commit()
    }

    fun saveAnon(type: Int = 0){
        val key = ref.child("users").push().key ?: return
        edit.putString("name", key)
        edit.putString("user", key)
        edit.putString("password", "null")
        edit.putInt("type", type)
        edit.putString("userID", key)

        var newUser = User(key,key,key, 1)
        ref.child("users").child(key).setValue(newUser)
        edit.commit()
        saveActUser(key, key, type, key)
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
    fun getCurrenUserID(): String{
        return prefAct.getString("userID", "-1").toString()
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
            var id: String = (pref.getString("userID"+user, "-1").toString())
            saveActUser(user, name, 1, id)
            Log.e("RESULT:",user+"\n"+name+"\n"+id  )
            return 1
        }
        //incorrect password
        return 0
    }

}