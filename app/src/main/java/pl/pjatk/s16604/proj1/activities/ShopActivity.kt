package pl.pjatk.s16604.proj1.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_shop.*
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.models.Upgrade
import pl.pjatk.s16604.proj1.recyclerAdapters.UpgradeRecyclerAdapter
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

        onAddClick()
        onHomeClick()

    }


    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ShopActivity)
            val topSpacingDecorator =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
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

//    private fun onAddClick(){
//
//        upgradeAdapter.setOnItemClickListener {
//            if (cookies > it.cost.toInt()) {
//                it.addUpgrade()
//                cookies -= it.cost.toInt()
//                perSecond += it.income
//                animate(this,)
//                tempo.text = "Tempo: $perSecond  C/s"
//                grannyBtn.text = "+ (${upgrade.amount})"
//            }
//            upgrades[1] = upgrade
//            saveData()
//        }
//    }


    private fun onHomeClick(){
        startBtn.setOnClickListener{
            animate(this, startBtn)
            finish()
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
