package com.example.four_pic.manager

import com.example.four_pic.R
import com.example.four_pic.models.QuestionData
import kotlin.collections.ArrayList

class GameManager(
    var questionsList: ArrayList<QuestionData> ,
    var level: Int ,
    var coins: Int,
) {
    fun question() = questionsList[level]
    fun getQuestions() = question().image
    fun getWord() = question().word
    fun getQuestionSize() =questionsList.size
    var currentQuestionPosition: Int = 0
    fun getWordSize() = question().word.length
    fun getLetters() = question().letters
    fun getLettersSize() = question().letters.length
    fun questionsSize() = questionsList.size
    fun check(word: String):Boolean{
//Jamshid Nuriddinov
//
        return getWord().trim().lowercase() == word.lowercase()
    }
    fun hasNextQuestion(): Boolean{
        if (currentQuestionPosition<getQuestionSize()-1){
            currentQuestionPosition++
            return true
        }
        return false
    }
}