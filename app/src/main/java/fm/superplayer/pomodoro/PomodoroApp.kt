package fm.superplayer.pomodoro

import android.app.Application
import android.arch.persistence.room.Room
import fm.superplayer.pomodoro.repository.PomodoroDB

class PomodoroApp: Application() {

    companion object {
        var database: PomodoroDB? = null
    }

    override fun onCreate() {
        super.onCreate()
        PomodoroApp.database =  Room.databaseBuilder(this, PomodoroDB::class.java, "pomodorodb").build()
    }
}