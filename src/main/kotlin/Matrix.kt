class Matrix private constructor(private val vectors: List<Vector>) : List<Vector> by vectors {
    constructor(init: Builder.() -> Unit) : this(Builder().apply(init))

    val numRows = vectors[0].size
    val numColumns = vectors.size

    class Builder : PlussableList<Vector>() {
        private fun isValid(element: Vector) = firstOrNull()?.let { element.size == it.size } ?: true

        override fun add(element: Vector): Boolean {
            require(isValid(element)) { "Invalid dimension for this vector" }
            return elements.add(element)
        }
    }

    override fun toString() =
        Array(numRows) { i ->
            Array(numColumns) { j ->
                vectors[j][i]
            }
        }.joinToString(separator = "\n") { it.joinToString(separator = "\t") }
}