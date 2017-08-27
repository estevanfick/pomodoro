package fm.superplayer.pomodoro.ui.pomodoro.history

import fm.superplayer.pomodoro.model.PomodoroHistory

interface HistoryType {
    val type: Int
}

class HistoryHeader(val date: String): HistoryType {
    override val type = 1
}

class HistoryData(val history: PomodoroHistory): HistoryType {
    override val type = 2
}