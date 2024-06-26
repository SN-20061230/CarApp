package uz.sardor.carapp.fragments

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.sardor.carapp.Database.CarClass
import uz.sardor.carapp.Database.CarData
import uz.sardor.carapp.Database.UserData
import uz.sardor.carapp.Items.Item
import uz.sardor.carapp.Items.RecommendItem
import uz.sardor.carapp.effects.AnimatedShimmer
import uz.sardor.carapp.effects.AnimatedShimmer1
import uz.sardor.carapp.firebaseUI
import uz.sardor.carapp.navigation.NavigationItem
import uz.sardor.carapp.navigation.Screens
import uz.sardor.carapp.ui.theme.poppinsFamily
import uz.sardor.carapp.ui.theme.primaryColor
import uz.sardor.carapp.ui.theme.secondaryColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current

    var cars by remember {
        mutableStateOf<List<CarClass>>(emptyList())
    }

    CarData.GetCars { list ->
        cars = list
    }

    val carslength1: Int = cars.size

    var loading by remember { mutableStateOf(true) }

    val items = listOf(
        NavigationItem(
            title = "Main",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,),
        NavigationItem(
            title = "WishList",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,),
        NavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 50.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            firebaseUI(LocalContext.current)
                        }

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Welcome to CarStore",
                            fontFamily = poppinsFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(168, 175, 185)
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = UserData.getUserSaved(context),
                            fontFamily = poppinsFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryColor
                        )

                    }
                    Spacer(modifier = Modifier.height(50.dp))


                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    fontFamily = poppinsFamily,
                                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {

                                selectedItemIndex = index
                                navController.navigate(item.title)
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                                .clickable {
                                },
                        )
                    }
                    Button(modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        onClick = {
                            UserData.UserSave(context, "")
                            navController.navigate("SignIn")
                        }) {
                        Text(
                            text = "Log out", fontSize = 20.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Hello, ",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = UserData.getUserSaved(context),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = primaryColor
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .clip(shape = RoundedCornerShape(25))
                                .background(Color.Black),
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .height(35.dp)
                                        .width(40.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                navController.navigate(Screens.SeeAllScreen.route)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        },
                    )
                },
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .zIndex(3f)
                            .padding(
                                bottom = 20.dp,
                                start = 25.dp, end = 25.dp, top = 20.dp
                            )
                            .clip(RoundedCornerShape(25.dp))
                            .border(
                                BorderStroke(1.dp, Color.LightGray),
                                shape = RoundedCornerShape(25.dp),
                            ),
                        containerColor = secondaryColor
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.title)
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (item.badgeCount != null) {
                                                Badge {
                                                    Text(text = item.badgeCount.toString())
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title,
                                            modifier = Modifier.size(35.dp),
                                            tint = primaryColor
                                        )
                                    }
                                }
                            )
                        }
                    }
                },
            )
            {}

            LaunchedEffect(
                key1 = true,
                block = {
                    delay(1000)
                    loading = false
                }
            )

            Row(modifier = Modifier.fillMaxWidth().padding(top = 100.dp)) {
                if (loading) {
                    LazyRow() {
                        items(carslength1) {
                            AnimatedShimmer1()
                        }
                    }
                } else {
                    LazyRow() {
                        items(cars) { item ->
                            item.title?.let {
                                item.price?.let { it1 ->
                                    item.condition?.let { it2 ->
                                        item.description?.let { it3 ->
                                            item.imageUrl.let { it4 ->
                                                item.year?.let { it5 ->
                                                    item.mileage?.let { it6 ->
                                                        item.userTelegram?.let { it7 ->
                                                            item.phonenumber?.let { it8 ->
                                                                Item(
                                                                    name = it,
                                                                    price = it1,
                                                                    condition = it2,
                                                                    description = it3,
                                                                    imgUrl = it4,
                                                                    year = it5,
                                                                    mile = it6,
                                                                    tg_username = it7,
                                                                    phonenumber = it8,
                                                                    navController
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            Column(modifier = Modifier) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 280.dp, start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Recommended",
                        fontFamily = poppinsFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "See all",
                        fontFamily = poppinsFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(168, 175, 185),
                        modifier = Modifier.clickable {
                            navController.navigate(Screens.SeeAllScreen.route)
                        }
                    )
                }

                if (loading) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(bottom = 110.dp, start = 10.dp, end = 10.dp)
                    )
                    {
                        items(carslength1) {
                            AnimatedShimmer()
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(bottom = 110.dp, start = 10.dp, end = 10.dp)
                    ) {
                        items(items = cars) { item ->
                            item.title?.let {
                                item.price?.let { it1 ->
                                    item.condition?.let { it2 ->
                                        item.description?.let { it3 ->
                                            item.imageUrl?.let { it4 ->
                                                item.year?.let { it5 ->
                                                    item.mileage?.let { it6 ->
                                                        item.userTelegram?.let { it7 ->
                                                            item.phonenumber?.let { it8 ->
                                                                RecommendItem(
                                                                    name = it,
                                                                    price = it1,
                                                                    condition = it2,
                                                                    description = it3,
                                                                    imgUrl = it4,
                                                                    year = it5,
                                                                    mile = it6,
                                                                    tg_username = it7,
                                                                    phonenumber = it8,
                                                                    navController
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
