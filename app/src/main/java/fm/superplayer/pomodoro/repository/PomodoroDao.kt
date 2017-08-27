package fm.superplayer.pomodoro.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import fm.superplayer.pomodoro.model.PomodoroHistory
import io.reactivex.Flowable

@Dao
interface PomodoroDao {

    @Insert
    fun insert(history: PomodoroHistory)

    @Query("select * from history order by date desc")
    fun queryAll(): Flowable<List<PomodoroHistory>>

    @Query("Delete from history")
    fun deleteAll()
}