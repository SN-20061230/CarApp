package uz.sardor.carapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.sardor.carapp.Database.CarData
import uz.sardor.carapp.R
import uz.sardor.carapp.ui.theme.poppinsFamily
import uz.sardor.carapp.ui.theme.primaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@ExperimentalMaterial3Api
@Composable
fun DetailsScreen(name:String, price:String,condition:String, description:String,year:String,mile:String, tg_username:String, phone:String, navController: NavController) {
    var img by remember { mutableStateOf("") }
    CarData.GetCarImage(name) {
        img = it
    }
    val dialLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        // Handle result if needed
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Main") }) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                            contentDescription = null,
                            modifier = Modifier.width(80.dp).height(50.dp)
                        )
                    }

                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phone")
                        }
                        dialLauncher.launch(intent)
                    }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_local_phone_24),
                            contentDescription = null,
                            modifier = Modifier.width(50.dp).height(35.dp)
                        )
                    }
                },

                )
        }
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = rememberAsyncImagePainter(img),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(0.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 20.dp, end = 10.dp)
                , verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "$year $name",
                    modifier = Modifier.border(
                        BorderStroke(1.dp, primaryColor),
                        shape = RoundedCornerShape(12.dp),
                    )
                        .padding(10.dp).fillMaxWidth(),

                    fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                )
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Text(text = "Narxi: $price $",
                        modifier = Modifier.border(
                            BorderStroke(1.dp, primaryColor),
                            shape = RoundedCornerShape(12.dp),
                        )
                            .padding(10.dp).weight(0.5f),
                        fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold

                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Holati: $condition ",
                        modifier = Modifier.border(
                            BorderStroke(1.dp, primaryColor),
                            shape = RoundedCornerShape(12.dp),
                        )
                            .padding(10.dp).weight(0.5f),
                        fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold

                    )
                }
                Text(text = "Yurgan: $mile km",
                    modifier = Modifier.border(
                        BorderStroke(1.dp, primaryColor),
                        shape = RoundedCornerShape(12.dp),
                    )
                        .padding(10.dp).fillMaxWidth(),
                    fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                )
                Text(text = "Qo'shimcha: $description",
                    modifier = Modifier.border(
                        BorderStroke(1.dp, primaryColor),
                        shape = RoundedCornerShape(12.dp),
                    )
                        .padding(10.dp).fillMaxWidth(),
                    fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                )
                Text(text = "Telefon: $phone",
                    modifier = Modifier.border(
                        BorderStroke(1.dp, primaryColor),
                        shape = RoundedCornerShape(12.dp),
                    )
                        .padding(10.dp).fillMaxWidth(),
                    fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                )



                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(55.dp)
                        .width(400.dp),
                    onClick = {

                        scope.launch {
                            delay(300)
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color(255, 87, 34, 255))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                        text = "Contacts",
                        fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {

            Column(modifier = Modifier.padding(top = 30.dp, bottom  =30.dp)){
                IntegrationScreen(tg_username)
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(55.dp)
                        .width(400.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phone")
                        }
                        dialLauncher.launch(intent)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color(255, 87, 34, 255))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_local_phone_24_white),
                        contentDescription = null,
                        modifier = Modifier.width(50.dp).height(35.dp)
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                        text = "Qo'ngiroq qilish",
                        fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                    )
                }
            }



        },
        sheetPeekHeight = 0.dp
    ) {

    }
}
@Composable
fun IntegrationScreen(tgUsername: String) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TelegramButton(username = tgUsername)
    }
}
@Composable
fun TelegramButton(username: String) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .height(55.dp)
            .width(400.dp),
        onClick = {
            openTelegramProfile(context, username)
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color(255, 87, 34, 255))
    ) {

        Image(
            painter = painterResource(id = R.drawable.baseline_send_24),
            contentDescription = null,
            modifier = Modifier.width(50.dp).height(50.dp)
        )
        Text(
            text = "Telegram: $username",
            fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
        )
    }
}
private fun openTelegramProfile(context: android.content.Context, username: String) {
    try {
        val uri = Uri.parse("https://t.me/$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
//
    }
}