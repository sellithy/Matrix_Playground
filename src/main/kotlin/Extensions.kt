infix fun Int.over(other: Int) = Fraction(this, other)
operator fun Int.times(vec: Vector) = Fraction(this) * vec
operator fun Fraction.times(vec: Vector) =
    Vector {
        vec.map { +(this@times * it) }
    }