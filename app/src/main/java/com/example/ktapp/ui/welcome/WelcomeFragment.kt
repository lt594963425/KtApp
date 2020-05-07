package com.example.ktapp.ui.welcome


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.start_fragment.*
import com.example.ktapp.R


class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
        viewModel.getUsers().observe(this, Observer { user -> message.text = user.name })

        change_msg.setOnClickListener {
            //viewModel.getUsers("哈哈哈哈，便便")
        }
    }

}
