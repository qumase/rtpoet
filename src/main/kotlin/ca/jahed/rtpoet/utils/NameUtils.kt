import kotlin.random.Random

object NameUtils {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
    private val random = Random(0)

    fun randomString(length: Int): String {
        return (1..length)
            .map { random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}