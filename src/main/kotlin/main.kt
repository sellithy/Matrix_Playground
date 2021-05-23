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
    println("p1: $p1")
    println("p2: $p2")
    println("p1 + p2: ${p1 + p2}")

    println(Combo(5 to 'a') + Combo(7 to 'a'))

    val v1 = Vector {
        +p1
        +JustANumber(2)
        +Combo(2 to 'a')
    }

    val v2 = Vector {
        +Combo(2 to 'a')
        +p1
        +JustANumber(2)
        +JustANumber(4)
    }

    println("v1: $v1")
    println("v2: $v2")

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

        +v1
//        +v2
    }

    println(m1)
}