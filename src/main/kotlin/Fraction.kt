import kotlin.math.abs

infix fun Int.over(other: Int ) = Fraction(this, other)

class Fraction (numerator : Int, denominator : Int) {
    constructor(int: Int) : this(int, 1)

    val numerator : Int
    val denominator : Int

    init {
        require(denominator != 0) { "Denominator cannot be 0" }

        var gcd = abs(numerator)
        var temp = abs(denominator)

        while (gcd != temp) {
            if (gcd > temp)
                gcd -= temp
            else
                temp -= gcd
        }

        this.numerator = numerator / gcd
        this.denominator = denominator / gcd
    }

    override fun toString() = "$numerator/${if(denominator != 1) denominator else ""}"
}