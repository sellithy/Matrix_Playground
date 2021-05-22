// Column Vector
class Vector(init: ComposableBuilder<Expression>.() -> Unit) :
    Composable<Expression>(init) {

    override fun toString() = elements.toString()
}

class Matrix(init: ComposableBuilder<Vector>.() -> Unit) :
    Composable<Vector>(init, MatrixBuilder()) {

    class MatrixBuilder : ComposableBuilder<Vector>() {
        override fun verifyInput(t: Vector): Boolean =
            tempElements.firstOrNull()?.let {
                t.size == it.size
            } ?: true
    }

    override fun toString(): String {
        return Array(elements[0].size) { i ->
            Array(elements.size) { j ->
                elements[j][i]
            }
        }.joinToString(separator = "\n") { it.joinToString(separator = "\t") }
    }
}