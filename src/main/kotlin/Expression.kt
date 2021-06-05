sealed interface Expression {
    operator fun plus(exp: Expression): Expression
    operator fun times(exp: Expression): Expression
    override fun toString(): String
}

sealed interface Term : Expression {
    operator fun times(term: Term): Term
}