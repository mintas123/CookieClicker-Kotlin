package pl.pjatk.s16604.proj1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_upgrade_list_item.view.*

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

    class UpgradeHolder
    constructor( itemView: View ) : RecyclerView.ViewHolder(itemView) {
        val upgradeIcon = itemView.upgade_icon
        val upgradeTitle = itemView.upgrade_title
        val upgradeAmount = itemView.upgrade_amount

        fun bind(upgrade: Upgrade) {
            upgradeIcon.setImageResource(getImageId(itemView.context,upgrade.icon))
            upgradeTitle.text = upgrade.upgradeName
            upgradeAmount.text = upgrade.amount.toString()
        }
        private fun getImageId(context: Context, imageName: String): Int {
            return context.resources
                .getIdentifier("drawable/$imageName", null, context.packageName)
        }

    }


}