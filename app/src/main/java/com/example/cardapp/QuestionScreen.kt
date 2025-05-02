package com.example.cardapp

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

@Composable
fun QuestionScreen(navController: NavController, viewModel: QuizViewModel) {
    val currentQuestion = viewModel.currentQuestion
    val currentQuestionIndex = viewModel.currentQuestionIndex
    val showFeedback = viewModel.showFeedback
    val feedbackMessage = viewModel.feedbackMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Question ${currentQuestionIndex + 1} of 5",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentQuestion,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            Log.d("QuestionScreen", "True button press")
                            viewModel.answerQuestion(true) },
                        enabled = !showFeedback,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = "True")
                    }

                    Button(
                        onClick = {
                            Log.d("QuestionScreen", "False button press")
                            viewModel.answerQuestion(false) },
                        enabled = !showFeedback,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = "False")
                    }
                }
            }
        }

        if (showFeedback) {
            Text(
                text = feedbackMessage,
                fontSize = 18.sp,
                color = if (feedbackMessage.contains("Correct"))
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = {
                    if (currentQuestionIndex < 4) {
                        viewModel.loadNextQuestion()
                    } else {
                        navController.navigate("score")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}

// viewModel to manage the state of the quiz
class QuizViewModel {
    private val questions = arrayOf(
        "Nelson Mandela was the president in 1994",
        "The Great Wall of China is visible from space",
        "Vitamin C can cure the common cold",
        "The Pacific Ocean is the largest ocean on Earth",
        "The human body has 206 bones"
    )
    val answers = booleanArrayOf(true, false, false, true, true)

    var currentQuestionIndex by mutableStateOf(0)
    var currentQuestion by mutableStateOf(questions[0])
    var score by mutableStateOf(0)
    var showFeedback by mutableStateOf(false)
    var feedbackMessage by mutableStateOf("")
    val userAnswers = mutableStateListOf<Pair<String, Boolean>>()

    fun answerQuestion(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]
        userAnswers.add(Pair(questions[currentQuestionIndex], userAnswer))
        if (userAnswer == correctAnswer) {
            score++
            feedbackMessage = "Correct!"
        } else {
            feedbackMessage = "Incorrect!"
        }
        showFeedback = true
        //checking user answert and correct answer if they work together
        Log.d("QuizViewModel", "Answered Question: $currentQuestion, User Answer: $userAnswer, Correct: $correctAnswer")
    }

    fun loadNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            currentQuestion = questions[currentQuestionIndex]
            showFeedback = false
            Log.d("QuizViewModel", "Loaded Next Question: $currentQuestion")
        } else {
            Log.d("QuizViewModel", "No more questions. Quiz completed.")
        }
    }

    fun resetQuiz() {
        currentQuestionIndex = 0
        currentQuestion = questions[0]
        score = 0
        showFeedback = false
        feedbackMessage = ""
        userAnswers.clear()
        Log.d("QuizViewModel", "Quiz reset.")
    }
}