package com.example.poemgenerator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator.R

@Composable
fun Screen(
    poemViewModel: PoemViewModel = viewModel()
){
    var emotion by remember { mutableStateOf("") }
    var occasion by remember { mutableStateOf("") }
    var person by remember { mutableStateOf("") }
    val generatedPoem by remember { mutableStateOf("") }
    val uiState by poemViewModel.uiState.collectAsState()
    var result by rememberSaveable { mutableStateOf(generatedPoem) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.poem),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(modifier = Modifier.widthIn(min = 250.dp, max = 250.dp))
                {
                    Text(
                        text = stringResource(id = R.string.emotion),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = emotion,
                        onValueChange = { emotion = it },
                        singleLine = true,
                        shape = shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_emotion),
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
            item {
                Card(modifier = Modifier.widthIn(min = 250.dp, max = 250.dp))
                {
                    Text(
                        text = stringResource(id = R.string.occasion),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = occasion,
                        onValueChange = { occasion = it },
                        singleLine = true,
                        shape = shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_occasion),
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
            item {
                Card(modifier = Modifier.widthIn(min = 250.dp, max = 250.dp))
                {
                    Text(
                        text = stringResource(id = R.string.person),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = person,
                        onValueChange = { person = it },
                        singleLine = true,
                        shape = shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_person),
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        var showResult by remember { mutableStateOf(false) }
        Button(onClick = {
            poemViewModel.sendPrompt(emotion, occasion, person)
            showResult = true

        }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Generate Poem")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        else {
            if (uiState is UiState.Error) {
                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                result = (uiState as UiState.Success).outputText
            }
            if (showResult) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = result,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Generator(){
    Screen()
}
