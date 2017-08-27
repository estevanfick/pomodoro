package fm.superplayer.pomodoro.ui.pomodoro.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fm.superplayer.pomodoro.PomodoroApp
import fm.superplayer.pomodoro.R
import fm.superplayer.pomodoro.model.PomodoroHistory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pomodoro_history.*

class PomodoroHistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pomodoro_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerPomodoroHistory.layoutManager = LinearLayoutManager(activity)
        val adapter = PomodoroHistoryAdapter()
        recyclerPomodoroHistory.adapter = adapter

        PomodoroApp.database?.historyDao()?.queryAll()
                ?.map { createHistoryTypeList(it) }
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    adapter.updateData(it)
                })
    }

    private fun createHistoryTypeList(history: List<PomodoroHistory>): List<HistoryType> {
        val list = mutableListOf<HistoryType>()
        var oldDate = ""
        history.forEach {
            val actualDate = DateUtils.getRelativeTimeSpanString(it.date, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString()
            if (oldDate != actualDate) {
                list.add(HistoryHeader(actualDate))
                oldDate = DateUtils.getRelativeTimeSpanString(it.date, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString()
            }
            list.add(HistoryData(it))
        }
        return list
    }


    companion object {
        fun newInstance(): PomodoroHistoryFragment {
            val fragment = PomodoroHistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
