package com.example.cardapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReviewScreen(navController: NavController, viewModel: QuizViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                text = "Review Your Answers",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        itemsIndexed(viewModel.userAnswers) { index, (question, userAnswer) ->
            val correctAnswer = viewModel.answers[index]
            val isCorrect = userAnswer == correctAnswer

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Q${index + 1}: $question", fontSize = 18.sp)
                    Text(text = "Your Answer: ${if (userAnswer) "True" else "False"}")
                    Text(text = "Correct Answer: ${if (correctAnswer) "True" else "False"}")
                    Text(
                        text = if (isCorrect) "Correct!" else "Incorrect",
                        color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        item {
            Button(
                onClick = { navController.navigate("welcome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp)
            ) {
                Text(text = "Back to Home")
            }
        }
    }
}