package jp.ac.it_college.s20024.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import com.bumptech.glide.Glide
import jp.ac.it_college.s20024.quiz.databinding.ActivityQuestionBinding
import jp.ac.it_college.s20024.quiz.quiz.Quiz

class Question : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private var i = 0
    private lateinit var trueSound: SoundPool
    private var trueSoundId = 0
    private lateinit var falseSound: SoundPool
    private var falseSoundId = 0

    private var data = arrayListOf<Quiz>()

    private var timer = MyCountUpTimer(data.size * 10L * 1000L + 30L * 1000L, 100)
    private var downTimer = MyCountDownTimer(10L * 1000L, 100)
    private var resultTimer = MyResultPrintTimer(1000L, 100)
    private var nowCount = 0
    private var resultTimerTF = false
    private var yourData = arrayListOf<Int>()

    override fun onResume() {
        super.onResume()

        trueSound =
            SoundPool.Builder().run{
                val audioAttributes = AudioAttributes.Builder().run {
                    setUsage(AudioAttributes.USAGE_ALARM)
                    build()
                }
                setMaxStreams(1)
                setAudioAttributes(audioAttributes)
                build()
            }
        trueSoundId = trueSound.load(this, R.raw.maru, 1)

        falseSound =
            SoundPool.Builder().run{
                val audioAttributes = AudioAttributes.Builder().run {
                    setUsage(AudioAttributes.USAGE_ALARM)
                    build()
                }
                setMaxStreams(1)
                setAudioAttributes(audioAttributes)
                build()
            }
        falseSoundId = falseSound.load(this, R.raw.batu, 1)
    }

    override fun onPause() {
        super.onPause()
        trueSound.release()
        falseSound.release()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        yourData = arrayListOf<Int>()
        timer.cancel()
        downTimer.cancel()
        resultTimer.cancel()
        finish()
        return true
    }

    inner class MyCountUpTimer(millisInPast: Long, countUpInterval: Long):
        CountDownTimer(millisInPast, countUpInterval) {
        private val fastTime = millisInPast
        private var countTime = 0L
        override fun onTick(millisUnitFinished: Long) {
            val minute = (fastTime - millisUnitFinished) / 1000L / 60L
            val second = (fastTime - millisUnitFinished) / 1000L % 60L
            countTime = fastTime - millisUnitFinished
            binding.allTmerText.text = getString(R.string.time_format).format(minute, second)
        }
        override fun onFinish() {
        }
        fun getTime(): Long {
            return countTime
        }
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long):
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUitFinished: Long) {
            binding.cellTmerView.progress = (millisUitFinished / 100).toInt()
        }
        override fun onFinish() {
            printResult(0)
        }
    }

    inner class MyResultPrintTimer(millisInFuture: Long, countDownInterval: Long):
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisInFuture: Long) {}
        override fun onFinish() {
            nextQuestion()
            resultTimerTF = false
            binding.answerButton1.setBackgroundColor(Color.rgb(95, 73, 180)) // #95CEB4
            binding.answerButton2.setBackgroundColor(Color.rgb(95, 73, 180))
            binding.answerButton3.setBackgroundColor(Color.rgb(95, 73, 180))
            binding.answerButton4.setBackgroundColor(Color.rgb(95, 73, 180))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewQuestion(num: Int) {
        nowCount = num
        downTimer.cancel()
        downTimer.start()

        val cellData = data[num]

        val cellQuestion = cellData.getQuest()
        val cellQuiz = cellData.getQuiz()
        val cellImgUrl =
            resources.getIdentifier(cellData.getImageUrl().split(".")[0], "drawable", packageName)

        binding.questionView.text = cellQuestion
        binding.answerButton1.text = cellQuiz[0]
        binding.answerButton2.text = cellQuiz[1]
        binding.answerButton3.text = cellQuiz[2]
        binding.answerButton4.text = cellQuiz[3]
        binding.kyokaText.text = cellData.getImageKyoka()
        Glide.with(this).load(cellImgUrl).into(binding.imageView)

        binding.countQuizText.text = (num + 1).toString() + getString(R.string.cout_quest_format)
    }

    fun printResult(n: Int) {
        downTimer.cancel()
        resultTimer.cancel()
        resultTimer.start()
        resultTimerTF = true

        val cellCorrect = data[nowCount].getCorrect() + 1
        binding.kyokaText.text = ""

        yourData.add(n)

        printTrue(cellCorrect)
        if (n == cellCorrect) {
            trueSound.play(trueSoundId, 1.0f, 100f, 0, 0, 1.0f)
            binding.imageView.setImageResource(R.drawable.t)
        } else {
            printFalse(n)
            falseSound.play(falseSoundId, 1.0f, 100f, 0, 0, 1.0f)
            binding.imageView.setImageResource(R.drawable.f)
        }
    }

    private fun printTrue(num: Int) {
        when(num) {
            1 -> { binding.answerButton1.setBackgroundColor(Color.rgb(155, 222, 86)) }
            2 -> { binding.answerButton2.setBackgroundColor(Color.rgb(155, 222, 86)) }
            3 -> { binding.answerButton3.setBackgroundColor(Color.rgb(155, 222, 86)) }
            4 -> { binding.answerButton4.setBackgroundColor(Color.rgb(155, 222, 86)) }
        }
    }

    private fun printFalse(num: Int) {
        when(num) {
            1 -> { binding.answerButton1.setBackgroundColor(Color.rgb(200, 200, 200)) }
            2 -> { binding.answerButton2.setBackgroundColor(Color.rgb(200, 200, 200)) }
            3 -> { binding.answerButton3.setBackgroundColor(Color.rgb(200, 200, 200)) }
            4 -> { binding.answerButton4.setBackgroundColor(Color.rgb(200, 200, 200)) }
        }
    }

    fun nextQuestion() {
        ++i
        if (i == 10) {
            timer.cancel()
            downTimer.cancel()
            resultTimer.cancel()
            val intent = Intent(this, ViewResult::class.java)
            intent.putExtra("questionData", data)
            intent.putExtra("yourData", yourData)
            intent.putExtra("time", timer.getTime())
            startActivity(intent)
        }
        else {
            viewQuestion(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // quizName = intent.getStringExtra("QuizName")!!
        data = intent.extras?.get("questionsData") as ArrayList<Quiz>

        binding.answerButton1.setBackgroundColor(Color.rgb(95, 73, 180)) // #95CEB4
        binding.answerButton2.setBackgroundColor(Color.rgb(95, 73, 180))
        binding.answerButton3.setBackgroundColor(Color.rgb(95, 73, 180))
        binding.answerButton4.setBackgroundColor(Color.rgb(95, 73, 180))

        timer.start()
        viewQuestion(i)

        binding.back.setOnClickListener {
            timer.cancel()
            downTimer.cancel()
            resultTimer.cancel()
            finish()
        }
        binding.answerButton1.setOnClickListener {
            if (!resultTimerTF) {
                printResult(1)
            }
        }
        binding.answerButton2.setOnClickListener {
            if (!resultTimerTF) {
                printResult(2)
            }
        }
        binding.answerButton3.setOnClickListener {
            if (!resultTimerTF) {
                printResult(3)
            }
        }
        binding.answerButton4.setOnClickListener {
            if (!resultTimerTF) {
                printResult(4)
            }
        }
    }
}