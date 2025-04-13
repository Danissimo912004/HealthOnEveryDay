import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthoneveryday.AdviceCard
import com.example.healthoneveryday.R

class AdviceAdapter(private val context: Context, private val adviceList: List<AdviceCard>) :
    RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {

    // Генерируем случайный порядок для карточек
    private val shuffledAdviceList = adviceList.shuffled()

    // Вложенный класс для ViewHolder
    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_advice_card, parent, false)
        return AdviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        val advice = shuffledAdviceList[position]

        // Отображаем день от 1 до 30
        holder.dayTextView.text = "День ${position + 1}"
        holder.titleTextView.text = advice.title
        holder.imageView.setImageResource(advice.imageResId)
        holder.descriptionTextView.text = advice.description
    }

    override fun getItemCount(): Int = shuffledAdviceList.size
}
