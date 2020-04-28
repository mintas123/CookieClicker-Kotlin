package pl.pjatk.s16604.proj1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_shop.*
import java.lang.reflect.Type

class ShopActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private lateinit var upgradeAdapter: UpgradeRecyclerAdapter

    //initial default values in case of lack of SP
    private var cookies = 0L
    private var timer = MAX_TIME
    private var upgrades = initUpgrades()
    private var perSecond = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        initRecyclerView()
        loadData()

        onHomeClick()

    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ShopActivity)
            upgradeAdapter = UpgradeRecyclerAdapter()
            adapter = upgradeAdapter
        }
    }

    override fun onStart() {
        loadData()
        super.onStart()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }


    private fun onHomeClick(){
        startBtn.setOnClickListener{
            animate(this, startBtn)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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
        upgradeAdapter.submitList(upgrades)

    }
}
