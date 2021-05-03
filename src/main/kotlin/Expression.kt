private operator fun Number.plus(other: Number): Number =
    if (this is Int && other is Int)
        this + other
    else
        this.toDouble() + other.toDouble()

sealed class Expression {
    abstract operator fun plus(exp: Expression): Expression
    abstract override fun toString() : String
}

class Poly(private val poly: List<Term>) : Expression(), List<Term> by poly {

    constructor(terms: PolyBuilder.() -> Unit) : this(
        PolyBuilder().run {
            terms()
            build()
        }
    )

    constructor(vararg expressions: Expression) : this({
        expressions.forEach { expression(it) }
    })

    class PolyBuilder {
        private val tempPoly = mutableListOf<Term>()

        operator fun Expression.unaryPlus() = expression(this)

        fun expression(exp: Expression) {
            when (exp) {
                is Poly -> exp.poly.forEach { expression(it) }
                is Combo -> tempPoly
                    .indexOfFirst { it is Combo && it.letter == exp.letter }
                    .let { if (it != -1) tempPoly[it] = (tempPoly[it] + exp) as Combo else tempPoly.add(exp) }
                is JustANumber -> tempPoly
                    .indexOfFirst { it is JustANumber }
                    .let { if (it != -1) tempPoly[it] = (tempPoly[it] + exp) as JustANumber else tempPoly.add(exp) }
            }
        }

        fun build() = tempPoly
    }

    override fun plus(exp: Expression) =
        Poly {
            expression(this@Poly)
            expression(exp)
        }

    override fun toString() = poly.joinToString(separator = " + ")
}

//region Term
sealed class Term : Expression() {
    abstract override fun toString(): String
}

class JustANumber(val number: Number) : Term() {
    override fun toString() = number.toString()

    override fun plus(exp: Expression) =
        when (exp) {
            is JustANumber -> JustANumber(number + exp.number)
            is Poly -> exp + this
            is Combo -> exp + this
        }

}

class Combo(val number: Number, val letter: Char) : Term(){
    constructor(pair: Pair<Number, Char>) : this(pair.first, pair.second)
    override fun toString() = "$number$letter"

    init {
        require(letter.isLetter()) {"Needs to be a letter"}
    }

    override fun plus(exp: Expression) =
        when (exp) {
            is JustANumber -> Poly(exp, this)
            is Poly -> exp + this
            is Combo ->
                if (letter == exp.letter)
                    Combo(number + exp.number, letter)
                else
                    Poly(exp, this)
        }
}
//endregion