package com.zxdeveloper.myapplication

import java.math.BigDecimal

abstract class Operator(
    val operatorName: String,
    val precedence: Int,
    val isLeftAssociative: Boolean
) {
    abstract fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal
}