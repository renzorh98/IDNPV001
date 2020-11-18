package com.example.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.Repository.LoginRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var repo: LoginRepository

    init {
        repo = LoginRepository(application)
    }

    fun anonymousLogin(){
        repo.saveAnon()
    }

    fun logout(){
        repo.logout()
    }

    fun login(user: String, pass: String): Int{
        // -2 any of the parameters is ""
        // -1 dont exist this user
        // 0 user exist but password is incorrect
        // 1 login succesful
        var bool = reviewParams(user, pass)
        if (bool) {
            return -2
        }
        var result = repo.confirmLogin(user, pass)
        return result
    }

    fun sigIn(user: String, name: String, pass: String): Int{
        var bool: Boolean = reviewParams(user, name, pass)
        if(bool){
            return -2
        }
        repo.saveUser(user, name, pass)
        return 1

    }

    private fun reviewParams(
        s1: String = "aaa",
        s2: String = "bbb",
        s3: String = "ccc"
    ): Boolean{
        if(s1.equals("")){
            return true
        }
        else if (s2.equals("")){
            return true
        }
        else if (s3.equals("")){
            return true
        }
        return false
    }

    fun getUser(): String{
        return repo.getCurrentUser()
    }

    fun getName(): String{
        return repo.getCurrentUserName()
    }


}