package evgeniy.ryzhikov.guesstheflag.domain.questions


data class Question(val nameForPath: String, val correctAnswer: String, val answers: ArrayList<String>)
