package com.example.prodigy_ad_1

object Operations {
    fun performOperation(operator: String, firstOperand: String, secondOperand: String): String {
        val first = firstOperand.toDouble()
        val second = secondOperand.toDouble()
        return when (operator) {
            Constant.MINUS -> (first - second).toString()
            Constant.PLUS -> (first + second).toString()
            Constant.MULTIPLY -> (first * second).toString()
            Constant.DIVIDE -> if (second != 0.0) (first / second).toString() else "Error"
            else -> "Error"

        }
    }

    fun formatResult(result: String): String {
        return if (result.endsWith(".0")) result.dropLast(2) else result
    }

    fun isOperatorAdded(value: String): Boolean {
        return value.any {
            it == Constant.PLUS[0] ||
                    it == Constant.MINUS[0] ||
                    it == Constant.MULTIPLY[0] ||
                    it == Constant.DIVIDE[0]
        }
    }
}
