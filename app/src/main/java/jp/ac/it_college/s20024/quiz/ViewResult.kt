package jp.ac.it_college.s20024.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.bumptech.glide.Glide
import jp.ac.it_college.s20024.quiz.databinding.ActivityViewResultBinding
import jp.ac.it_college.s20024.quiz.quiz.Quiz

class ViewResult : AppCompatActivity() {
    private lateinit var binding: ActivityViewResultBinding
    private var questionData:ArrayList<Quiz> = arrayListOf()
    private var yourData:ArrayList<Int> = arrayListOf()
    private var time = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun onClickQuestButton(n: Int) {
        val cellData = questionData[n - 1]
        val cellQuiz = cellData.getQuiz()
        binding.questNumView.text = n.toString() + getString(R.string.cout_quest_format)
        binding.questBodyView.text = cellData.getQuest()
        Glide.with(this).load(resources.getIdentifier(cellData.getImageUrl().split(".")[0], "drawable", packageName)).into(binding.image)

        binding.answerView1.text = cellQuiz[0]
        binding.answerView2.text = cellQuiz[1]
        binding.answerView3.text = cellQuiz[2]
        binding.answerView4.text = cellQuiz[3]

        binding.answerView1.setBackgroundColor(Color.rgb(255, 255, 255))
        binding.answerView2.setBackgroundColor(Color.rgb(255, 255, 255))
        binding.answerView3.setBackgroundColor(Color.rgb(255, 255, 255))
        binding.answerView4.setBackgroundColor(Color.rgb(255, 255, 255))

        val youNum = yourData[n - 1]
        val crrNum = cellData.getCorrect() + 1
        printTrue(crrNum)

        if (youNum != crrNum) {
            printFalse(youNum)
        }
    }

    private fun printTrue(num: Int) {
        when(num) {
            1 -> { binding.answerView1.setBackgroundColor(Color.rgb(155, 222, 86)) }
            2 -> { binding.answerView2.setBackgroundColor(Color.rgb(155, 222, 86)) }
            3 -> { binding.answerView3.setBackgroundColor(Color.rgb(155, 222, 86)) }
            4 -> { binding.answerView4.setBackgroundColor(Color.rgb(155, 222, 86)) }
        }
    }

    private fun printFalse(num: Int) {
        when(num) {
            1 -> { binding.answerView1.setBackgroundColor(Color.rgb(200, 200, 200)) }
            2 -> { binding.answerView2.setBackgroundColor(Color.rgb(200, 200, 200)) }
            3 -> { binding.answerView3.setBackgroundColor(Color.rgb(200, 200, 200)) }
            4 -> { binding.answerView4.setBackgroundColor(Color.rgb(200, 200, 200)) }
        }
    }

    private fun printTrueButton(num: Int) {
        when(num) {
            1 -> { binding.view1.setBackgroundColor(Color.rgb(155, 222, 86)) }
            2 -> { binding.view2.setBackgroundColor(Color.rgb(155, 222, 86)) }
            3 -> { binding.view3.setBackgroundColor(Color.rgb(155, 222, 86)) }
            4 -> { binding.view4.setBackgroundColor(Color.rgb(155, 222, 86)) }
            5 -> { binding.view5.setBackgroundColor(Color.rgb(155, 222, 86)) }
            6 -> { binding.view6.setBackgroundColor(Color.rgb(155, 222, 86)) }
            7 -> { binding.view7.setBackgroundColor(Color.rgb(155, 222, 86)) }
            8 -> { binding.view8.setBackgroundColor(Color.rgb(155, 222, 86)) }
            9 -> { binding.view9.setBackgroundColor(Color.rgb(155, 222, 86)) }
            10 -> { binding.view10.setBackgroundColor(Color.rgb(155, 222, 86)) }
        }
    }

    private fun printFalseButton(num: Int) {
        when(num) {
            1 -> { binding.view1.setBackgroundColor(Color.rgb(200, 200, 200)) }
            2 -> { binding.view2.setBackgroundColor(Color.rgb(200, 200, 200)) }
            3 -> { binding.view3.setBackgroundColor(Color.rgb(200, 200, 200)) }
            4 -> { binding.view4.setBackgroundColor(Color.rgb(200, 200, 200)) }
            5 -> { binding.view5.setBackgroundColor(Color.rgb(200, 200, 200)) }
            6 -> { binding.view6.setBackgroundColor(Color.rgb(200, 200, 200)) }
            7 -> { binding.view7.setBackgroundColor(Color.rgb(200, 200, 200)) }
            8 -> { binding.view8.setBackgroundColor(Color.rgb(200, 200, 200)) }
            9 -> { binding.view9.setBackgroundColor(Color.rgb(200, 200, 200)) }
            10 -> { binding.view10.setBackgroundColor(Color.rgb(200, 200, 200)) }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionData = intent.extras?.get("questionData") as ArrayList<Quiz>
        yourData = intent.extras?.get("yourData") as ArrayList<Int>
        time = intent.extras?.get("time") as Long

        val minute = time / 1000L / 60L
        val second = time / 1000L % 60L
        binding.timeView.text = getString(R.string.time_format).format(minute, second)

        var c = 0
        for (i in (0 .. 9)){
            if (yourData[i] == questionData[i].getCorrect() + 1){
                c++
                printTrueButton(i + 1)
            } else {
                printFalseButton(i + 1)
            }
        }
        binding.scoreView.text = c.toString() + getString(R.string.cout_quest_format_1)

        onClickQuestButton(1)

        binding.backmain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.view1.setOnClickListener{onClickQuestButton(1)}
        binding.view2.setOnClickListener{onClickQuestButton(2)}
        binding.view3.setOnClickListener{onClickQuestButton(3)}
        binding.view4.setOnClickListener{onClickQuestButton(4)}
        binding.view5.setOnClickListener{onClickQuestButton(5)}
        binding.view6.setOnClickListener{onClickQuestButton(6)}
        binding.view7.setOnClickListener{onClickQuestButton(7)}
        binding.view8.setOnClickListener{onClickQuestButton(8)}
        binding.view9.setOnClickListener{onClickQuestButton(9)}
        binding.view10.setOnClickListener{onClickQuestButton(10)}

    }
}