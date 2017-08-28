package fm.superplayer.pomodoro.ui.pomodoro.create


import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fm.superplayer.pomodoro.PomodoroApp
import fm.superplayer.pomodoro.R
import fm.superplayer.pomodoro.ext.toTimeString
import fm.superplayer.pomodoro.model.PomodoroHistory
import fm.superplayer.pomodoro.ui.pomodoro.PomodoroActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pomodoro_create.*
import java.util.*


class PomodoroCreateFragment : Fragment() {

    private var running = false
    private var timer: CountDownTimer? = null
    var currentTime: Long = 0
    val startTimer: Long = 1500000

    companion object {
        fun newInstance(): PomodoroCreateFragment {
            val fragment = PomodoroCreateFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pomodoro_create, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startPomodoro.setOnClickListener {
            if (running) {
                stopTimer()
            } else {
                startTimer()
            }
        }
    }

    private fun stopTimer(){
        timer?.cancel()
        saveHistory(currentTime, 0)
    }

    private fun startTimer(){
        running = true
        startPomodoro.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_stop_24dp))
        textTimerPomodoro.setTextColor(ContextCompat.getColor(activity, R.color.textStart))
        timer =  object : CountDownTimer(startTimer, 1000) {

            override fun onTick(millis: Long) {
                currentTime = startTimer - millis
                textTimerPomodoro?.text = millis.toTimeString()
            }

            override fun onFinish() {
                showNotification()
                saveHistory(startTimer, 1)
            }
        }.start()
    }

    private fun finish(){
        running = false
        startPomodoro.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_play_arrow_24dp))
        textTimerPomodoro.run {
            setTextColor(ContextCompat.getColor(activity, R.color.textStop))
            text = startTimer.toTimeString()
        }
    }

    private fun saveHistory(time: Long, status: Int){
        finish()
        val date = Calendar.getInstance().timeInMillis
        val history = PomodoroHistory(time, date, status)
        Single.fromCallable {
            PomodoroApp.database?.historyDao()?.insert(history) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ _ ->
                    (activity as PomodoroActivity).goToHistory()
                }
    }

    private fun showNotification(){
        val notification = NotificationCompat.Builder(activity, NotificationCompat.CATEGORY_MESSAGE)
        notification.run {
            setSmallIcon(R.drawable.ic_time_24dp)
            setContentTitle(resources.getString(R.string.app_name))
            setContentText(resources.getString(R.string.notification_text))
        }

        val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification.build())
    }
}