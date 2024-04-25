package uz.sardor.carapp.Database

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.app.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
//import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class UserData { companion object {
    private val users = FirebaseDatabase.getInstance().reference.child("users")
    fun UserCreate(user: UserClass) {
        user.username?.let { username ->
            users.child(username).setValue(user)
        }
    }
    fun getUserSaved(context: Context): String {
        val preferences = context.getSharedPreferences("db", Context.MODE_PRIVATE)
        return preferences.getString("user", "") ?: ""
    }
    fun UserSave(context: Context, user: String) {
        val preferences = context.getSharedPreferences("db", Context.MODE_PRIVATE)
        preferences.edit().putString("user", user).apply()
    }
    fun Usercheck(user: String, callback: (Boolean) -> Unit) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                callback(!dataSnapshot.exists())
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(false)
            }
        })
    }
    fun UserGet(user: TextFieldValue, password: TextFieldValue, callback: (String) -> Unit) {
        val userText = user.text
        val passwordText = password.text

        users.child(userText).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val retrievedUser = dataSnapshot.getValue(UserClass::class.java)
                val isValidUser = retrievedUser?.password == passwordText

                if (dataSnapshot.exists() && isValidUser) {
                    callback("Successful Login")
                } else {
                    callback("Incorrect Username or Password")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback("Error")
            }
        })
    }



    fun FavouritesCreate(user: String, name:String) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val retrievedUser = dataSnapshot.getValue(UserClass::class.java)

                if (retrievedUser != null) {
                    retrievedUser.favourites = retrievedUser.favourites.plus(name)
                }
                users.child(user).setValue(retrievedUser)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }


    fun FavouritesDelete(user: String, name:String) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val retrievedUser = dataSnapshot.getValue(UserClass::class.java)

                if (retrievedUser != null) {
                    retrievedUser.favourites = retrievedUser.favourites.minus(name)
                }
                users.child(user).setValue(retrievedUser)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun FavouriteGet(user: String, callback: (List<String>) -> Unit) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val retrievedUser = dataSnapshot.getValue(UserClass::class.java)
                if (retrievedUser != null) {
                    callback(retrievedUser.favourites)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    fun isFavourite(user: String, model:String, callback: (Boolean) -> Unit) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val retrievedUser = dataSnapshot.getValue(UserClass::class.java)
                val favourites = retrievedUser!!.favourites
                var flag = false
                if (dataSnapshot.exists()) {
                    for (item in favourites) {
                        if (item == model) {
                            flag = true
                            callback(true)
                        }
                    }
                    if (!flag) callback(false)
                } else {
                    callback(false)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(false)
            }
        }
        )
    }
    fun ChangePassword(user: String, password: String, oldPassword:String,callback: (String) -> Unit) {
        users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val changedUser = dataSnapshot.getValue(UserClass::class.java)
                if(oldPassword == changedUser!!.password){
                    changedUser?.let {
                        it.password = password
                        users.child(user).setValue(it)
                        callback("Changed Successfully")
                    }

                }
                else{
                    callback("Not Changed")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback("Not Changed Success")

            }
        })
    }
    fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback :(Boolean, String) -> Unit){


        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${bitmap}")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()



        imageRef.putBytes(imageData).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri->
                val imageUrl = uri.toString()
                callback(true, imageUrl)
            }.addOnFailureListener{
                callback(false, null.toString())
            }
        }.addOnFailureListener{
            callback(false, null.toString())
        }
    }

}
}
