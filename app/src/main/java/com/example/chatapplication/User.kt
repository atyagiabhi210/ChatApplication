package com.example.chatapplication
//normally we would have needed a data class to store the details of the user but as we are
//using the firebase we would require an empty constructor
class User {
    var name:String?=null
    var email:String?=null
    var uid:String?=null
    constructor(){}
    constructor(name:String?,email:String?,uid:String){
        this.name=name
        this.email=email
        this.uid=uid
    }
}