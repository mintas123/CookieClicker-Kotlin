package pl.pjatk.s16604.proj1.recyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_upgrade_list_item.view.*
import pl.pjatk.s16604.proj1.R
import pl.pjatk.s16604.proj1.models.Upgrade

class UpgradeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Upgrade> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return UpgradeHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_upgrade_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is UpgradeHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(upgradeList: List<Upgrade>) {
        items = upgradeList
    }

//    var listener: ((item: Upgrade) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (item: Upgrade) -> Unit) {
//        this.listener = listener
//    }

    class UpgradeHolder
    constructor( itemView: View ) : RecyclerView.ViewHolder(itemView) {
        val upgradeIcon = itemView.upgade_icon
        val upgradeTitle = itemView.upgrade_title
        val upgradeAmount = itemView.upgrade_amount
        val upgradeTempo = itemView.upgrade_tempo
        val upgradeCost = itemView.upgrade_cost

//        init {
//           itemView.upgrade_add.setOnClickListener {
//               listener?.invoke(items[adapterPosition])
//           }
//        }


        fun bind(upgrade: Upgrade) {
            upgradeIcon.setImageResource(getImageId(itemView.context,upgrade.icon))
            upgradeTitle.text = upgrade.upgradeName
            upgradeAmount.text = upgrade.amount.toString()
            upgradeCost.text = upgrade.cost.toString()
            upgradeTempo.text = "${upgrade.calcIncome()}C/s"
        }
        private fun getImageId(context: Context, imageName: String): Int {
            return context.resources
                .getIdentifier("drawable/$imageName", null, context.packageName)
        }

    }


}