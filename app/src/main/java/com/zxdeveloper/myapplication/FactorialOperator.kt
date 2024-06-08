package com.zxdeveloper.myapplication

import com.udojava.evalex.Expression
import com.udojava.evalex.LazyOperator
import java.math.BigDecimal
import java.math.BigInteger

class FactorialOperator : LazyOperator {

    override fun getOper(): String {
        return "!"
    }

    override fun getPrecedence(): Int {
        return Expression.OPERATOR_PRECEDENCE_POWER + 1
    }

    override fun isLeftAssoc(): Boolean {
        return true
    }

    override fun isBooleanOperator(): Boolean {
        return false
    }

    override fun isUnaryOperator(): Boolean {
        return true
    }

    override fun eval(v1: Expression.LazyNumber?, v2: Expression.LazyNumber?): Expression.LazyNumber {
        val arg = v1!!.eval().toInt()
        require(arg >= 0) { "The operand of the factorial can not be less than zero" }
        var result = BigInteger.ONE
        for (i in 2..arg) {
            result = result.multiply(BigInteger.valueOf(i.toLong()))
        }
        return object : Expression.LazyNumber {
            override fun eval(): BigDecimal {
                return BigDecimal(result)
            }

            override fun getString(): String {
                return result.toString()
            }
        }
    }
}