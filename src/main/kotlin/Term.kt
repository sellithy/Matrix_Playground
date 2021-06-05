import java.lang.StringBuilder
import java.util.*

private operator fun Number.plus(other: Number): Number =
    if (this is Int && other is Int)
        this + other
    else
        this.toDouble() + other.toDouble()

private operator fun Number.times(other: Number): Number =
    if (this is Int && other is Int)
        this * other
    else
        this.toDouble() * other.toDouble()

sealed interface Term : Expression {
    operator fun times(term: Term): Term
}

class JustANumber(val number: Number) : Term {
    override fun toString() = number.toString()

    override fun plus(exp: Expression) =
        when (exp) {
            is Poly, is Combo -> exp + this
            is JustANumber -> JustANumber(number + exp.number)
        }

    override fun times(exp: Expression) =
        when (exp) {
            is Poly -> exp * this
            is Term -> this * exp
        }

    override fun times(term: Term): Term =
        when (term) {
            is Combo -> term * this
            is JustANumber -> JustANumber(number * term.number)
        }
}

class Combo(val coefficient: Number, val letters: SortedMap<Char, Int>) : Term {
    constructor(pair: Pair<Number, Char>) : this(pair.first, sortedMapOf(pair.second to 1))

    override fun toString() =
        StringBuilder().apply {
            if (coefficient != 1 && coefficient != -1) append(coefficient)
            if (coefficient == -1) append('-')
            letters.forEach { (letter, exponent) ->
                if (exponent == 1) append(letter)
                else append("($letter^$exponent)")
            }
        }.toString()

    init {
        require(letters.all { (letter, _) -> letter.isLetter() }) { "Needs to be a letter" }
    }

    override fun plus(exp: Expression) =
        when (exp) {
            is Poly -> exp + this
            is JustANumber -> Poly(exp, this)
            is Combo ->
                if (letters == exp.letters)
                    Combo(coefficient + exp.coefficient, letters)
                else
                    Poly(exp, this)
        }

    override fun times(term: Term) =
        when (term) {
            is Combo ->
                Combo(coefficient * term.coefficient,
                    (letters.asSequence() + term.letters.asSequence())
                        .groupBy({ it.key }, { it.value })
                        .mapValues { (_, values) -> values.sum() }.toSortedMap())

            is JustANumber -> Combo(coefficient * term.number, letters)
        }

    override fun times(exp: Expression) =
        when (exp) {
            is Poly -> exp * this
            is Term -> this * exp
        }

}