package com.example.tonjootestkotlin.activities

import android.content.Intent
import android.net.DnsResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tonjootestkotlin.R
import com.example.tonjootestkotlin.api.RetrofitClient
import com.example.tonjootestkotlin.models.LoginResponse
import com.example.tonjootestkotlin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var prefManager: SharedPrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager = SharedPrefManager(this)

        checkLogin()

        loginButton.setOnClickListener {
            val username = usernameLogin.text.toString().trim()
            val password = passwordLogin.text.toString().trim()

            if (username.isEmpty()){
                usernameLogin.error = "Isi Username!"
                usernameLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                passwordLogin.error = "Isi Password!"
                passwordLogin.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.login(username,password)
                .enqueue(object: Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response.body()?.success  == true){
                            prefManager.setLoggin(true)
                            prefManager.setUsername(username)

                            val intent = Intent(applicationContext, ContactListActivity::class.java)
                            startActivity(intent)
                            finish()

                        }else{
                            Toast.makeText(applicationContext, "Username / Password salah", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })
        }

    }


    private fun checkLogin(){
        if (prefManager.isLogin()!!){
            val intent = Intent(this, ContactListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}