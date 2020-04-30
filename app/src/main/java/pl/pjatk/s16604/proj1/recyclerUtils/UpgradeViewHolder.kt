package pl.pjatk.s16604.proj1.recyclerUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_upgrade_list_item.view.*
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.models.ProjMetadata
import pl.pjatk.s16604.proj1.models.Upgrade


class UpgradeViewHolder : RecyclerView.ViewHolder {

    private val upgradeIcon: ImageView
    private val upgradeTitle: TextView
    private val upgradeAmount: TextView
    private val upgradeTempo: TextView
    private val upgradeCost: TextView
    var mView: View

    constructor(itemView: View) : super(itemView) {
        upgradeIcon = itemView.upgade_icon
        upgradeTitle = itemView.upgrade_title
        upgradeAmount = itemView.upgrade_amount
        upgradeTempo = itemView.upgrade_tempo
        upgradeCost = itemView.upgrade_cost
        mView = itemView
    }

    fun bind(upgrade: Upgrade) {
        upgradeIcon.setImageResource(getImageId(itemView.context, upgrade.icon))
        upgradeTitle.text = upgrade.upgradeName
        upgradeAmount.text = upgrade.amount.toString()
        upgradeCost.text = upgrade.cost.toString()
        upgradeTempo.text = "${upgrade.calcIncome()}C/s"
    }

    class RecyclerAdapterMenu : RecyclerView.Adapter<UpgradeViewHolder> {

        private var upgradeList: MutableList<Upgrade> = ArrayList()
        private var myContext: Context


        constructor(context: Context) {
            this.myContext = context
            upgradeList = loadData(myContext).upgrades

        }

        private fun saveData(metadata: ProjMetadata) {
            STORAGE.saveData(metadata)
        }

        private fun loadData(context: Context): ProjMetadata {
            return STORAGE.loadData(context)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpgradeViewHolder {
            val inflater = LayoutInflater.from(parent.context) as LayoutInflater
            return UpgradeViewHolder(
                inflater.inflate(
                    R.layout.layout_upgrade_list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return upgradeList.size
        }

        override fun onBindViewHolder(recyclerViewHolder: UpgradeViewHolder, position: Int) {
            recyclerViewHolder.bind(upgradeList[position])

            recyclerViewHolder.mView.setOnClickListener {
                var metadata = STORAGE.loadData(myContext)
                val upgradeClicked = upgradeList[position]

                if (upgradeClicked.cost < metadata.cookies) {
                    animate(myContext,it)
                    upgradeClicked.addUpgrade()
                    metadata.cookies -= upgradeClicked.cost
                    metadata.perSecond += upgradeClicked.income
                    metadata.upgrades = upgradeList

                    saveData(metadata)
                    loadData(myContext)
                    this.notifyDataSetChanged()
                } else {
                    animateShakeBlocked(myContext,it)
                    Toast.makeText(it.context, "Need moar cookies", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }
}