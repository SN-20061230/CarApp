package uz.sardor.carapp.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.navigation.NavController
import uz.sardor.carapp.Database.CarClass
import uz.sardor.carapp.Database.CarData
import uz.sardor.carapp.Database.UserData
import uz.sardor.carapp.R
import uz.sardor.carapp.ui.theme.poppinsFamily
import uz.sardor.carapp.ui.theme.primaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCarScreen(navController: NavController){


    val context = LocalContext.current
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var year by remember { mutableStateOf(TextFieldValue("")) }
    var mileage by remember { mutableStateOf(TextFieldValue("")) }
    var condition by remember { mutableStateOf(TextFieldValue(""))}
    var color by remember { mutableStateOf(TextFieldValue(""))}
    var phone by remember { mutableStateOf(TextFieldValue(""))}
    var tg_username by remember { mutableStateOf(TextFieldValue(""))}

    var price by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var imgUrl by remember {mutableStateOf("") }
    val isUploading  = remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null)}
    var showDialog by remember { mutableStateOf(false)}
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let{
            bitmap = if(Build.VERSION.SDK_INT <28){
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
            else{
                var source = ImageDecoder.createSource(context.contentResolver,it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }
    val cLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.TakePicturePreview()
    ){
        bitmap = it
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("Selling") }) {

                            Image(
                                painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                                contentDescription = null,
                                modifier = Modifier.width(80.dp).height(50.dp)
                            )
                        }

                    },


                    )
            }
        )
        {

        }
        Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if(isUploading.value){
                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp).height(60.dp),
                    color = primaryColor
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize().padding(top =60.dp)
            .verticalScroll(rememberScrollState())

            , horizontalAlignment = Alignment.CenterHorizontally){
            if(bitmap !=null){
                Image(
                    bitmap = bitmap?.asImageBitmap()!!,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                        .clickable { showDialog = true }
                )
            }
            else{
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .clickable { showDialog = true }
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp),
                value = title,
                leadingIcon = {Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.padding(8.dp),tint = primaryColor) },
                onValueChange = { title = it },
                label = { Text("Car Name",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                )
            )
            Row( modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 10.dp), horizontalArrangement = Arrangement.SpaceAround) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = year,
                    leadingIcon = {Icon(imageVector =Icons.Default.Edit, contentDescription = null, modifier = Modifier.padding(8.dp),tint = primaryColor) },
                    onValueChange = { year = it },
                    label = { Text("Year",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primaryColor,
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = mileage,
                    leadingIcon = {
                        Icon(imageVector =Icons.Default.Star, contentDescription = null, modifier = Modifier.padding(8.dp),tint = primaryColor) },
                    onValueChange = { mileage = it },
                    label = { Text("Mileage",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primaryColor,
                    )
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp),
                value = color,
                leadingIcon = {
                    Icon(imageVector =Icons.Default.Edit, contentDescription = null, modifier = Modifier.padding(8.dp),   tint = primaryColor) },
                onValueChange = { color = it },
                label = { Text("Color",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                )
            )
            Row( modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 10.dp), horizontalArrangement = Arrangement.SpaceAround){
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = condition,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_vpn_key_24),
                            contentDescription = "Komilni puli",
                        )
                    },
                    onValueChange = { condition = it },
                    label = { Text("Condition",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primaryColor,
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = price,
                    leadingIcon = {Image(
                        painter = painterResource(id = R.drawable.baseline_attach_money_24),
                        contentDescription = "null",
                    )},
                    onValueChange = { price = it },
                    label = { Text("Price",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primaryColor,
                    )
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp),
                value = phone,
                leadingIcon = {Icon(imageVector =Icons.Default.Phone, contentDescription = null, modifier = Modifier.padding(8.dp),tint = primaryColor) },
                onValueChange = { phone = it },
                label = { Text("Telefon",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp),
                value = tg_username,
                leadingIcon = {Icon(imageVector =Icons.Default.Edit, contentDescription = null, modifier = Modifier.padding(8.dp), tint = primaryColor) },
                onValueChange = { tg_username = it },
                label = { Text("Telegram Username",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp).height(100.dp),
                value = description,
                onValueChange = { description = it },
                label = { Text("Additional description",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                )
            )
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (title.text == "" || mileage.text =="" || condition.text == ""|| year.text == ""
                        || year.text == "" || description.text == "" || price.text == "" || phone.text == "" || tg_username.text == "" || color.text == ""
                        || bitmap ==null) {
                        isUploading.value = false
                        Toast.makeText(context, "Fill all", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else{
                        isUploading.value = true
                        bitmap.let{bitmap ->
                            if(bitmap != null){
                                CarData.uploadImageToFirebase(bitmap, context as ComponentActivity) { success, imageUrl ->
                                    isUploading.value = false
                                    if(success){
                                        imageUrl.let{
                                            imgUrl = it
                                        }
                                        CarData.CreateCar(CarClass(UserData.getUserSaved(context),title.text, year.text, price.text,mileage.text,condition.text,color.text, description.text, imgUrl, tg_username.text, phone.text))
                                        Toast.makeText(
                                            context, "Mashina muvaqqiyatli qo'shildi", Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("Main")
                                    }
                                    else{
                                        Toast.makeText(context, "Not Save", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(255,165,0))
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    text = "Qo'shish",
                    fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(
                )
                .padding(bottom = 10.dp)
        ) {
            if(showDialog){
                Row(
                    verticalAlignment =Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(250.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(255,165,0))
                ){
                    Column (modifier = Modifier.padding(start = 10.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    cLauncher.launch()
                                    showDialog = false
                                }
                        )
                        Text(
                            text = "Camera",
                            fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Column(){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_image_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    launcher.launch("image/*")
                                    showDialog = false
                                }
                        )
                        Text(
                            text = "Gallery",
                            fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 60.dp)
                    ){
                        Text(
                            text = "X",
                            fontFamily = poppinsFamily, fontWeight = FontWeight.Normal,
                            color = Color.White,
                            modifier = Modifier
                                .clickable { showDialog = false }
                        )
                    }
                }
            }
        }

    }}