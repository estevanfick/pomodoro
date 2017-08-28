package fm.superplayer.pomodoro.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "history")
data class PomodoroHistory(
        var time: Long,
        var date: Long,
        var status: Int,
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0 )