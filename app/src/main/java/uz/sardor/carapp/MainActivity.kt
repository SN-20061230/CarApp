package uz.sardor.carapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.sardor.carapp.fragments.AddNewCarScreen
import uz.sardor.carapp.fragments.ChangePassword
import uz.sardor.carapp.fragments.DetailsScreen
import uz.sardor.carapp.fragments.MainScreen
import uz.sardor.carapp.fragments.OnboardingScreen
import uz.sardor.carapp.fragments.ProfileScreen
import uz.sardor.carapp.fragments.SeeAllScreen
import uz.sardor.carapp.fragments.SellingCarScreen
import uz.sardor.carapp.fragments.SignInScreen
import uz.sardor.carapp.fragments.SignUpScreen
import uz.sardor.carapp.fragments.SplashScreen
import uz.sardor.carapp.fragments.WishlistScreen
import uz.sardor.carapp.navigation.CONDITION_KEY
import uz.sardor.carapp.navigation.DESCRIPTION_KEY
import uz.sardor.carapp.navigation.MILE_KEY
import uz.sardor.carapp.navigation.NAME_KEY
import uz.sardor.carapp.navigation.PHONE_KEY
import uz.sardor.carapp.navigation.PRICE_KEY
import uz.sardor.carapp.navigation.Screens
import uz.sardor.carapp.navigation.TGUSERNAME_KEY
import uz.sardor.carapp.navigation.YEAR_KEY
import uz.sardor.carapp.ui.theme.CarAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screens.Splash.route){
                    composable(route = Screens.Splash.route) {
                        SplashScreen(navController = navController)
                    }
                    composable(route = Screens.OnBoarding.route) {
                        OnboardingScreen(navController = navController, this@MainActivity)
                    }
                    composable(route = Screens.SignIn.route){
                        SignInScreen(navController = navController)
                    }
                    composable(route = Screens.SignUp.route){
                        SignUpScreen(navController = navController)
                    }

                    composable(route = Screens.Main.route){
                        MainScreen(navController = navController)
                    }
                    composable(route = Screens.Details.route, arguments = listOf(
                        navArgument(NAME_KEY) {
                            type = NavType.StringType
                        },

                        navArgument(PRICE_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(CONDITION_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(DESCRIPTION_KEY) {
                            type = NavType.StringType
                        },

                        navArgument(YEAR_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(MILE_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(TGUSERNAME_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(PHONE_KEY) {
                            type = NavType.StringType
                        },
                    ),
                    ) { navBackStackEntry ->
                        val name = navBackStackEntry.arguments?.getString(NAME_KEY)
                        val price = navBackStackEntry.arguments?.getString(PRICE_KEY)
                        val condition = navBackStackEntry.arguments?.getString(CONDITION_KEY)
                        val description = navBackStackEntry.arguments?.getString(DESCRIPTION_KEY)
                        val year = navBackStackEntry.arguments?.getString(YEAR_KEY)
                        val mile = navBackStackEntry.arguments?.getString(MILE_KEY)
                        val tg_username = navBackStackEntry.arguments?.getString(TGUSERNAME_KEY)
                        val phone = navBackStackEntry.arguments?.getString(PHONE_KEY)
                        if (name != null && price!= null && condition!= null && description!= null && year!= null && mile!= null && tg_username!= null && phone!= null ) {
                            DetailsScreen(name = name,price= price, condition = condition,description = description,year = year, mile = mile, tg_username = tg_username, phone = phone, navController)
                        }
                    }
                    composable(route = Screens.Wishlist.route) {
                        WishlistScreen(navController = navController)
                    }
                    composable(route = Screens.Profile.route) {
                        ProfileScreen(navController = navController)
                    }
                    composable(route = Screens.Selling.route) {
                        SellingCarScreen(navController = navController)
                    }

                    composable(route = Screens.ChangePassword.route){
                        ChangePassword(navController = navController)
                    }
                    composable(route = Screens.NewCar.route){
                        AddNewCarScreen(navController = navController)
                    }

                    composable(route = Screens.SeeAllScreen.route){
                        SeeAllScreen(navController = navController)
                    }
                }

            }
        }
    }
}

