package pl.pjatk.s16604.proj1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.net.CookieHandler


const val COOKIES = "COOKIES"
const val PREFS_FILENAME = "GAME_STATE"
const val TIMER = "TIMER"
const val MAX_TIME = 1000*60*60*48


class MainActivity : AppCompatActivity() {


    var sharedPreferences: SharedPreferences? = null
    private var counter = 0
    private var timer = MAX_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        counter = sharedPreferences!!.getInt(COOKIES,0)
        timer = sharedPreferences!!.getInt(TIMER, MAX_TIME)

        cookieCounter.text = "Cookies: $counter"
        timeLeft.text= "Time: $timer"


        onCookieClick()
        onShopClick()
        timer()

    }



    private fun onCookieClick() {
        cookieClick.setOnClickListener{

            animate(this, cookieClick)
            counter++
            cookieCounter.text = "Cookies: $counter"
            val editor = sharedPreferences!!.edit()
            editor.putInt(COOKIES,counter)
            editor.apply()


        }
    }

    private fun onShopClick() {
        shopBtn.setOnClickListener{
            animate(this,shopBtn)
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

    private fun timer() {
        val timrrr = object: CountDownTimer(timer.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished/1000
                timeLeft.text = "Time: $seconds"
                val editor = sharedPreferences!!.edit()
                editor.putInt(TIMER, millisUntilFinished.toInt())
                editor.apply()

            }

            override fun onFinish() {
                timeLeft.text = "Time: END"
            }
        }
        timrrr.start()
    }





}
