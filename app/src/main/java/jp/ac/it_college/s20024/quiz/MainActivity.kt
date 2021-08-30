package jp.ac.it_college.s20024.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import jp.ac.it_college.s20024.quiz.databinding.ActivityMainBinding
import jp.ac.it_college.s20024.quiz.quiz.Quiz
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var data = arrayListOf<Quiz>()
    private val spinnerItems = arrayListOf("s20007", "s20008", "s20010", "s20014", "s20016", "s20024")
    private var filename = "s20024"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            data = arrayListOf<Quiz>()

            var a: InputStream? = null
            var i: BufferedReader? = null

            try {
                try {
                    a = assets.open("${filename}.csv")
                    i = BufferedReader(InputStreamReader(a))
                    i.readLine()

                    do {
                        val splitData = arrayListOf<String>()
                        val e = i.readLine()
                        for (o in e.split(",")) {splitData.add(o)}
                        if (splitData.size == 6) {splitData.add("")}
                        data.add(Quiz(splitData[0], splitData[1], splitData[2], splitData[3], splitData[4], splitData[5], splitData[6]))
                    } while (e != null)
                } finally {
                    a?.close()
                    i?.close()
                }
            } catch (e: Exception) {
                println(e)
            }

            val intent = Intent(this, Question::class.java)
            data.shuffle()
            intent.putExtra("questionsData", data)
            startActivity(intent)
        }

        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            spinnerItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filename = spinnerItems[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                filename = "s20024"
            }
        }
    }
}