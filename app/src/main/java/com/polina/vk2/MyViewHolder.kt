import com.polina.vk2.R

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val loadedImage: ImageView = view.findViewById(R.id.loadedImage)
    fun bind(url: String){
        Glide.with(itemView)
            .load(url)
            .into(loadedImage)
    }
}