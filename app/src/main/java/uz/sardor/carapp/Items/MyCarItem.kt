package uz.sardor.carapp.Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uz.sardor.carapp.navigation.Screens
import uz.sardor.carapp.ui.theme.poppinsFamily

@Composable
fun MyCarItem(name: String, price:String, condition: String,description: String,imgUrl:String,year:String,mile:String,tg_username:String, phonenumber:String,navController: NavController){

    Card(modifier = Modifier.padding(10.dp).fillMaxWidth().height(200.dp)
        .clickable {
            navController.navigate(route = Screens.Details.getFullRoute(name = name, price = price,condition = condition,year = year,description = description, mile = mile, tg_username = tg_username, phone = phonenumber))
        }        .border(
            BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(12.dp),

            ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White),
    ){

        Column(modifier = Modifier.fillMaxWidth().height(200.dp)){
            Image(
                painter = rememberAsyncImagePainter(imgUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(140.dp),
                contentScale = ContentScale.Crop,
            )
            Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly,){
                Text(textAlign = TextAlign.Center,text = name.uppercase(), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,   color = Color(168,175,185))
                Text(textAlign = TextAlign.Center,text = price.uppercase() + " $", fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,   color = Color(168,175,185))

            }


        }

    }
}