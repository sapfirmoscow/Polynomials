package com.math.polynomials.models

import java.lang.IllegalArgumentException
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.regex.Pattern
import kotlin.math.abs

class Polynom : ArrayList<Int> {

    val degree: Int
        get() = size - 1

    constructor() : super(1) {
        this.add(0, 0)
    }

    constructor(coeffs: IntArray) : super(coeffs.size) {
        for (i in coeffs.indices) {
            this.add(0, coeffs[i])
        }
    }

    constructor(s: String) : super(1)
    {
        val integerConstantPattern = Pattern.compile("^-?\\d+$")
        val integerConstantMatcher = integerConstantPattern.matcher(s)

        if (integerConstantMatcher.matches()) {
            this.add(0, s.toInt())
            return
        }

        val degreeOnePattern = Pattern.compile("^(-?)(\\d*)x( ([+-]) (\\d+))?$")

        val degreeOneMatcher = degreeOnePattern.matcher(s)
        if (degreeOneMatcher.matches()) {
            var xCoeff = 1
            var constantTerm = 0
            val xCoeffSign = degreeOneMatcher.group(1)
            val xCoeffString = degreeOneMatcher.group(2)
            val constantTermSign = degreeOneMatcher.group(4)
            val constantTermString = degreeOneMatcher.group(5)
            if (xCoeffString != null && xCoeffString.isNotEmpty()) {
                xCoeff = xCoeffString.toInt()
            }
            if (xCoeffSign != null && xCoeffSign == MINUS) {
                xCoeff *= -1
            }
            if (constantTermString != null && constantTermString.isNotEmpty()) {
                constantTerm = constantTermString.toInt()
            }
            if (constantTermSign != null && constantTermSign == MINUS) {
                constantTerm *= -1
            }
            this.add(0, constantTerm)
            this.add(1, xCoeff)
            return
        }

        val twoOrMoreRe = ("^"
                + "([-]?)(\\d*)x\\^(\\d+)"
                + "(( [+-] \\d*x\\^\\d+)*)"
                + "( [+-] \\d*x)?"
                + "( [+-] \\d+)?"
                + "$")
        val degreeTwoOrMorePattern = Pattern.compile(twoOrMoreRe)
        val degreeTwoOrMoreMatcher = degreeTwoOrMorePattern.matcher(s)

        if (degreeTwoOrMoreMatcher.matches()) {
            var firstCoeff = 1
            val startSign = degreeTwoOrMoreMatcher.group(1)
            val coeffString = degreeTwoOrMoreMatcher.group(2)
            val degreeString = degreeTwoOrMoreMatcher.group(3)
            val middleXtoTheTerms = degreeTwoOrMoreMatcher.group(4)
            val optionalXTermPart = degreeTwoOrMoreMatcher.group(6)
            val optionalConstantTermPart = degreeTwoOrMoreMatcher.group(7)
            if (coeffString != null && coeffString.isNotEmpty()) {
                firstCoeff = coeffString.toInt()
            }
            if (startSign != null && startSign == MINUS) {
                firstCoeff *= -1
            }
            val degree = degreeString.toInt()

            ensureCapacity(degree + 1)
            for (i in 0..degree)
                this.add(0, 0)
            this[degree] = firstCoeff
            if (middleXtoTheTerms != null && middleXtoTheTerms != "") {
                val addlXtoThePowerTermPattern = Pattern.compile(" ([+-]) (\\d+)(x\\^)(\\d+)")
                val addlXtoThePowerTermMatcher =
                    addlXtoThePowerTermPattern.matcher(middleXtoTheTerms)
                while (addlXtoThePowerTermMatcher.find()) {
                    var coeff = 1
                    val sign = addlXtoThePowerTermMatcher.group(1)
                    val nextCoeffString = addlXtoThePowerTermMatcher.group(2)
                    val nextDegreeString = addlXtoThePowerTermMatcher.group(4)
                    if (nextCoeffString != null && nextCoeffString != "") {
                        coeff = nextCoeffString.toInt()
                    }
                    if (sign != null && sign == MINUS) {
                        coeff *= -1
                    }
                    this[nextDegreeString.toInt()] = coeff
                }
            }

            if (optionalXTermPart != null && optionalXTermPart.isNotEmpty()) {
                val optXTermPattern = Pattern.compile("^ ([+-]) (\\d*)x$")
                val optXTermMatcher = optXTermPattern.matcher(optionalXTermPart)
                optXTermMatcher.find()
                var xCoeff = 1
                val xCoeffSign = optXTermMatcher.group(1)
                val xCoeffString = optXTermMatcher.group(2)
                if (xCoeffString != null && xCoeffString.isNotEmpty()) {
                    xCoeff = xCoeffString.toInt()
                }
                if (xCoeffSign != null && xCoeffSign == MINUS) {
                    xCoeff *= -1
                }
                this[1] = xCoeff
            }
            if (optionalConstantTermPart != null
                && optionalConstantTermPart.isNotEmpty()
            ) {
                val constantTermPattern = Pattern.compile("^ ([+-]) (\\d+)$")
                val constantTermMatcher = constantTermPattern.matcher(optionalConstantTermPart)
                constantTermMatcher.find()
                var constant = 0
                val sign = constantTermMatcher.group(1)
                val constantString = constantTermMatcher.group(2)
                if (constantString != null && constantString.isNotEmpty()) {
                    constant = constantString.toInt()
                }
                if (sign != null && sign == MINUS) {
                    constant *= -1
                }
                this[0] = constant
            }
            return
        }
        throw IllegalArgumentException("Bad polynom String: [$s]")
    }

