import matrices.Matrix
import matrices.Vector
import pieces.Combo
import pieces.Fraction
import pieces.Poly
import pieces.Term
import util.times

fun main() {
    val p1 = Poly {
        +Fraction(5)
        +Fraction(3)
        +Combo(8, 'a')
    }

    val p2 = Poly {
        +Fraction(6)
        +Combo(9, 'a')
        +Combo(2, 'b')
        +Combo(5, 'c')
    }

    println(p1 + listOf<Term>(Fraction(3)))
    println("p1: $p1")
    println("p2: $p2")
    println("p1 + p2: ${p1 + p2}")

    println(Combo(5, 'a') + Combo(7, 'a'))

    val vector1 = Vector {
        +p1
        +Fraction(2)
        +Combo(2, 'a')
    }

    val vector2 = Vector {
        +Combo(2, 'a')
        +p1
        +Fraction(2)
        +Fraction(4)
    }

    println("v1: $vector1")
    println("v2: $vector2")

    val m1 = Matrix {
        +Vector {
            +Fraction(1)
            +Fraction(2)
            +Fraction(3)
        }

        +Vector {
            +Fraction(3)
            +Fraction(2)
            +Fraction(1)
        }

        +vector1
//        +v2
    }

    println(m1)

    val pp1 = Poly {
        +Combo(1, 'a')
        +Combo(1, 'b')
    }

    val pp2 = Poly {
        +Combo(1, 'a')
        +Combo(1, 'c')
    }

    println("pp1: $pp1")
    println("pp2: $pp2")
    val product = pp1 * pp2
    println("pp1 * pp2: $product")

    val z = Vector{
        +Fraction(3)
        +Fraction(9)
        +Fraction(0)
        +Fraction(-1)
    }

    val v1 = Vector{
        +Fraction(3)
        +Fraction(0)
        +Fraction(-1)
        +Fraction(-2)
    }

    val v2 = Vector{
        +Fraction(4)
        +Fraction(-1)
        +Fraction(6)
        +Fraction(3)
    }

    println("z: $z")
    println("v1: $v1")
    println("v2: $v2")
    println("2 * v1: ${2 * v1}")

    val zdotv1 = (z dot v1) as Fraction
    val v1dotv1 = (v1 dot v1) as Fraction

    val zdotv2 = (z dot v2) as Fraction
    val v2dotv2 = (v2 dot v2) as Fraction
    println("z dot v1: $zdotv1")
    println("v1 dot v1: $v1dotv1")

    println(zdotv1 / v1dotv1 * v1 + zdotv2 / v2dotv2 * v2 )
}