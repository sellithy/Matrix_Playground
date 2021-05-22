
abstract class Composable<T>(protected val elements: List<T>) : List<T> by elements{

    constructor(init: ComposableBuilder<T>.() -> Unit,
                builder : ComposableBuilder<T> = ComposableBuilder()) : this(
        builder.run {
            init()
            build()
        })

    open class ComposableBuilder<T> {
        protected val tempElements = mutableListOf<T>()

        open fun verifyInput(t : T) = true

        open operator fun T.unaryPlus() {
            require(verifyInput(this)) { "Invalid input" }
            tempElements += this
        }

        fun build() = tempElements
    }
}