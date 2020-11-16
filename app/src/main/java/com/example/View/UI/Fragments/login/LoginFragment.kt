package com.example.View.UI.Fragments.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.View.UI.MainActivityView
import com.example.ViewModel.LoginViewModel
import com.example.idnpv001.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val root =  inflater.inflate(R.layout.fragment_login, container, false)

        val userET: EditText = root.findViewById(R.id.userET)
        val passET: EditText = root.findViewById(R.id.passET)

        val logBtn: Button = root.findViewById(R.id.logBtn)
        val regBtn: Button = root.findViewById(R.id.regBtn)
        val backBtn: FloatingActionButton = root.findViewById(R.id.loginBackButton)

        logBtn.setOnClickListener{
            var res: Int = loginViewModel.login(userET.text.toString().trim(),passET.text.toString().trim())
            //Alert Dialog impl

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(R.string.title_dialog_login)
            dialogBuilder.setPositiveButton("OK"){
                    dialogInterface, which -> dialogInterface.cancel()
            }
            dialogBuilder.setIcon(R.drawable.ic_baseline_error_24)
            when(res){
                -2 -> dialogBuilder.setMessage(R.string.fill_inputs_login)
                -1 -> dialogBuilder.setMessage(R.string.incorrect_email)
                0 -> dialogBuilder.setMessage(R.string.incorrect_password)
                1 -> {
                    dialogBuilder.setIcon(R.drawable.ic_baseline_check_circle_24)
                    dialogBuilder.setPositiveButton("OK"){
                        dialogInterface, i ->

                        val intent = Intent(activity, MainActivityView::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    var message = "Congratulations welcome\n User:"+loginViewModel.getUser()+"\n Name:"+loginViewModel.getName()
                    dialogBuilder.setMessage(message)
                }
                else -> {
                   dialogBuilder.setMessage(R.string.fatal_error)
                }

            }

            val alert = dialogBuilder.create()
            alert.show()
        }

        regBtn.setOnClickListener{
            findNavController().navigate(R.id.navigation_sign_up)
        }

        backBtn.setOnClickListener{
            findNavController().navigate(R.id.navigation_login_ini)
        }


        return root
    }

}