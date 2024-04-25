package uz.sardor.carapp.Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uz.sardor.carapp.Database.CarClass
import uz.sardor.carapp.Database.CarData
import uz.sardor.carapp.Database.UserData
import uz.sardor.carapp.R
import uz.sardor.carapp.navigation.Screens
import uz.sardor.carapp.ui.theme.poppinsFamily

@Composable
fun RecommendItem(name: String, price:String, condition: String,description: String,imgUrl:String,year:String, mile:String, tg_username:String, phonenumber:String, navController: NavController){
    val context = LocalContext.current
    var cars by remember {
        mutableStateOf<List<CarClass>>(emptyList())
    }
    CarData.GetCars { list ->
        cars = list
    }
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)

        .border(
            BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(12.dp),)
        .clickable {
            navController.navigate(route = Screens.Details.getFullRoute(name = name, price = price,condition = condition,description = description, year = year, mile = mile, tg_username = tg_username, phone = phonenumber))

        },   colors = CardDefaults.cardColors(
        containerColor = Color.White,
    )){
        var isClicked by remember {
            mutableStateOf(false)
        }

        UserData.isFavourite(UserData.getUserSaved(context), name) {
            isClicked = it
        }


        Column(modifier = Modifier){

            Image(
                painter = rememberAsyncImagePainter(imgUrl),
                contentDescription = "gfg image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )


            Row(modifier = Modifier.align(Alignment.End)){
                IconButton(
                    onClick = {
                        if (!isClicked) UserData.FavouritesCreate(UserData.getUserSaved(context), name)
                        else UserData.FavouritesDelete(UserData.getUserSaved(context), name)
                        isClicked = !isClicked
                    },
                ) {
                    if (isClicked) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_favorite_24),
                            contentDescription = null,
                        )
                    }
                    else {
                        Image(
                            painter =
                            painterResource(id = R.drawable.baseline_favorite_border_24),
                            contentDescription = null,
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "$name".uppercase(),modifier = Modifier.fillMaxWidth(), fontFamily = poppinsFamily, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "$price".uppercase() + " $",modifier = Modifier.fillMaxWidth(), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.Medium,textAlign = TextAlign.Center, color = Color(255,165,0))
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}
