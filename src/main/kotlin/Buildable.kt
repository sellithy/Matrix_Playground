
open class Buildable<T>(init: Buildable<T>.BuildableBuilder.() -> Unit){
    protected val list : List<T>

    init {
        list = BuildableBuilder().run{
            init()
            build()
        }
    }

    inner class BuildableBuilder{
        private val tempList = mutableListOf<T>()

        fun add(t : T){
            tempList += t
        }

        fun build() = tempList
    }
}