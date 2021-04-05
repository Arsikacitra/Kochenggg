package com.example.tubes

class User {
    var id : Int = 0
    var username : String = ""
    var email : String = ""
    var password : String = ""
    var image : String = ""

    constructor(username: String, email: String, password: String, image: String){
        this.username = username
        this.email = email
        this.password = password
        this.image = image
    }

    constructor(){
    }
}