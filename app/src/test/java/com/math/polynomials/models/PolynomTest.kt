package com.math.polynomials.models

import org.junit.Test

import org.junit.Assert.*
import java.lang.IllegalArgumentException

class PolynomTest {

    @Test
    fun testNoArgConstructor() {
        val p = Polynom()
        assertEquals(0, p.degree)
        assertEquals(0, p[0])
    }

    @Test
    fun testConstructorIntArray() {
        val p = Polynom(intArrayOf(2, 1, 5))
        assertEquals(2, p.degree)
        assertEquals(2, p[2])
        assertEquals(1, p[1])
        assertEquals(5, p[0])
    }

    @Test
    fun testToString1() {
        val p1 = Polynom(intArrayOf(0))
        assertEquals("0", p1.toString())
    }

    @Test
    fun testToString2() {
        val p2 = Polynom(intArrayOf(1))
        assertEquals("1", p2.toString())
    }

    @Test
    fun testToString3() {
        val p3 = Polynom(intArrayOf(2, -3))
        assertEquals("2x - 3", p3.toString())
    }

    @Test
    fun testToString4() {
        val p4 = Polynom(intArrayOf(1, -5, 6))
        assertEquals("x^2 - 5x + 6", p4.toString())
    }

    @Test
    fun testToString5() {
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        assertEquals("7x^4 - 8x^3 - 9x^2 - 10x - 11", p5.toString())
    }

    @Test
    fun testToString6() {
        val p6 = Polynom(intArrayOf(-1, 0, -2, 0, 3, 0, -4, 0))
        assertEquals("-x^7 - 2x^5 + 3x^3 - 4x", p6.toString())
    }

    @Test
    fun testToString7() {
        val p7 = Polynom(intArrayOf(-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0))
        assertEquals("-2x^11 + x", p7.toString())
    }

    @Test
    fun testEquals4() {
        val p4a = Polynom(intArrayOf(1, -5, 6))
        val p4b = Polynom(intArrayOf(1, -5, 6))
        assertEquals(true, p4a == p4b)
    }

    @Test
    fun testEquals7() {
        val p7a = Polynom(intArrayOf(-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0))
        val p7b = Polynom(intArrayOf(-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0))
        assertEquals(true, p7a == p7b)
    }

    @Test
    fun testEqualsNull() {
        val p7a = Polynom(intArrayOf(-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0))
        assertEquals(false, p7a.equals(null))
    }

    @Test
    fun testEqualsWrongType() {
        val p7a = Polynom(intArrayOf(-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0))
        assertEquals(false, p7a.equals("A string"))
    }

    @Test
    fun testEqualsWrongDegree() {
        val p1 = Polynom(intArrayOf(1, 1, 1))
        val p2 = Polynom(intArrayOf(1, 1))
        assertEquals(false, p1 == p2)
    }

    @Test
    fun testp1plusp2() {
        val p1 = Polynom(intArrayOf(0))
        val p2 = Polynom(intArrayOf(1))
        assertEquals(Polynom(intArrayOf(1)), p1.plus(p2))
    }

    @Test
    fun testp2plusp3() {
        val p2 = Polynom(intArrayOf(1))
        val p3 = Polynom(intArrayOf(2, -3))
        assertEquals(Polynom(intArrayOf(2, -2)), p2.plus(p3))
    }

    @Test
    fun testp3plusp4() {
        val p3 = Polynom(intArrayOf(2, -3))
        val p4 = Polynom(intArrayOf(1, -5, 6))
        assertEquals(Polynom(intArrayOf(1, -3, 3)), p3.plus(p4))
    }

    @Test
    fun testp4plusp5() {
        val p4 = Polynom(intArrayOf(1, -5, 6))
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        assertEquals(Polynom(intArrayOf(7, -8, -8, -15, -5)), p4.plus(p5))
    }

    @Test
    fun testp5plusp6() {
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        val p6 = Polynom(intArrayOf(-1, 0, -2, 0, 3, 0, -4, 0))
        assertEquals(Polynom(intArrayOf(-1, 0, -2, 7, -5, -9, -14, -11)), p5.plus(p6))
    }

