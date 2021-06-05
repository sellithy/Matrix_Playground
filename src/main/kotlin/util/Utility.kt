package util

import kotlin.math.abs

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