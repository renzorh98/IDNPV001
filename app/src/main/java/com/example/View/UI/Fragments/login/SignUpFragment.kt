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

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val root =  inflater.inflate(R.layout.fragment_sign_up, container, false)

        val userET: EditText = root.findViewById(R.id.userSU)
        val nameET: EditText = root.findViewById(R.id.nameSU)
        val passET: EditText = root.findViewById(R.id.passSU)

        val sigInBtn: Button = root.findViewById(R.id.logBtn)
        val backButton: FloatingActionButton = root.findViewById(R.id.signupBackButton)

        sigInBtn.setOnClickListener {
            var res = loginViewModel.sigIn(
                userET.text.toString().trim(),
                nameET.text.toString().trim(),
                passET.text.toString().trim()
                )

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(R.string.title_dialog_sign_up)

            dialogBuilder.setPositiveButton("OK"){
                    dialogInterface, which -> dialogInterface.cancel()
            }
            dialogBuilder.setIcon(R.drawable.ic_baseline_error_24)
            when(res){
                -2 -> dialogBuilder.setMessage(R.string.fill_inputs_sign_up)
                1 -> {
                    dialogBuilder.setIcon(R.drawable.ic_baseline_check_circle_24)
                    dialogBuilder.setPositiveButton("OK"){
                        dialogInterface, i ->
                        val intent = Intent(activity, MainActivityView::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    var message = "Congratulations welcome\n User:\n\t"+loginViewModel.getUser()+"\n Name:\n\t"+loginViewModel.getName()
                    dialogBuilder.setMessage(message)
                }
                else -> {
                    dialogBuilder.setMessage(R.string.fatal_error)
                }
            }


            val alert = dialogBuilder.create()
            alert.show()
        }

        backButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
        }





        return root
    }
}