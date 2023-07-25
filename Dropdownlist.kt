package UI.NavGraph.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DropdownList(dropdownOptions:List<String>){
    var selectedOption by remember { mutableStateOf("") }
    var filteredOptions by remember { mutableStateOf(dropdownOptions) }
    var inputText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                filteredOptions = dropdownOptions.filter { option ->
                    option.contains(inputText, ignoreCase = true)
                }
                showOptions = true
            },
            label = { Text("Selected Option") },
            singleLine = true,
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = {
                        inputText = ""
                        selectedOption = ""
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        if (showOptions && filteredOptions.isNotEmpty()) {
            BoxWithConstraints {
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val isInPopup = offset.x >= 0f && offset.x <= constraints.maxWidth &&
                                        offset.y >= 0f && offset.y <= constraints.maxHeight
                                if (!isInPopup) {
                                    showOptions = false
                                }
                            }
                        }
                )
                Card(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        for (option in filteredOptions) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption = option
                                    inputText = option
                                    showOptions = false
                                }
                            ) {
                                Text(text = option)
                            }
                        }
                    }
                }
            }
        } else if (showOptions && filteredOptions.isEmpty()) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(onClick = {}) {
                    Text("No matching options")
                }
            }
        }
    }


}