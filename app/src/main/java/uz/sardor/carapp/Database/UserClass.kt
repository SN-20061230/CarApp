package uz.sardor.carapp.Database

data class UserClass(
    val fullname: String?,
    var username: String?,
    var password: String?,
    var favourites: List<String>,
    var imageUrl:String,

    ) {
    constructor() : this(null, null, null, emptyList(), null.toString())
}
