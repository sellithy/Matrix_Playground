package util

open class PlussableList<T>(protected val elements : MutableList<T> = mutableListOf())
    : MutableList<T> by elements {

    open operator fun T.unaryPlus() = add(this)
}