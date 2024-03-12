package com.example.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityRegisterBinding
import com.example.movieapp.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtSignUpEmail.text.toString()
            val password = binding.edtSignUpPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {

                authViewModel.register(email, password,
                {
                    // On successful registration, navigate to MovieActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },
                { errorMessage ->
                    // On registration error, show error message to user
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtSignUpEmail.text.toString()
            val password = binding.edtSignUpPassword.text.toString()
            // Check for null or empty email and password
            if (email.isNotBlank() && password.isNotBlank()) {
                authViewModel.login(email, password,
                    {
                        // On successful login, navigate to MovieActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    },
                    { errorMessage ->
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

       /* binding.btnLogout.setOnClickListener {
            authViewModel.logout()
        }*/
    }

}