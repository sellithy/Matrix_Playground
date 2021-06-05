import java.lang.StringBuilder
import java.util.*

sealed interface Term : Expression {
    operator fun times(term: Term): Term
}

class Combo(val coefficient: Fraction, val letters: SortedMap<Char, Int>) : Term {
    constructor(int: Int, char: Char) : this(Fraction(int), char)
    constructor(fraction: Fraction, char: Char) : this(fraction, sortedMapOf(char to 1))

    override fun toString() =
        StringBuilder().apply {
            if (coefficient nequalTo 1 && coefficient nequalTo -1) append(coefficient)
            if (coefficient equalTo -1) append('-')
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
            is Fraction -> Poly(exp, this)
            is Combo ->
                if (letters == exp.letters)
                    Combo(coefficient + exp.coefficient, letters)
                else
                    Poly(exp, this)
            is Poly -> exp + this
        }

    override fun times(term: Term) =
        when (term) {
            is Fraction -> Combo(coefficient * term, letters)
            is Combo ->
                Combo(coefficient * term.coefficient,
                    (letters.asSequence() + term.letters.asSequence())
                        .groupBy({ it.key }, { it.value })
                        .mapValues { (_, values) -> values.sum() }.toSortedMap())
        }

    override fun times(exp: Expression) = exp * this
}