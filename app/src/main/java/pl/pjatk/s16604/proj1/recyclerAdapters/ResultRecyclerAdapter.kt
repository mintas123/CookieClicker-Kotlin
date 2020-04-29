package pl.pjatk.s16604.proj1.recyclerAdapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_result_list_item.view.*
import pl.pjatk.s16604.proj1.R
import pl.pjatk.s16604.proj1.models.Result

class ResultRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Result> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ResultHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_result_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ResultHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(resultList: List<Result>) {
        items = resultList
    }



    class ResultHolder
    constructor( itemView: View ) : RecyclerView.ViewHolder(itemView) {
        val resultName = itemView.result_name
        val resultScore = itemView.result_score
        val resultDate = itemView.result_date

        fun bind(result: Result) {
            resultName.text = result.name
            resultScore.text = result.score.toString()
            resultDate.text = result.date.toString()
        }

    }


}