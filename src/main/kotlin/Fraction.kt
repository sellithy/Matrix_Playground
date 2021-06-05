import kotlin.math.abs

infix fun Int.over(other: Int) = Fraction(this, other)
fun gcd(a: Int, b: Int): Int {
    var gcd = abs(a)
    var temp = abs(b)

    while (gcd != temp) {
        if (gcd > temp)
            gcd -= temp
        else
            temp -= gcd
    }

    return gcd
}

fun lcm(a: Int, b: Int) = a * b / gcd(a, b)

class Fraction(num: Int, den: Int) : Term {
    constructor(int: Int) : this(int, 1)

    val numerator: Int
    val denominator: Int

    init {
        require(den != 0) { "Denominator cannot be 0" }

        if (num == 0) {
            numerator = 0
            denominator = 1
        } else {
            gcd(num, den).let {
                numerator = num / it
                denominator = den / it
            }
        }
    }

    fun inverse() = denominator over numerator

    infix fun nequalTo(number: Number) = !equalTo(number)
    infix fun equalTo(number: Number) =
        when (number) {
            is Int -> denominator == 1 && numerator == number
            else -> number.toDouble().equals(numerator.toDouble() / denominator)
        }

    operator fun plus(other: Fraction) : Fraction{
        if(other equalTo 0)
            return numerator over denominator

        if(this equalTo 0)
            return other.numerator over other.denominator

        return lcm(denominator, other.denominator).let {
            (numerator * (it / denominator) + other.numerator * (it / numerator)) over it
        }
    }
    override fun plus(exp: Expression) =
        when (exp) {
            is Fraction -> this + exp
            is Combo, is Poly -> exp + this
        }

    operator fun times(other: Fraction) = (numerator * other.numerator) over (denominator * other.denominator)
    override fun times(term: Term) =
        when(term){
            is Fraction -> this * term
            is Combo -> term * this
        }
    override fun times(exp: Expression) =
        when (exp) {
            is Term -> this * exp
            is Poly -> exp * this
        }

    operator fun div(other: Fraction) = this * other.inverse()

    override fun toString() = "$numerator" + if (denominator != 1) "/$denominator" else ""
}