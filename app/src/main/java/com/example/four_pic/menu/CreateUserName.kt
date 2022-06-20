package com.example.four_pic.menu

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.four_pic.R
import com.example.four_pic.utils.SharedPreferencesHelper


class CreateUserName : AppCompatActivity() {
    lateinit var newGame : AppCompatButton
    private val shared by lazy {
        SharedPreferencesHelper(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        //val userName : EditText = findViewById(R.id.userName)
        newGame = findViewById(R.id.btnSubmit)
        val btnClose=findViewById<ImageView>(R.id.btnClose)
        newGame.setOnClickListener {
//            val userName2 :String = userName.text.toString()
//            shared.setUserName(userName2)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            checkUserName()
            finish()
        }
        btnClose.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkUserName() {
        val userName : EditText = findViewById(R.id.userName)
        val name:String = userName.text.toString()
        if (name.trim().isNotEmpty()){
            if (name.trim().length in 3..12) {
                shared.setUserName(name)
                shared.setLevel(0)
                shared.setCoin(0)
                val intent = Intent(this , GameActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this ,"Username is not valid!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CreateUserName, CreateUserName::class.java)
                startActivity(intent)
                finish()
            }
        }else {
            Toast.makeText(this , "create a username!" , Toast.LENGTH_SHORT).show()
            val intent = Intent(this@CreateUserName, CreateUserName::class.java)
            startActivity(intent)
        }
    }
//    override fun onPause() {
//        super.onPause()
//        stopService(Intent(this, MyService::class.java)) // остановить песню
//    }
//
//    // развернули приложение
//    override fun onResume() {
//        super.onResume()
//        startService(Intent(this, MyService::class.java)) // запустить песню
//    }
}

    //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