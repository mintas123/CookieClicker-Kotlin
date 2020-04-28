package pl.pjatk.s16604.proj1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import kotlin.random.Random


const val COOKIES = "COOKIES"
const val PREFS_FILENAME = "TEST"
const val TIMER = "TIMER"
const val UPGRADES = "UPGRADES"
const val TEMPO = "TEMPO"
const val MAX_TIME = 1000 * 60 * 60 * 12L


class MainActivity : AppCompatActivity() {


    private var sharedPreferences: SharedPreferences? = null

    //initial default values in case of lack of SP
    private var cookies = 0L
    private var timer = MAX_TIME
    private var upgrades = initUpgrades()
    private var perSecond = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        onCookieClick()
        onShopClick()
        onUpgradeClick()
        onScoreBoardClick()
        timer()
    }

    override fun onStart() {
        loadData()
        super.onStart()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }

    private fun onCookieClick() {
        cookieClick.setOnClickListener {
            animate(this, cookieClick)
            val cursor = upgrades[0]
            if (cursor.amount > 0) {
                cookies += cursor.amount

            } else {
                cookies++

            }
            cookieCounter.text = "Cookies: $cookies"
            saveData()
        }
    }

    private fun onShopClick() {
        shopBtn.setOnClickListener {
            animate(this, shopBtn)
            saveData()
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onScoreBoardClick() {
        scoreBoard.setOnClickListener {
            animate(this, scoreBoard)
            saveData()
            val intent = Intent(this, ScoreboardActivity::class.java)
            startActivity(intent)
        }
    }

    //for reference in shopActivity
    private fun onUpgradeClick() {
        var upgrade = upgrades[1]
        grannyBtn.setOnClickListener {
            if (cookies > upgrade.cost.toInt()) {
                upgrade.addUpgrade()
                cookies -= upgrade.cost.toInt()
                perSecond += upgrade.income
                tempo.text = "Tempo: $perSecond  C/s"
                grannyBtn.text = "+ (${upgrade.amount})"
            }
            upgrades[1] = upgrade
            saveData()
        }
    }

    private fun goToFinish() {
        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)
    }


    private fun timer() {
        val countdownTimer = object : CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                perSecond = calcIncome()
                if (perSecond > 0) {
                    cookieCounter.text = "Cookies: $cookies"
                    tempo.text = "Tempo: $perSecond C/s"
                    cookies += perSecond
                }

                var seconds = millisUntilFinished / 1000
                timeLeft.text = "Time: ${timeFormatter(seconds)}"
                timer = millisUntilFinished

                val chance = Random.nextInt(0, 10000)
                if (chance < 10) {
                    showMonster()
                }
            }

            override fun onFinish() {
                timeLeft.text = "Time: END"
                goToFinish()
            }
        }
        countdownTimer.start()
    }

    private fun calcIncome(): Long {
        var sum = 0L
        upgrades.forEach {
            if (it.amount > 0) {
                sum += it.income * it.amount
            }
        }
        return sum
    }

    private fun saveData() {
        val editor = sharedPreferences!!.edit()
        val gson = Gson()
        var jsonUpgrades = gson.toJson(upgrades)
        editor.putLong(COOKIES, cookies)
        editor.putLong(TEMPO, perSecond)
        editor.putString(UPGRADES, jsonUpgrades)
        editor.putLong(TIMER, timer)
        editor.apply()
    }

    private fun loadData() {
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        cookies = sharedPreferences!!.getLong(COOKIES, 0L)
        timer = sharedPreferences!!.getLong(TIMER, MAX_TIME)
        perSecond = sharedPreferences!!.getLong(TEMPO, 0L)


        val gson = Gson()
        var jsonUpgrades = sharedPreferences!!.getString(UPGRADES, gson.toJson(initUpgrades()))
        val upgradeListType: Type = object : TypeToken<ArrayList<Upgrade?>?>() {}.type
        upgrades = gson.fromJson(jsonUpgrades, upgradeListType)

        //set loaded data
        tempo.text = "Tempo: ${calcIncome()}  C/s"
        cookieCounter.text = "Cookies: $cookies"
        timeLeft.text = "Time: $timer}"
        tempo.text = "Tempo: $perSecond"
    }

    private fun showMonster() {
        val punishment = Random.nextDouble(0.1 * cookies, 0.5 * cookies)


        val countdownTimer = object : CountDownTimer(3000L, 1000L) {
            override fun onFinish() {
                randomMonster.visibility = View.GONE
                cookieClick.bringToFront()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
        randomMonster.bringToFront()
        randomMonster.visibility = View.VISIBLE
        countdownTimer.start()
        animateShake(this, randomMonster)

        randomMonster.setOnClickListener {
            animate(this, randomMonster)
            if (cookies > punishment) {
                cookies -= punishment.toLong()
            } else {
                cookies = 0L
            }
            randomMonster.visibility = View.GONE
            cookieClick.bringToFront()

        }
        saveData()
    }


}
