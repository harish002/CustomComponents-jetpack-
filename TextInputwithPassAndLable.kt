package UI.NavGraph.Components.Form

import UI.NavGraph.Screens.leads.screens.errorFlag
import UI.NavGraph.Screens.leads.screens.isValidEmail
import UI.NavGraph.Screens.leads.screens.isValidNumber
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
//fun TextInput(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    label: String,
//    array: List<String>? = null,
//    inputType: KeyboardOptions,
//    passwordField : Boolean = false,
//    isError:Boolean = false
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    var passwordVisibility by remember { mutableStateOf(false) }
//
//    // use outlinedtextfield as textinput
//    OutlinedTextField(
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
//        value = value,
//        isError = isError,
//        onValueChange = onValueChange,
//        modifier = modifier.fillMaxWidth()
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
//                    keyboardController?.hide() // Hide the keyboard on Enter key press
//                    true
//                } else {
//                    false
//                }
//            }
//        ,
//        label = {
//            Text(text = label)
//        },
//        visualTransformation =
//        if(passwordField){
//             if(passwordVisibility){
//                 VisualTransformation.None
//            }else{
//                 PasswordVisualTransformation()
//             }
//        }else{
//            VisualTransformation.None
//        },
//        trailingIcon = {
//            if(passwordField){
//                IconButton(
//                    onClick = { passwordVisibility = !passwordVisibility }
//                ) {
//                    Icon(
//                        imageVector = if (!passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
//                        contentDescription = "Toggle Password Visibility"
//                    )
//                }
//            }
//
//        },
//    )
//}
//
//
//@Preview
//@Composable
//fun TextInputPreviews(){
////    TextInput(value = "Name", onValueChange = {}, label = "Name" )
//}

fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    inputType: KeyboardOptions,
    passwordField: Boolean = false,
    array: List<String?>? = null,
    arrayBusi: List<String?>? = null,
    showError: Boolean = true,


//    ,max:Int=1
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember { mutableStateOf(false) }
    val isError by remember(value, array,arrayBusi) { derivedStateOf {
        if (label == "PIN Code")  {
                array != null && value.isNotEmpty() &&( value in array ||  value.length > 6 ||
                        !value.matches(Regex("^[0-9]+\$")))
        }
        else if (label == "Type of Business") {
            val capsVal=value.uppercase()
            arrayBusi != null && value.isNotEmpty() && capsVal in arrayBusi
        }
        else if(label == "Email" && value.isNotEmpty()){
            !isValidEmail(value)
        }
        else if(label == "Contact Number" && (!value.matches(Regex("^[0-9]+\$"))
                    || value.length > 10 ) && value.isNotEmpty())
        {
             !isValidNumber(value)
        }
        else {
            false
        } }
    }




        // Use OutlinedTextField as TextInput
    OutlinedTextField(
        keyboardOptions = inputType,
        value = value,
        isError = isError,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                    keyboardController?.hide() // Hide the keyboard on Enter key press
                    true
                } else {
                    false
                }
            },
        label = {
            Text(text = label)
        },
        visualTransformation =
        if (passwordField) {
            if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (passwordField) {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    Icon(
                        imageVector = if (!passwordVisibility) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        },
    )

    if (showError && isError && label =="PIN Code") {

        Text(text = "Negative OR Invalid PinCode!!",
            modifier=Modifier.padding(horizontal = 15.dp, vertical = 0.5.dp),
            color = androidx.compose.ui.graphics.Color.Red)
            errorFlag = true
    }
    else{errorFlag = false}
    if (showError && isError && label =="Type of Business") {
        Text(text = "Negative Business Type",
            modifier=Modifier.padding(horizontal = 15.dp, vertical = 0.5.dp),
            color = androidx.compose.ui.graphics.Color.Red)
        errorFlag = true
    }
    else{errorFlag = false}

    if (showError && isError && label =="Contact Number") {
        Text(text = "Incorrect Format!",
            modifier=Modifier.padding(horizontal = 15.dp, vertical = 0.5.dp),
            color = androidx.compose.ui.graphics.Color.Red)
        errorFlag = true

    }
    else{errorFlag = false}

    if (showError && isError && label =="Email") {
        Text(text = "Invalid Email Format",
            modifier=Modifier.padding(horizontal = 15.dp, vertical = 0.5.dp),
            color = androidx.compose.ui.graphics.Color.Red)
        errorFlag = true

    }
    else{errorFlag = false}


//    if(label == "Email"){
//        Text(
//            text = emailFieldValue,
//            color = Color.Red,
//            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.5.dp)
//        )
//    }

}

fun String.isValidPinCode(): Boolean {
    val pinCodeRegex = Regex("^[1-9][0-9]{5}$")
    return matches(pinCodeRegex) && length ==6
}

fun isValidNumber(value: String): Boolean {
    val maxDigits = 10
    val numericPattern = "^[0-9]+$"
    return try {
        if (value.matches(numericPattern.toRegex())) {
            val parsedNumber = value.toLong()
            val digitCount = parsedNumber.toString().length
            digitCount <= maxDigits
        } else {
            false
        }
    } catch (e: NumberFormatException) {
        false
    }
}



fun isValidEmail(email:String): Boolean {
    val emailRegex = Regex(pattern = "^[A-Za-z]+[0-9]*@[A-Za-z]+\\.[A-Za-z]+\$")
    return emailRegex.matches(email)
}
