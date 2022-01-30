package com.example.tonjootestkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tonjootestkotlin.R
import com.example.tonjootestkotlin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListActivity : AppCompatActivity() {

    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        prefManager = SharedPrefManager(this)

        checkLogin()

        logoutButton.setOnClickListener {
            prefManager.removeData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkLogin(){
        if (prefManager.isLogin() == false){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}