package evgeniy.ryzhikov.guesstheflag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityGameMainBinding
import evgeniy.ryzhikov.guesstheflag.questions.QuestionManager
import evgeniy.ryzhikov.guesstheflag.utils.DrawableReader

class GameMain : AppCompatActivity() {
    private lateinit var binding: ActivityGameMainBinding
    private lateinit var drawableReader: DrawableReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawableReader = DrawableReader(this)

        binding.ivQuestion.setImageDrawable(drawableReader.getDrawable("albania"))

        test()
    }

    fun test() {
        val questionManager = QuestionManager(this)
        while (questionManager.isHaveNextQuestion()) {
           Log.d("QUEST", questionManager.answers.toString())
        }
    }
}