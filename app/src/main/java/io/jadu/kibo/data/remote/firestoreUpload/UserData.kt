package io.jadu.kibo.data.remote.firestoreUpload

data class UserData(
    val sha256Code:String,
    val email:String,
    val selectedTree:String,
    val location:String,
    val imageUrl:String,
    val points:Int,
    val lastUpdateDate:String,
){
    constructor():this("","","","","",0,"")
}
