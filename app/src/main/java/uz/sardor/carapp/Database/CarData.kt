package uz.sardor.carapp.Database

import android.graphics.Bitmap
import androidx.core.app.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class CarData {
    companion object {
        val cars = FirebaseDatabase.getInstance().reference.child("cars")
        fun CreateCar(car: CarClass) {
            car.title?.let { title ->
                cars.child(title).setValue(car)
            }
        }
        fun GetCars(callback: (List<CarClass>) -> Unit) {
            cars.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val cars = mutableListOf<CarClass>()
                    dataSnapshot.children.forEach {
                        val car = it.getValue(CarClass::class.java)
                        if (car != null) {
                            cars.add(car)
                        }
                    }
                    callback(cars)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }
        fun GetCarImage(name: String, callback: (String) -> Unit) {
            cars.child(name).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback(dataSnapshot.getValue(CarClass::class.java)!!.imageUrl)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    callback("")
                }
            })
        }

        fun FavouritesFilter(lst: List<String>, callback: (List<CarClass>) -> Unit) {
            cars.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val fltr = mutableListOf<CarClass>()
                    dataSnapshot.children.forEach {
                        val car = it.getValue(CarClass::class.java)
                        if (car != null && car.title in lst) {
                            fltr.add(car)
                        }
                    }
                    callback(fltr)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }
        fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback :(Boolean, String) -> Unit){
            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("carimages/${bitmap}")
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