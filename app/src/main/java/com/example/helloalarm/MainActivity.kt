package com.example.helloalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloalarm.ui.theme.HelloAlarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAlarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val myAlarm = MyAlarm(this)
                    AlarmTest(
                        scheduleAlarm = {
                            myAlarm.schedule(it)
                        },
                        cancel = {
                            myAlarm.cancel()
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlarmTest(
    scheduleAlarm: (String) -> Unit,
    cancel: () -> Unit,
) {
    var alarmId by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    DisposableEffect(Unit) {
        onDispose {
            cancel()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = alarmId,
            onValueChange = {
                alarmId = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                scheduleAlarm("Hello $alarmId")
            }
        ) {
            Text("Set Alarm")
        }

        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                cancel()
            }
        ) {
            Text("Cancel")
        }
    }
}

@Preview
@Composable
fun PreviewAlarmTest() {
    AlarmTest({}, {})
}
