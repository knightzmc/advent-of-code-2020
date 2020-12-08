sealed class Tree<T>()

data class Leaf<T>(val value: T) : Tree<T>()
data class Branch<T>(val left: Tree<T>, val right: Tree<T>) : Tree<T>()

fun <T> Tree<T>.walk(callback: (T) -> Unit) {
    if (this is Leaf<T>) {
        return callback(value)
    } else if (this is Branch<T>) {
        left.walk(callback)
        right.walk(callback)
    }
}
