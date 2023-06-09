package evgeniy.ryzhikov.guesstheflag.questions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import evgeniy.ryzhikov.guesstheflag.GameMain
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.settings.NUMBER_OF_QUESTION_PER_ROUND
import evgeniy.ryzhikov.guesstheflag.utils.DataFiles
import evgeniy.ryzhikov.guesstheflag.utils.DrawableReader
import kotlin.random.Random

class QuestionManager(private val context: Context) {
    private val drawableReader = DrawableReader(context)
    private val questions = ArrayList<Question>()
    private var counter  = 0

    init {
        generateQuestion()
    }

    private fun generateQuestion() {
        repeat(NUMBER_OF_QUESTION_PER_ROUND) {
            questions.add(newQuestion())
        }
    }

    private fun newQuestion() : Question {
        val mapOfIdNames = DataFiles().getMapOfNames()
        val answers = createAnswers(mapOfIdNames)

        val nameForPath = answers[0]
        val answersName = ArrayList<String>()
        answers.forEach {
            answersName.add(context.resources.getString(mapOfIdNames[it]!!))
        }
        val correctAnswer = answersName[0]
        answersName.shuffle()
        return Question(nameForPath, correctAnswer,  answersName)
    }

    private fun createAnswers(mapOfIdNames: Map<String, Int>) : ArrayList<String> {
        val answers = ArrayList<String>()
        while(answers.size < 4) {
            val answer = mapOfIdNames.entries.elementAt(Random.nextInt(mapOfIdNames.size)).key
            if (!answers.contains(answer)) {
                answers.add(answer)
            }
        }
        return answers
    }

    fun isCorrectAnswer(answer: String) : Boolean {
        return questions[counter].correctAnswer.equals(answer)
    }

    fun isHaveNextQuestion() : Boolean {
        counter++
        return (questions.size - 1) >= counter
    }

    val answers: ArrayList<String>
        get() {
            return questions[counter].answers
        }

    val drawableQuestion: Drawable?
        get() {
            return drawableReader.getDrawable(questions[counter].nameForPath)
        }
}
