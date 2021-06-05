fun main() {
    val p1 = Poly {
        +JustANumber(5)
        +JustANumber(3)
        +Combo(8 to 'a')
    }

    val p2 = Poly {
        +JustANumber(6)
        +Combo(9 to 'a')
        +Combo(2 to 'b')
        +Combo(5 to 'c')
    }

    println(p1 + listOf<Term>(JustANumber(3)))
    println("p1: $p1")
    println("p2: $p2")
    println("p1 + p2: ${p1 + p2}")

    println(Combo(5 to 'a') + Combo(7 to 'a'))

    val vector1 = Vector {
        +p1
        +JustANumber(2)
        +Combo(2 to 'a')
    }

    val vector2 = Vector {
        +Combo(2 to 'a')
        +p1
        +JustANumber(2)
        +JustANumber(4)
    }

    println("v1: $vector1")
    println("v2: $vector2")

    val m1 = Matrix {
        +Vector {
            +JustANumber(1)
            +JustANumber(2)
            +JustANumber(3)
        }

        +Vector {
            +JustANumber(3)
            +JustANumber(2)
            +JustANumber(1)
        }

        +vector1
//        +v2
    }

    println(m1)

    val pp1 = Poly {
        +Combo(1 to 'a')
        +Combo(1 to 'b')
    }

    val pp2 = Poly {
        +Combo(1 to 'a')
        +Combo(1 to 'c')
    }

    println("pp1: $pp1")
    println("pp2: $pp2")
    val product = pp1 * pp2
    println("pp1 * pp2: $product")

    val z = Vector{
        +JustANumber(3)
        +JustANumber(9)
        +JustANumber(0)
        +JustANumber(-1)
    }

    val v1 = Vector{
        +JustANumber(3)
        +JustANumber(0)
        +JustANumber(-1)
        +JustANumber(-2)
    }

    val v2 = Vector{
        +JustANumber(4)
        +JustANumber(-1)
        +JustANumber(6)
        +JustANumber(3)
    }

    println("z: $z")
    println("v1: $v1")
    println("v2: $v2")
    println("2 * v1: ${2 * v1}")

    val zdotv1 = (z dot v1) as JustANumber
    val v1dotv1 = (v1 dot v1) as JustANumber

    val zdotv2 = (z dot v2) as JustANumber
    val v2dotv2 = (v2 dot v2) as JustANumber
    println("z dot v1: $zdotv1")
    println("v1 dot v1: $v1dotv1")

    println((zdotv1.number.toDouble() / v1dotv1.number.toDouble()) * v1 + (zdotv2.number.toDouble() / v2dotv2.number.toDouble()) * v2 )
}