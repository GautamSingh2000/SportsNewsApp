package com.mindgeeks.sportsnews.Screens.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.mindgeeks.sportsnews.Components.MyModalBottomSheet
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.BatBallTheme
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.dark_blue
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.light_blue
import java.io.File

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatBallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComponent(onBackPressed = { onBackPressed() })
                }
            }
        }
    }
}


@Preview
@Composable
private fun mainComponentPreview() {
    MainComponent {
    }
}

@Composable
fun MainComponent(onBackPressed: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        actionBar(onBackPressed)
        Spacer(modifier = Modifier.height(15.dp))
        ProfilePic()
        Informationform()
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = light_blue,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = dark_blue)
                ),
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun Informationform() {

    var EmailState by remember {
        mutableStateOf("")
    }
    var PasswordState by remember {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(40.dp))
    OutlinedTextField(
        value = EmailState,
        onValueChange = {
            EmailState = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        label = { Text(text = "Email") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = dark_blue,
            focusedLeadingIconColor = dark_blue,
            focusedLabelColor = dark_blue,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),

        )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = PasswordState, onValueChange = {
            PasswordState = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        label = { Text(text = "Password") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        }, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = dark_blue,
            focusedLeadingIconColor = dark_blue,
            focusedLabelColor = dark_blue,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
fun actionBar(onBackPressed: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(light_blue),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = onBackPressed,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = light_blue,
                        contentColor = Color.White
                    )
                )
                {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back btn",
                        tint = Color.White,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 10.dp)
                    )
                }
                Text(
                    text = "Profile", fontStyle = FontStyle.Normal,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 60.dp)
                )
            }
        }
    }
}@Composable
fun ProfilePic() {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val cacheDirectory = File(context.cacheDir, "images")

    Box {
        Surface(
            modifier = Modifier.size(160.dp),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(1.dp, Color.LightGray),
            shadowElevation = 10.dp
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(160.dp)
                )
            } else {
                Image(
                    painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop
                )
            }
        }

        FilledIconButton(
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 20.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Image(
                painterResource(id = R.drawable.camera),
                contentDescription = "Camera",
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    if (showBottomSheet) {
        MyImageArea(
            directory = cacheDirectory,
            onSetUri = { newUri ->
                profileImageUri = newUri
                showBottomSheet = false
            }
        )
    }
}

@Composable
fun MyImageArea(
    directory: File? = null, // stored directory
    onSetUri: (Uri) -> Unit = {}
) {
    val context = LocalContext.current
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val authority = stringResource(id = R.string.fileprovider)

    fun getTempUri(): Uri? {
        directory?.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )

            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
        return null
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                tempUri.value?.let {
                    onSetUri.invoke(it)
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            tempUri.value?.let {
                takePhotoLauncher.launch(it)
            }
        }
    }

    MyModalBottomSheet(
        onDismiss = {
            // Handle dismiss action here if needed
        },
        onTakePhotoClick = {
            val permission = Manifest.permission.CAMERA
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                val tmpUri = getTempUri()
                tempUri.value = tmpUri
                tempUri.value?.let {
                    takePhotoLauncher.launch(it)
                }
            } else {
                cameraPermissionLauncher.launch(permission)
            }
        },
        onPhotoGalleryClick = {
            imagePicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    )
}