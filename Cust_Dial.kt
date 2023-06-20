package UI.NavGraph.Components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComposeEditModal() {
    val message = remember { mutableStateOf("Edit Me") }
    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("ComposeEditModal")
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message.value,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        editMessage.value = message.value
                        openDialog.value = true
                    }
                ) {
                    Text("Open Dialog")
                }

                Button(
                    onClick = {
                        Log.d("hee","Check success")
                    }
                ) {
                    Text("Open check")
                }
            }
        }

        if (openDialog.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = contentColorFor(MaterialTheme.colors.background)
                            .copy(alpha = 0.6f)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            openDialog.value = false
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                CustomDialog(message, openDialog, editMessage)
            }
        }
    }
}

@Composable
fun CustomDialog(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "Input Message")

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = editMessage.value,
                onValueChange = { editMessage.value = it },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Resend")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    message.value = editMessage.value
                    openDialog.value = false
                }
            ) {
                Text("Submit")
            }
        }
    }
}


@Composable
@Preview
fun preve(){
    ComposeEditModal()
}
