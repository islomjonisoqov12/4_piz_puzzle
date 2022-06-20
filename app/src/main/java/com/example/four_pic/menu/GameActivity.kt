package com.example.four_pic.menu

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.example.four_pic.R
import com.example.four_pic.repository.Repositoru
import com.example.four_pic.databinding.ActivityGameBinding
import com.example.four_pic.manager.GameManager
import com.example.four_pic.utils.*
import java.util.*
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var imagesList: ArrayList<ImageView>
    private lateinit var wordList: ArrayList<Button>
    private lateinit var lettersList: ArrayList<Button>
    private lateinit var gameManager: GameManager
    private lateinit var userName : TextView
    private var wordCheck = ""
    private val repos by lazy{
        Repositoru()
    }
    private lateinit var dialog:Dialog
    private val shared by lazy {
        SharedPreferencesHelper(this)
    }
    var wordSize = 0
    var letterSize =0
    private var mediaYes = MediaPlayer()
    private var mediaNo = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setTimer()
        mediaYes = MediaPlayer.create(this, R.raw.child_yes)
        mediaNo = MediaPlayer.create(this, R.raw.child_no)
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        val userNAME = shared.getUserName()
        userName = findViewById(R.id.userNameID)
        userName.text=userNAME.toString()
        repos.getAllQuestions()
        getDifficult()
        loadViews()
        loadDataToView()
//        setTimer()
        binding.btnClean.setOnClickListener {
            for (i in 0 until wordList.size){
                wordList[i].text=""
                letterSize=0
            }
            for (i in 0 until lettersList.size) {
                lettersList[i].visible()
            }
            wordCheck=""
        }
        binding.btnHelp.setOnClickListener {
            if (gameManager.coins>=5) {
                var n = 0
                for (i in 0 until wordList.size) {
                    n = i
                    if (wordList[i].text.isEmpty()) {
                        wordList[n].text = gameManager.getWord()[n].toString()
                        break
                    }
                }
                for (i in 0 until lettersList.size) {
                    if (lettersList[i].text.toString() == wordList[n].text.toString()) {
                        lettersList[i].invisible()
                        break
                    }
                }
                letterSize++
                binding.coins.text = gameManager.coins.toString()
                if (letterSize==wordSize){
                        if (_check()) {
                            if (!gameManager.hasNextQuestion()){
                                custom()
                            }else{
                                gameManager.coins += 10
                                binding.coins.text = gameManager.coins.toString()
                                gameManager.level++
                                shared.setCoin(gameManager.coins)
                                binding.level.text = ((gameManager.level)+1).toString()
                                Handler().postDelayed({
                                    shared.setLevel(gameManager.level)
                                    loadDataToView()
                                    wordCheck = ""
                                    letterSize=0
                                    wordSize = gameManager.getWord().length
                                },500)
                                if (shared.getSound()){
                                    mediaYes.start()
                                }
                            }
                        }else{
                            if (shared.getSound()){
                                mediaNo.start()
                            }
                        }
                }
                gameManager.coins-=5
                binding.coins.text = gameManager.coins.toString()
            }else{
                dialog= Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_coins)
                dialog.setCancelable(true)
                dialog.window!!.attributes.windowAnimations=R.style.DialogAnimation
                val button=dialog.findViewById<AppCompatButton>(R.id.coins_okay)
                button.setOnClickListener {
                    dialog.cancel()
                }
                dialog.show()
            }
        }
    }

    fun getDifficult(){
        if (shared.getDifficulty()=="EASY"){
            gameManager = GameManager(repos.questionsList_3and4, shared.getLevel(), shared.getCoin())
        }else if (shared.getDifficulty()=="MEDIUM"){
            gameManager = GameManager(repos.questionsList_5and6, shared.getLevel(), shared.getCoin())
        }else if (shared.getDifficulty()=="HARD"){
            gameManager = GameManager(repos.questionsList_7and8, shared.getLevel(),shared.getCoin())
        }
        binding.level.text = (shared.getLevel()+1).toString()
        binding.coins.text = shared.getCoin().toString()
    }

    override fun onStop() {
        shared.setLevel(gameManager.level)
        shared.setCoin(gameManager.coins)
        super.onStop()
    }

