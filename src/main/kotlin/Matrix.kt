// Column Vector
class Vector(private val elements: List<Expression>) : List<Expression> by elements {

    constructor(init: VectorBuilder.() -> Unit) : this(
        VectorBuilder().run {
            init()
            build()
        })

    class VectorBuilder {
        private val tempElements = mutableListOf<Expression>()

        operator fun Expression.unaryPlus() {
            tempElements += this
        }

        fun build() = tempElements
    }

    override fun toString() = elements.toString()
}

class Matrix(private val vectors: List<Vector>) : List<Vector> by vectors {

    constructor(init: MatrixBuilder.() -> Unit) : this(
        MatrixBuilder().run {
            init()
            build()
        })

    class MatrixBuilder {
        private val tempVectors = mutableListOf<Vector>()

        operator fun Vector.unaryPlus() {
            tempVectors.firstOrNull()?.let {
                require(this.size == it.size) { "Incompatible Vector sizes" }
            }
            tempVectors += this
        }

        fun build() = tempVectors
    }

    override fun toString(): String {
        return Array(vectors[0].size) { i ->
            Array(vectors.size) { j ->
                vectors[j][i]
            }
        }.joinToString(separator = "\n") { it.joinToString(separator = "\t") }
    }
}