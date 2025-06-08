import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.innovantapp.R
import com.example.innovantapp.data.model.ColorImage
import androidx.core.content.ContextCompat


class ColorAdapter(
    private var items: MutableList<ColorImage>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition = -1
    private val colorMap = mapOf(
        "Blended Grey" to "#A9A9A9",
        "Russet" to "#80461B",
        "Olivia" to "#708238",
        "Mistic" to "#B4C7C1",
        "Honey" to "#FFC30B",
        "Brownish" to "#A52A2A",
        "Coffee" to "#6F4E37",
        "Cloudio" to "#D3D3D3",
        "Caramello" to "#C68E17",
        "Greyish" to "#BEBEBE"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color_circle, parent, false)
        return ColorViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val item = items[position]
        val colorHex = colorMap[item.name] ?: "#FFFFFF"

        holder.bind(item.imageUrl, colorHex, position == selectedPosition)

        holder.itemView.setOnClickListener {
            onClick(position)
            setSelectedPosition(position)
        }
    }

    fun updateList(newItems: List<ColorImage>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun setSelectedPosition(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        if (previous != -1) notifyItemChanged(previous)
        notifyItemChanged(position)
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.colorCircle)

        fun bind(imageUrl: String?, fallbackColorHex: String, isSelected: Boolean) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.hourglass_empty)
                    .error(R.drawable.broken_image)
                    .circleCrop()
                    .into(imageView)
            } else {
                imageView.setImageDrawable(null)
                try {
                    imageView.setBackgroundColor(Color.parseColor(fallbackColorHex))
                } catch (e: Exception) {
                    imageView.setBackgroundColor(Color.WHITE)
                }
            }

            val backgroundRes = if (isSelected) R.drawable.selected_border else R.drawable.unselected_border
            (itemView as FrameLayout).background = ContextCompat.getDrawable(itemView.context, backgroundRes)
        }
    }
}



