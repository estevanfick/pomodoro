package fm.superplayer.pomodoro.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import fm.superplayer.pomodoro.model.PomodoroHistory

@Database(entities = arrayOf(PomodoroHistory::class), version = 1)
abstract class PomodoroDB: RoomDatabase(){

    abstract fun historyDao(): PomodoroDao
}