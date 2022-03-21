package com.itacademy.alertdialogapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itacademy.alertdialogapp.dataclass.Location
import com.itacademy.alertdialogapp.dictionaries.Constants
import com.itacademy.alertdialogapp.models.WeatherResponseModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val cities = arrayListOf<Location>(
        Location("Бишкек", 42.34523, 44.23432),
        Location("Талас",42.5300508,72.1879),
        Location("Баткен",31.5235,33.2323),


    )
    var location: Location? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        val cityName: TextView = findViewById(R.id.cityName)
        val temp: TextView = findViewById(R.id.temp)
        val spinner: Spinner = findViewById(R.id.spinner)
        val image: ImageView = findViewById(R.id.imageView)

        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, cities)



        button.setOnClickListener {
            location = spinner.selectedItem as Location
            App.service?.getWeather(location?.lat!!, location?.lon!!, Constants.API_ID)?.enqueue(object: Callback<WeatherResponseModel>{
                override fun onResponse(call: Call<WeatherResponseModel>, response: Response<WeatherResponseModel>) {
                    cityName.text = response.body()?.name
                    temp.text = response.body()?.main?.temp.toString()
                    setImageView(response.body()!!.weather[0].icon, image)
                }

                override fun onFailure(call: Call<WeatherResponseModel>, t: Throwable) {
                    Log.e("Error", t.localizedMessage)
                }
            })
        }
    }

    fun setImageView(image: String, view: ImageView){
        val url: String = "http://openweathermap.org/img/wn/"
        Picasso.get()
            .load("$url$image@3x.png")
            .into(view)

    }
}