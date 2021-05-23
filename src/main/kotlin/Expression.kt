private operator fun Number.plus(other: Number): Number =
    if (this is Int && other is Int)
        this + other
    else
        this.toDouble() + other.toDouble()

sealed interface Expression {
    operator fun plus(exp: Expression): Expression
    override fun toString() : String
}

class Poly private constructor(private val expressions : List<Term>) : List<Term> by expressions, Expression{
    constructor(init : Builder.() -> Unit) : this(Builder().apply(init))

    constructor(expressions: Iterable<Expression>) : this ({
        expressions.forEach { exp ->
            when(exp){
                is Term -> +exp
                is Poly -> exp.forEach { +it }
            }
        }
    })

    constructor(vararg expressions: Expression) : this (expressions.toList())

    class Builder : PlussableList<Term>() {
        override fun add(element: Term) : Boolean =
            when (element) {
                is Combo -> elements
                    .indexOfFirst { it is Combo && it.letter == element.letter }
                    .let {
                        if (it == -1) return@let elements.add(element)
                        elements[it] = (elements[it] + element) as Combo
                        return@let true
                    }
                is JustANumber -> elements
                    .indexOfFirst { it is JustANumber }
                    .let {
                        if (it == -1) return@let elements.add(element)
                        elements[it] = (elements[it] + element) as JustANumber
                        return@let true
                    }
            }
    }

    override fun plus(exp: Expression) =
        Poly(this@Poly, exp)

    override fun toString() = expressions.joinToString(separator = " + ")
}

//region Term
sealed interface Term : Expression

class JustANumber(val number: Number) : Term {
    override fun toString() = number.toString()

    override fun plus(exp: Expression) =
        when (exp) {
            is JustANumber -> JustANumber(number + exp.number)
            is Poly -> exp + this
            is Combo -> exp + this
        }

}

class Combo(val number: Number, val letter: Char) : Term{
    constructor(pair: Pair<Number, Char>) : this(pair.first, pair.second)
    override fun toString() = "$number$letter"

    init {
        require(letter.isLetter()) {"Needs to be a letter"}
    }

    override fun plus(exp: Expression) =
        when (exp) {
            is Poly -> exp + this
            is JustANumber -> Poly(exp, this)
            is Combo ->
                if (letter == exp.letter)
                    Combo(number + exp.number, letter)
                else
                    Poly(exp, this)
        }
}
//endregion