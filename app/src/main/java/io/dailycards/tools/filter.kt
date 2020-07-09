package io.dailycards.tools

import android.content.Context
import io.dailycards.tools.db.DB

fun getPossibleAnswers(rightAnswer: String, allAnswers: List<String>, answersCount: Int = 4) : List<String> {
    val answers = mutableListOf(rightAnswer)
    for (a in allAnswers.shuffled()) {
        if (a !in answers) answers.add(a)
        if (answers.size >= answersCount) break
    }
    return answers.shuffled()
}

fun getCardContent(content: Context, dbId: Int) : List<Pair<String, String>> {
    val cardContent = mutableListOf<Pair<String, String>>()
    DB(content).run {
        query(arrayOf(DB.ID, DB.QUESTIONS_FILEPATH, DB.ANSWERS_FILEPATH),
            "${DB.ID} = $dbId").run {
            moveToFirst()
            val questions = readFile(content, this, DB.QUESTIONS_FILEPATH)
            val answers = readFile(content, this, DB.ANSWERS_FILEPATH)
            var i = 0
            while (i < questions.size) {
                cardContent.add(questions[i] to answers[i])
                i++
            }
            close()
        }
        close()
    }
    return cardContent
}

fun getCardContent(questions: List<String>, answers: List<String>) : List<Pair<String, String>> {
    val cardContent = mutableListOf<Pair<String, String>>()
    var i = 0
    while (i < questions.size) {
        cardContent.add(questions[i] to answers[i])
        i++
    }
    return cardContent
}

fun getQuestionsAnswersPair(cardContent: List<Pair<String, String>>) : Pair<List<String>, List<String>> {
    val questions = mutableListOf<String>()
    val answers = mutableListOf<String>()
    for (cardContentItem in cardContent) {
        questions.add(cardContentItem.first)
        answers.add(cardContentItem.second)
    }
    return questions to answers
}