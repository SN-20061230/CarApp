package uz.sardor.carapp.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import uz.sardor.carapp.Database.UserData


@Composable
fun SplashScreen(navController: NavController,) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        delay(1500)
        if (UserData.getUserSaved(context) == ""){
            navController.navigate("SignIn")
        }
        else{
            navController.navigate("Main")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://lottie.host/2efb0d40-0e69-4566-b718-400d5a88fcbb/IyXFFjxjPD.lottie"))
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}