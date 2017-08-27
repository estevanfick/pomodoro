package fm.superplayer.pomodoro.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "history")
data class PomodoroHistory(
        var time: String = "",
        var date: Long = 0,
        var status: Int = 0,
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0 )