package com.example.View.UI.Fragments.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.View.UI.MainActivityView
import com.example.ViewModel.HistoryViewModel
import com.example.ViewModel.LoginViewModel
import com.example.idnpv001.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        historyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //logout button
        val logOutBtn: FloatingActionButton = root.findViewById(R.id.logOutButton)
        logOutBtn.setOnClickListener {
            var loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
            loginViewModel.logout()
            val intent = Intent(activity, MainActivityView::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }
}