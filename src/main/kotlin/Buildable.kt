
abstract class Buildable<T>(protected val elements: List<T>) : List<T> by elements{

    constructor(init: BuildableBuilder<T>.() -> Unit,
                builder : BuildableBuilder<T> = BuildableBuilder()) : this(
        builder.run {
            init()
            build()
        })

    open class BuildableBuilder<T> {
        protected val tempElements = mutableListOf<T>()

        open fun verifyInput(t : T) = true

        open operator fun T.unaryPlus() {
            require(verifyInput(this)) { "Invalid input" }
            tempElements += this
        }

        fun build() = tempElements
    }
}