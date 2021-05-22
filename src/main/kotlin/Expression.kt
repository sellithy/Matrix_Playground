private operator fun Number.plus(other: Number): Number =
    if (this is Int && other is Int)
        this + other
    else
        this.toDouble() + other.toDouble()

sealed interface Expression {
    operator fun plus(exp: Expression): Expression
    override fun toString() : String
}

class Poly(init : PolyBuilder.() -> Unit) : Expression,
    Composable<Term>(init as ComposableBuilder<Term>.() -> Unit, PolyBuilder()) {

    constructor(vararg expressions: Expression) : this ({
        expressions.forEach { +it }
    })

    class PolyBuilder : ComposableBuilder<Term>() {

        override fun Term.unaryPlus() = expression(this)

        operator fun Expression.unaryPlus() = expression(this)

        private fun expression(exp: Expression) {
            when (exp) {
                is Poly -> exp.elements.forEach { expression(it) }
                is Combo -> tempElements
                    .indexOfFirst { it is Combo && it.letter == exp.letter }
                    .let {
                        if (it == -1) return@let tempElements.add(exp)
                        tempElements[it] = (tempElements[it] + exp) as Combo
                    }
                is JustANumber -> tempElements
                    .indexOfFirst { it is JustANumber }
                    .let {
                        if (it == -1) return@let tempElements.add(exp)
                        tempElements[it] = (tempElements[it] + exp) as JustANumber
                    }
            }
        }
    }

    override fun plus(exp: Expression) =
        Poly(this@Poly, exp)

    override fun toString() = elements.joinToString(separator = " + ")
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