    @Test
    fun testp1timesp2() {
        val p1 = Polynom(intArrayOf(0))
        val p2 = Polynom(intArrayOf(1))
        assertEquals(Polynom(intArrayOf(0)), p1.times(p2))
    }

    @Test
    fun testp2timesp3() {
        val p2 = Polynom(intArrayOf(1))
        val p3 = Polynom(intArrayOf(2, -3))
        assertEquals(Polynom(intArrayOf(2, -3)), p2.times(p3))
    }

    @Test
    fun testp3timesp4() {
        val p3 = Polynom(intArrayOf(2, -3))
        val p4 = Polynom(intArrayOf(1, -5, 6))
        assertEquals(Polynom(intArrayOf(2, -13, 27, -18)), p3.times(p4))
    }

    @Test
    fun testp4timesp5() {
        val p4 = Polynom(intArrayOf(1, -5, 6))
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        assertEquals(Polynom(intArrayOf(7, -43, 73, -13, -15, -5, -66)), p4.times(p5))
    }

    @Test
    fun testp5timesp6() {
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        val p6 = Polynom(intArrayOf(-1, 0, -2, 0, 3, 0, -4, 0))
        assertEquals(
            Polynom(intArrayOf(-7, 8, -5, 26, 50, -4, -33, 2, 3, 40, 44, 0)),
            p5.times(p6)
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_ConstructorFromString_exception1() {
        Polynom("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_ConstructorFromString_exception2() {
        Polynom("9-")
    }


    @Test(expected = IllegalArgumentException::class)
    fun test_ConstructorFromString_exception3() {
       Polynom("x9")
    }

    @Test
    fun test_ConstructorFromString_zero() {
        val p = Polynom("0")
        assertEquals(0, p.degree)
        assertEquals(0, p[0])
        assertEquals("0", p.toString())
    }

    @Test
    fun test_ConstructorFromString_two() {
        val p = Polynom("2")
        assertEquals(0, p.degree)
        assertEquals(2, p[0])
        assertEquals("2", p.toString())
    }


    @Test
    fun test_ConstructorFromString_twoAlt() {
        val expected = Polynom(intArrayOf(3, 4, 5, 6))
        val actual = Polynom("3x^3 + 4x^2 + 5x + 6")
        assertEquals(expected, actual)
    }

    @Test
    fun test_ConstructorFromString_negThree() {
        val p = Polynom("-3")
        assertEquals(0, p.degree)
        assertEquals(-3, p[0])
        assertEquals("-3", p.toString())
    }


    @Test
    fun test_ConstructorFromString_x() {
        val p = Polynom("x")
        assertEquals(1, p.degree)
        assertEquals(0, p[0])
        assertEquals(1, p[1])
        assertEquals("x", p.toString())
    }

    @Test
    fun test_ConstructorFromString_negX() {
        val p = Polynom("-x")
        assertEquals(1, p.degree)
        assertEquals(0, p[0])
        assertEquals(-1, p[1])
        assertEquals("-x", p.toString())
    }

    @Test
    fun test_ConstructorFromString_p3() {
        val p3 = Polynom(intArrayOf(2, -3))
        val p3s: String = p3.toString()
        val p3n = Polynom(p3s)
        assertEquals(p3n, p3)
    }


    @Test
    fun test_ConstructorFromString_p4() {
        val p4 = Polynom(intArrayOf(1, -5, 6))
        val p4s: String = p4.toString()
        val p4n = Polynom(p4s)
        assertEquals(p4n, p4)
    }

    @Test
    fun test_ConstructorFromString_p5() {
        val p5 = Polynom(intArrayOf(7, -8, -9, -10, -11))
        val p5s: String = p5.toString()
        val p5n = Polynom(p5s)
        assertEquals(p5n, p5)
    }

    @Test
    fun test_ConstructorFromString_p6() {
        val p6 = Polynom(intArrayOf(-1, 0, -2, 0, 3, 0, -4, 0))
        val p6s: String = p6.toString()
        val p6n = Polynom(p6s)
        assertEquals(p6n, p6)
    }

}
