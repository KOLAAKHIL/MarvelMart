package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.marvelmart.databinding.ActivitySignupBinding
import com.example.marvelmart.models.RegisterRequest
import com.example.marvelmart.repositories.UserRepository
import com.example.marvelmart.viewmodels.SignupViewModel
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels {
        SignupViewModel.SignupViewModelFactory(UserRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val mobileNo = binding.etMobileNo.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (fullName.isNotEmpty() && mobileNo.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val registerRequest = RegisterRequest(fullName, mobileNo, email, password)
                viewModel.registerUser(registerRequest)
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.registerResponse.observe(this) { response ->
            val status = response?.status ?: -1
            val message = response?.message ?: "Unknown error"

            if (status == 0) {
                Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            } else {
                Log.e("SignupError", "Registration failed: $message")
            }
        }
    }
}

