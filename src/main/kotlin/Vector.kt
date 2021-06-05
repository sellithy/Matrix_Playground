// Column Vector
class Vector private constructor(private val expressions: List<Expression>) : List<Expression> by expressions {
    constructor(init: PlussableList<Expression>.() -> Unit)
            : this(PlussableList<Expression>().apply(init))

    infix fun dot(other: Vector) =
        zip(other) { a, b -> a * b }
            .reduce { acc, exp -> acc + exp }


    operator fun plus(other: Vector) =
        Vector { expressions.zip(other) { a, b -> add(a + b) } }

    override fun toString() = expressions.joinToString(prefix = "[", postfix = "]")
}

operator fun JustANumber.times(vec: Vector) =
    Vector{
        vec.map { +(this@times * it) }
    }

operator fun Number.times(vec: Vector) = JustANumber(this) * vec