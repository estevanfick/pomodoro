package fm.superplayer.pomodoro.ui.pomodoro.history

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fm.superplayer.pomodoro.R
import fm.superplayer.pomodoro.model.PomodoroHistory
import kotlinx.android.synthetic.main.list_item_pomodoro_date.view.*
import kotlinx.android.synthetic.main.list_item_pomodoro_history.view.*


class PomodoroHistoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<HistoryType> = emptyList()

    fun updateData(list: List<HistoryType>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)){
            1 -> {
                val dataHolder = holder as DateViewHolder
                val data = (list[position] as HistoryHeader).date
                dataHolder.bind(data)
            }
            else -> {
                val historyHolder = holder as HistoryViewHolder
                val history = (list[position] as HistoryData).history
                historyHolder.bind(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_pomodoro_date, parent, false)
                DateViewHolder(view)
            }
            else -> {
                val view =LayoutInflater.from(parent?.context).inflate(R.layout.list_item_pomodoro_history, parent, false)
                HistoryViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(day: String){
            itemView.txtDay.text = day
        }
    }

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(history: PomodoroHistory){
            itemView.txtTimeHistory.text = history.time
            itemView.txtStatus.text = if (history.status == 0) itemView.context.getString(R.string.status_stopped) else itemView.context.getString(R.string.status_finished)
            if (DateUtils.isToday(history.date)){
                itemView.txtDate.text = DateUtils.getRelativeTimeSpanString(history.date).toString()
            } else {
                itemView.txtDate.text = DateUtils.formatDateTime(itemView.context, history.date, DateUtils.FORMAT_SHOW_TIME).toString()
            }
        }
    }
}