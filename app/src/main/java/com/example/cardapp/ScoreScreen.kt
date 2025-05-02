package com.example.cardapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScoreScreen(navController: NavController, viewModel: QuizViewModel) {
    val score = viewModel.score

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Completed! Woohoo lmao!",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Your Score: $score out of 5",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            // Display a message based on the score if the score is 3 or higher great job else eish chief
            text = if (score >= 3) "Great job!" else "Keep practicing!",
            fontSize = 18.sp,
            color = if (score >= 3)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            // gonna have to figute out how to review questions
            onClick = { navController.navigate("review") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Review")
        }

        Button(
            // back to welcom sscreen
            onClick = { navController.popBackStack(route = "welcome", inclusive = false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Exit")
        }
    }
}