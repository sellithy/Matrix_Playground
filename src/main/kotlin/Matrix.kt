// Column Vector
class Vector(private val expressions : List<Expression>) : List<Expression> by expressions {
    constructor(init: PlussableList<Expression>.() -> Unit)
            : this(PlussableList<Expression>().apply(init))

    override fun toString() = expressions.joinToString()
}

class Matrix(private val vectors : List<Vector>) : List<Vector> by vectors {
    constructor(init: Builder.() -> Unit) : this(Builder().apply(init))

    init {
        require(vectors.all { it.size == vectors.first().size }) {"All vectors need to be the same length"}
    }

    class Builder : PlussableList<Vector>() {
        private fun isValid(element: Vector) = firstOrNull()?.let {element.size == it.size} ?: true

        override fun add(element: Vector): Boolean {
            require(isValid(element)) { "Invalid dimension for this vector"}
            return elements.add(element)
        }
    }

    override fun toString(): String {
        return Array(vectors[0].size) { i ->
            Array(vectors.size) { j ->
                vectors[j][i]
            }
        }.joinToString(separator = "\n") { it.joinToString(separator = "\t") }
    }
}