    operator fun plus(p: Polynom): Polynom {
        val thisDegree = degree
        val thatDegree = p.degree
        val numCoeffs = if (thisDegree > thatDegree) thisDegree + 1 else thatDegree + 1
        val coeffs = IntArray(numCoeffs)
        for (i in 0 until numCoeffs) {
            coeffs[i] = 0
        }
        for (i in 0..thisDegree) {
            coeffs[i] += this[i]
        }
        for (i in 0..thatDegree) {
            coeffs[i] += p[i]
        }
        val finalCoeffs = findHighestNonZeroAndInvert(coeffs)
        return Polynom(finalCoeffs)
    }

    operator fun times(p: Polynom): Polynom {
        val newDegree =  degree + p.degree
        val newCoeffs = IntArray(newDegree + 1)
        for (i in newCoeffs.indices) newCoeffs[i] = 0
        for (i in  degree downTo 0) {
            val thisCoeff = this[i]
            for (j in p.degree downTo 0) {
                val thisTermPower = i + j
                newCoeffs[thisTermPower] += thisCoeff * p[j]
            }
        }
        val finalCoeffs = findHighestNonZeroAndInvert(newCoeffs)
        return Polynom(finalCoeffs)
    }


    private fun indexHighestNonZeroTerm(coeffs: IntArray): Int {
        for (i in coeffs.indices.reversed()) {
            if (coeffs[i] != 0) {
                return i
            }
        }
        return 0
    }

    private fun findHighestNonZeroAndInvert(coeffs: IntArray): IntArray {
        val highestNonZeroTerm = indexHighestNonZeroTerm(coeffs)
        val finalCoeffs = IntArray(highestNonZeroTerm + 1)
        for ((j, i) in (highestNonZeroTerm downTo 0).withIndex()) {
            finalCoeffs[i] = coeffs[j]
        }
        return finalCoeffs
    }


    override fun toString(): String {
        var result = ""
        val df = DecimalFormat("#")

        if ( degree == 0) {
            return df.format(this[0])
        }

        val firstTerm = this[degree]

        if (firstTerm < 0) {
            result += "-"
        }

        if (abs(firstTerm) != 1) {
            result += df.format(abs(firstTerm).toLong())
        }
        result += "x"
        if ( degree > 1) {
            result += "^$degree"
        }

        for (i in  degree - 1 downTo 0) {
            if (this[i] == 0) {
                continue
            }
            result += if (this[i] < 0) " - " else " + "

            if (abs(this[i]) != 1) {
                result += df.format(abs(this[i]).toLong())
            }
            if (i >= 2) {
                result += "x^$i"
            } else if (i == 1) {
                result += "x"
            }
        }
        return result
    }

    companion object{
        private const val PLUS = "+"
        private const val MINUS = "-"
    }
}