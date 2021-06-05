sealed interface Expression {
    operator fun plus(exp: Expression): Expression
    operator fun times(exp: Expression): Expression
    override fun toString(): String
}

class Poly private constructor(private val expressions: List<Term>) : List<Term> by expressions, Expression {
    constructor(init: Builder.() -> Unit) : this(Builder().apply(init))

    constructor(expressions: Iterable<Expression>) : this({
        expressions.forEach { exp ->
            when (exp) {
                is Term -> +exp
                is Poly -> exp.forEach { +it }
            }
        }
    })

    constructor(vararg expressions: Expression) : this(expressions.toList())

    class Builder : PlussableList<Term>() {
        override fun add(element: Term): Boolean =
            when (element) {
                is Combo -> elements
                    .indexOfFirst { it is Combo && it.letters == element.letters }
                    .let {
                        if (it == -1) return@let elements.add(element)
                        elements[it] = (elements[it] + element) as Combo
                        if((elements[it] as Combo).coefficient == 0) elements.removeAt(it)
                        return@let true
                    }
                is JustANumber -> elements
                    .indexOfFirst { it is JustANumber }
                    .let {
                        if (it == -1) return@let elements.add(element)
                        elements[it] = (elements[it] + element) as JustANumber
                        if((elements[it] as JustANumber).number == 0) elements.removeAt(it)
                        return@let true
                    }
            }
    }

    override fun plus(exp: Expression) =
        Poly(this@Poly, exp)

    override fun times(exp: Expression) =
        when (exp) {
            is Poly -> Poly {
                for (t1 in expressions)
                    for (t2 in exp.expressions)
                        +(t1 * t2)
            }
            is Term -> Poly(expressions.map { (it * exp) })
        }

    override fun toString() = expressions.joinToString(separator = " + ")
}