//    private fun startTimer() {
//        object : CountDownTimer(3000, 2000) {
//            override fun onFinish() {
////                startActivity(Intent(this@MainActivity, MenuActivity::class.java))
//            }
//            override fun onTick(millisUntilFinished: Long) {
//            }
//        }.start()
//    }

    private fun custom() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@GameActivity,
            R.style.fullScreenAlert
        )
        val view: View = layoutInflater.inflate(R.layout.dialog_win, null)
        val goToMenu = findViewById<Button>(R.id.go_to_menu)
        goToMenu.setOnClickListener {
            shared.setLevel(0)
            shared.setCoin(0)
            shared.setUserName("")
            startActivity(Intent(this, MenuActivity::class.java))
        }
        alertDialog.setView(view)
        val dialog = alertDialog.create()
        dialog.show()
    }

    private fun loadViews() {
        imagesList = ArrayList()
        for (i in 0 until binding.imagesLayout.childCount) {
            imagesList.add(binding.imagesLayout.getChildAt(i) as ImageView)
        }
        /////////
        wordList = ArrayList()
        for (i in 0 until binding.wordLayout.childCount) {
            wordList.add(binding.wordLayout.getChildAt(i) as Button)
            wordList[i].setOnClickListener {

                wordBtnClick(it as Button)
            }
        }
        ///////////
        lettersList = ArrayList()
        for (i in 0 until binding.letterLayout.childCount) {
            lettersList.add(binding.letterLayout.getChildAt(i) as Button)
            lettersList[i].setOnClickListener {
                letterBtnClick(it as Button)
                wordSize = gameManager.getWordSize()
                letterSize++
                if (letterSize==wordSize){
                    if (_check()) {
                        if (gameManager.hasNextQuestion()){
                            gameManager.coins += 10
                            binding.coins.text = gameManager.coins.toString()
                            gameManager.level++
                            binding.level.text = ((gameManager.level)+1).toString()
                            Handler().postDelayed({
                                shared.setCoin(gameManager.coins)
                                shared.setLevel(gameManager.level)
                                loadDataToView()
                                wordCheck = ""
                                letterSize=0
                                wordSize = gameManager.getWord().length
                            },500)
                            if (shared.getSound()){
                                mediaYes.start()
                            }
                        } else {
                            if (shared.getSound()){
                                mediaNo.start()
                            }
                        }
                    }else{
                        custom()
                    }
                }
            }
        }
    }

    private fun letterBtnClick(button: Button) {
        if (button.isVisible && wordList[gameManager.getWordSize()-1].text.isEmpty()) {
            button.invisible()
            val word = button.text.toString()
            for (i in 0 until wordList.size) {
                if (wordList[i].text.isEmpty()) {
                    wordList[i].text = word
                    break
                }
            }
        }
    }

    private fun _check(): Boolean {
        for (i in 0 until (gameManager.getWordSize())){
            wordCheck+=wordList[i].text.toString()
        }
        return gameManager.check(wordCheck)
    }

    private fun wordBtnClick(it: Button) {
        if (it.text.isNotEmpty()) {
            val word = it.text.toString()
            it.text = ""
            for (i in 0 until lettersList.size) {
                if (lettersList[i].isInvisible()
                    && lettersList[i].text.toString().lowercase() == word.lowercase()
                ) {
                    lettersList[i].visible()
                    wordCheck+=word
                    break
                }
            }
            for (i in 0 until wordList.size){
                if(wordList[i].text.isNotEmpty()){
                    wordCheck+=wordList[i].toString()
                }else wordCheck=""
            }
        }
        wordCheck = ""
    }

    private fun loadDataToView() {
        for (i in 0 until imagesList.size) {
            imagesList[i].setImageResource(gameManager.question().image)
        }
        /////////
        for (i in 0 until wordList.size) {
            if (gameManager.getWordSize() > i) {
                wordList[i].visible()
                wordList[i].text = ""
            } else {
                wordList[i].gone()
            }
        }
        ////////
        for (i in 0 until lettersList.size) {
            lettersList[i].visible()
            lettersList[i].text = gameManager.getLetters()[i].toString()
        }
    }
}