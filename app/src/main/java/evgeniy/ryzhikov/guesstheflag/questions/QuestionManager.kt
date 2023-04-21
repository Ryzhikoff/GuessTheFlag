package evgeniy.ryzhikov.guesstheflag.questions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.utils.DataFiles
import evgeniy.ryzhikov.guesstheflag.utils.DrawableReader

class QuestionManager(val context: Context) {
    private val drawableReader = DrawableReader(context)
    private val questions = ArrayList<Question>()
    private var counter  = 0

    init {
        generateQuestion()
    }

    private fun generateQuestion() {
        repeat(context.resources.getInteger(R.integer.number_of_question_per_round)) {
            questions.add(newQuestion())
        }
    }

    private fun newQuestion() : Question {
        val answers = createAnswers()
        val correctAnswer = answers[0]
        answers.shuffle()
        return Question(correctAnswer, answers)
    }

    private fun createAnswers() : ArrayList<String> {
        val listOfNames = DataFiles().getListOfNames()
        val answers  = ArrayList<String>()
        Log.d("CREATE ANSWERS", "start create answers")
        while(answers.size < 4) {
            val answer = listOfNames.random()
            if (!answers.contains(answer)) {
                answers.add(answer)
            }
        }
        Log.d("CREATE ANSWERS", "finish create answers")
        return answers
    }

    fun isHaveNextQuestion() : Boolean {
        Log.d("COUNTER", counter.toString() + " " + questions.size.toString())
        counter++
        return (questions.size - 1) >= counter
    }

    val answers: ArrayList<String>
        get() {
            return questions[counter].answers
        }

    val drawableQuestion: Drawable?
        get() {
            return drawableReader.getDrawable(questions[counter].correctAnswer)
        }
}
