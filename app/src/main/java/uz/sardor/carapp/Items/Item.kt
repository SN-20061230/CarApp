package uz.sardor.carapp.Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uz.sardor.carapp.navigation.Screens

@Composable
fun Item(name: String, price:String, condition: String,description: String, imgUrl:String, year:String, mile:String, tg_username:String, phonenumber:String,navController: NavController){
    Card(modifier = Modifier.padding(5.dp).width(150.dp).height(150.dp)
        .clickable {
            navController.navigate(route = Screens.Details.getFullRoute(name = name, price = price,condition = condition,description = description, year = year, mile = mile, tg_username = tg_username, phone = phonenumber))
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,),
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Image(
                painter = rememberAsyncImagePainter(imgUrl),
                contentDescription = "gfg image",
                modifier = Modifier
                    .size(150.dp)
                    .border(
                        BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(12.dp),),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
