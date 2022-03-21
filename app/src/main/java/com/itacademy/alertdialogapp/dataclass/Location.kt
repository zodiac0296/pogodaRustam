package com.itacademy.alertdialogapp.dataclass

class Location (
    var cityName: String,
    var lat: Double,
    var lon: Double
    ){
    override fun toString(): String {
        return cityName
    }
}