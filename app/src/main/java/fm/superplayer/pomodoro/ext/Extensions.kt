package fm.superplayer.pomodoro.ext

fun Long.toTimeString(): String {
    val seconds: Long = (this / 1000) % 60
    val minutes: Long = (this / (1000 * 60)) % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}