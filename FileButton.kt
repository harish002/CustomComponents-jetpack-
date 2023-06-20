package UI.NavGraph.Components.Form

import UI.NavGraph.Services.GenerateLeadApi.FileUpload.UploadApiService.UploadViewModel
import UI.NavGraph.Services.LoginApi.loginApi.getToken
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uijetpack.android.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@Composable
fun FileButton(
    onFileSelected: (String) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    id:String,
    isEnable :Boolean = true,
    painterResourceId:Int = R.drawable.baseline_file_upload_24
) {

    val context = LocalContext.current
    var file: String? = ""
    val _uri = remember { mutableStateOf<Uri?>(null) }
    var token = getToken(context = context)
    val finalToken = "Bearer $token"
    val chooseFile =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent())
        { uri ->
            uri?.let {
                _uri.value = uri
            }

        }
    Row() {

        if (isEnable) {

            TextButton(
                onClick = {
                    chooseFile.launch("*/*")
                },
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f)
                    .border(
                        1.dp, color = Color.Black,
                        shape = RectangleShape
                    ),
                enabled = isEnable

            ) {
                if (_uri.value == null) {
                    Text(text = title)
                } else {
                    val contentResolver = context.contentResolver
                    file = getFileName(contentResolver = contentResolver, uri = _uri.value!!)
                    file?.let { Text(text = it) }
                }
            }
            IconButton(onClick = {
                if (_uri.value == null) {
                    Toast.makeText(context, "Please select File", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    upload(context, _uri.value!!, viewModal = UploadViewModel(id,finalToken), title)
                    onFileSelected(_uri.value.toString())

                }

            }, modifier = Modifier.align(CenterVertically)) {
                Image(
                    painter = painterResource(painterResourceId),
                    contentDescription = "Upload Content"
                )
            }
        }
    }
}
//sending uri to uploadviewmodal

//private fun upload(context :
private fun upload(context: Context, _uri: Uri, viewModal: UploadViewModel,title: String) {
        val contentResolver = context.contentResolver

        val inputStream: InputStream? = contentResolver.openInputStream(_uri)
        val fileName = getFileName(contentResolver, _uri)

        if (inputStream != null && fileName != null) {
            var token = getToken(context = context)
            val finalToken = "Bearer $token"
            val requestBody = inputStream.readBytes().toRequestBody("/".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", fileName, requestBody)
            viewModal.uploadfile(part, context,title,finalToken)
         }
        else {
// Handle file retrieval or upload error
        }

}

private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    cursor?.use { cursor ->
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex != -1) {
                return cursor.getString(columnIndex)
            }
        }
    }
    return null
}

