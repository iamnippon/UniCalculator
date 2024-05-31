package com.zxdeveloper.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private val input: TextView by lazy { findViewById<TextView>(R.id.input) }
    private val leftParenthesisButton: Button by lazy { findViewById<Button>(R.id.leftParenthesisButton) }
    private val rightParenthesisButton: Button by lazy { findViewById<Button>(R.id.rightParenthesisButton) }
    private val resultDisplay: TextView by lazy { findViewById<TextView>(R.id.resultDisplay) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val divideBy100Button: Button = findViewById(R.id.divideBy100Button)
        divideBy100Button.setOnClickListener { operatorButton(it) }

        val divideButton: Button = findViewById(R.id.divideButton)
        divideButton.setOnClickListener { operatorButton(it) }

        val multiplyButton: Button = findViewById(R.id.multiplyButton)
        multiplyButton.setOnClickListener { operatorButton(it) }

        val subtractButton: Button = findViewById(R.id.subtractButton)
        subtractButton.setOnClickListener { operatorButton(it) }

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener { operatorButton(it) }


        input.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // No action needed here
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // No action needed here
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.length > 15) {
                        s.delete(0, 1)
                    }
                }
            })
    }

    fun parenthesesButton(view: View) {
        // Show the leftParenthesisButton and rightParenthesisButton when parenthesesButton is clicked
        leftParenthesisButton.visibility = View.VISIBLE
        rightParenthesisButton.visibility = View.VISIBLE
    }

    fun leftParenthesisButton(view: View) {
        // Append "(" to the input and hide the leftParenthesisButton and rightParenthesisButton
        input.append("(")
        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
    }

    fun rightParenthesisButton(view: View) {
        // Append ")" to the input and hide the leftParenthesisButton and rightParenthesisButton
        input.append(")")
        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
    }


    fun operatorButton(view: View) {
        // Append the operator to the input when an operator button is clicked
        val button = view as Button
        val operator = button.text.toString()
        val currentInput = input.text.toString()

        if (currentInput.isNotEmpty() && currentInput.last().isDigit().not()) {
            input.text = currentInput.dropLast(1) + operator
        } else {
            input.append(operator)
        }
    }


    // controls the button clicked and display the value
    fun keyDigitPadMappingToDisplay(view: View) {
        val button = view as Button
        input.append(button.text)
    }

    fun equalsButton(view: View) {
        // Perform the calculation based on the input and display the result
        val currentInput = input.text.toString()

        // You need to implement the calculate function
        val result = calculate(currentInput)

        // Display the result in resultDisplay
        resultDisplay.text = result.toString()
    }

    // This function needs to be implemented according to your requirements
    fun calculate(input: String): Double {
        // Parse the input and perform the calculation
        // This is a placeholder implementation and needs to be replaced
        return 0.0
    }



}