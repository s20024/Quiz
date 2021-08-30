package jp.ac.it_college.s20024.quiz.quiz

import java.io.Serializable

class Quiz (quest: String,
            imageUrl: String,
            answer1: String,
            answer2: String,
            answer3: String,
            answer4: String,
            imgKyoka: String, ):Serializable{
    private var question = quest
    private var imageurl = imageUrl
    private var correct = 0
    private var shuffled = arrayListOf(answer1, answer2, answer3, answer4)
    private var imgkyoka = imgKyoka

    init {
        shuffled.shuffle()
        correct = shuffled.indexOf(answer1)
    }

    fun getCorrect(): Int {
        return correct
    }

    fun getQuest(): String {
        return question
    }

    fun getImageUrl(): String {
        return imageurl
    }

    fun getQuiz(): List<String> {
        return shuffled
    }

    fun getImageKyoka(): String {
        return imgkyoka
    }
}