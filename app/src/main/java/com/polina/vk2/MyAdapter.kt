import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polina.vk2.R

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    val items = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(list: MutableList<String>) {
        val previousSize = items.size
        items.clear()
        items.addAll(list)
        notifyItemRangeInserted(previousSize, list.size)
    }

    fun addItems(item: String) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}