package com.example.prodigy_ad_1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prodigy_ad_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var lastOperator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) = with(binding) {
        currentResult.append((view as TextView).text)
        lastNumeric = true
        lastDot = false
    }

    @SuppressLint("SetTextI18n")
    fun onClear(view: View) = with(binding) {
        currentResult.text = ""
        lastOperation.text = ""
        resetCalculator()
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDot) {
            binding.currentResult.append(Constant.DOT)
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !Operations.isOperatorAdded(binding.currentResult.text.toString())) {
            lastOperator = (view as TextView).text.toString()
            binding.currentResult.append(view.text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View) {
        performCalculation()
    }

    @SuppressLint("SetTextI18n")
    fun onPercentage(view: View) {
        performCalculation(isPercentage = true)
    }

    @SuppressLint("SetTextI18n")
    fun onNegative(view: View) = with(binding) {
        currentResult.text = if (currentResult.text.startsWith(Constant.MINUS)) {
            currentResult.text.substring(1)
        } else {
            Constant.MINUS + currentResult.text
        }
        lastOperator = null
        lastNumeric = true
    }

    @SuppressLint("SetTextI18n")
    private fun performCalculation(isPercentage: Boolean = false) = with(binding) {
        if (lastNumeric && lastOperator != null) {
            val value = currentResult.text.toString()
            val operands = value.split(lastOperator!!)
            if (operands.size == 2) {
                val firstOperand = operands[0].toDouble()
                var secondOperand = operands[1]

                if (isPercentage) {
                    val percentageValue = firstOperand * (secondOperand.toDouble() / 100)
                    secondOperand = percentageValue.toString()
                    currentResult.text = "$firstOperand $lastOperator $percentageValue"
                }

                val result = Operations.performOperation(
                    lastOperator!!,
                    firstOperand.toString(),
                    secondOperand
                )
                currentResult.text = Operations.formatResult(result)
                lastOperation.text = value
            }
        }
    }

    private fun resetCalculator() {
        lastNumeric = false
        lastDot = false
        lastOperator = null
    }
}