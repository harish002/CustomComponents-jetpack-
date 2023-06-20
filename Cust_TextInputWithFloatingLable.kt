package UI.NavGraph.Components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun cust_Textinput(
    lable:String,
    boardtype:KeyboardType = KeyboardType.Text,
    maxlenght:Int=110,
    value: String,
// keyboardActions: KeyboardActions
    onValueChange: (String) -> Unit,
){
    Column(modifier = Modifier.padding(35.dp,8.dp)) {
        var textState by remember { mutableStateOf("") }

        val maxLength = maxlenght
        val lightBlue = Color(0xffd8e6ff)
        val blue = Color(0xff76a9ff)
        Text(
            text = lable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp, start = 5.dp),
            textAlign = TextAlign.Start,
            color = blue,
            fontSize = 16.sp

        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            keyboardOptions =KeyboardOptions(keyboardType = boardtype),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (textState.isNotEmpty()) {
                    IconButton(onClick = { textState = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
// Text(
// text = "${textState.length} / $maxLength",
// modifier = Modifier
// .fillMaxWidth()
// .padding(top = 4.dp),
// textAlign = TextAlign.End,
// color = blue
// )
    }
}
//
//@Preview
//@Composable
//fun customTextinputPreview(){
// cust_Textinput(lable = "First")
//}

@Preview
@Composable
fun customTextinputPreview(){
    cust_Textinput(lable = "First", boardtype = KeyboardType.Text, maxlenght = 110, value = "First", onValueChange = {})
}