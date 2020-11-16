package com.example.View.UI.Fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.View.UI.MainActivityView
import com.example.ViewModel.LoginViewModel
import com.example.idnpv001.R

class LoginIniFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_login_ini, container, false)

        val logUser: Button = root.findViewById(R.id.logUsrBtn)
        logUser.setOnClickListener{
            findNavController().navigate(R.id.navigation_login)
        }

        val logAnon: Button = root.findViewById(R.id.logAnoBtn)
        logAnon.setOnClickListener{
            loginViewModel.anonymousLogin()
            val intent = Intent(activity, MainActivityView::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }
}