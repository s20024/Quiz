package jp.ac.it_college.s20024.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.ac.it_college.s20024.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("unnkoooooooooooooooooooooo")
    }
}