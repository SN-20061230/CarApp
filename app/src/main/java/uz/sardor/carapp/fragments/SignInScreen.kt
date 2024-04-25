package uz.sardor.carapp.fragments

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.sardor.carapp.Database.UserData
import uz.sardor.carapp.R
import uz.sardor.carapp.ui.theme.poppinsFamily
import uz.sardor.carapp.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController){
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val isUploading  = remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .background(primaryColor)
        .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if(isUploading.value){
                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp).height(60.dp),
                    color = primaryColor
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Login", fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(255,165,0))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Welcome to CarStore", fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold,   color = Color(168,175,185))
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = username,
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.padding(8.dp)) },
                onValueChange = { username = it },
                label = { Text("Username",    color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next,),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = primaryColor,)
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.padding(8.dp)) },
                onValueChange = { password = it },
                label = { Text("Password",   color = Color(168,175,185), fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primaryColor,
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon"
                        )
                    }
                },

                )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text="Forgot Password?",fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.Medium,   color = Color(168,175,185))
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (username.text =="" || password.text =="" ) {
                        isUploading.value = false
                        Toast.makeText(context,"Username or password empty" , Toast.LENGTH_SHORT)
                            .show()
                    }
                    else{
                        isUploading.value = true

                        UserData.UserGet(username, password) { result ->
                            if (result == "Successful Login") {
                                Toast.makeText(context,"Welcome, ${username.text} ", Toast.LENGTH_SHORT)
                                    .show()
                                UserData.UserSave(context, username.text)
                                navController.navigate("Main")
                            }
                            else{
                                isUploading.value = false
                                Toast.makeText(context, "Username or password is incorrect " , Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(255,165,0))
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    text = "Login",
                    fontFamily = poppinsFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            }
            Spacer(modifier = Modifier.height(30.dp))
            Row{
                Text(text="Don't have an account?", fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.Medium,   color = Color(168,175,185))
            }
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .height(50.dp)
                    .width(150.dp),
                onClick = { navController.navigate("OnBoarding")},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(255,165,0))
            ){
                Text(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp), text = "Sign Up", fontFamily = poppinsFamily, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
