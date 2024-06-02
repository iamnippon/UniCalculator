package com.zxdeveloper.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.udojava.evalex.Expression
import com.zxdeveloper.myapplication.R.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

    private val input: TextView by lazy { findViewById<TextView>(id.input) }
    private val leftParenthesisButton: Button by lazy { findViewById<Button>(id.leftParenthesisButton) }
    private val rightParenthesisButton: Button by lazy { findViewById<Button>(id.rightParenthesisButton) }
    private val resultDisplay: TextView by lazy { findViewById<TextView>(id.resultDisplay) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val divideBy100Button: Button = findViewById(id.divideBy100Button)
        divideBy100Button.setOnClickListener { operatorButton(it) }

        val divideButton: Button = findViewById(id.divideButton)
        divideButton.setOnClickListener { operatorButton(it) }

        val multiplyButton: Button = findViewById(id.multiplyButton)
        multiplyButton.setOnClickListener { operatorButton(it) }

        val subtractButton: Button = findViewById(id.subtractButton)
        subtractButton.setOnClickListener { operatorButton(it) }

        val addButton: Button = findViewById(id.addButton)
        addButton.setOnClickListener { operatorButton(it) }

        val backspaceButton: ImageButton = findViewById(id.backspaceButton)
        backspaceButton.setOnClickListener { backspaceButton() }

        val pointButton: ImageButton = findViewById(id.pointButton)
        pointButton.setOnClickListener { pointButton(it) }

        val percentButton: Button = findViewById(id.divideBy100Button)
        percentButton.setOnClickListener { percentButton(it) }

        val scientistModeSwitchButton: ImageButton = findViewById(id.scientistModeSwitchButton)
        scientistModeSwitchButton.setOnClickListener { scientistModeSwitchButton() }

        val sineButton: Button = findViewById(id.sineButton)
        sineButton.setOnClickListener { input.append("sin(") }

        val cosineButton: Button = findViewById(id.cosineButton)
        cosineButton.setOnClickListener { input.append("cos(") }

        val tangentButton: Button = findViewById(id.tangentButton)
        tangentButton.setOnClickListener { input.append("tan(") }

        val invButton: Button = findViewById(id.invButton)
        invButton.setOnClickListener { input.append("^(-1)") }

        val eButton: Button = findViewById(id.eButton)
        eButton.setOnClickListener { input.append("e") }

        val naturalLogarithmButton: Button = findViewById(id.naturalLogarithmButton)
        naturalLogarithmButton.setOnClickListener { input.append("ln(") }

        val logarithmButton: Button = findViewById(id.logarithmButton)
        logarithmButton.setOnClickListener { input.append("log(") }

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
        var operator = button.text.toString()
        val currentInput = input.text.toString()

        // Replace special characters with standard ASCII characters
        if (operator == "×") {
            operator = "*"
        } else if (operator == "÷") {
            operator = "/"
        }

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

    fun equalsButton(view: View) = lifecycleScope.launch {
        val currentInput = input.text.toString()
        try {
            val result = withContext(Dispatchers.Default) { calculate(currentInput) }
            withContext(Dispatchers.Main) {
                resultDisplay.text = result.toString()
                input.text = "" // Clear input after calculation
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                resultDisplay.text = "Error: ${e.message}" // Display error message
            }
        }
    }

    // This function needs to be implemented according to your requirements
    fun calculate(input: String): Double {
        val expression = Expression(input)
        expression.setPrecision(14) // Set the precision to 14 decimal places
        return expression.eval().toDouble()
    }

    // It is used to delete the last character in the input.
    fun backspaceButton() {
        val currentInput = input.text.toString()
        if (currentInput.isNotEmpty()) {
            input.text = currentInput.dropLast(1)
        }

    }

    fun pointButton(view: View) {
        val currentInput = input.text.toString()

        if (currentInput.contains(Regex("\\.[0-9]*$"))) {
            // If it does, do not append another decimal point
            return
        }

        // If the last character is not a decimal point, append a decimal point
        if (currentInput.isNotEmpty() && currentInput.last() != '.') {
            input.append(".")
        }

    }

    fun percentButton(view: View) {
        // Append "/100" to the input when the divideBy100Button is clicked
        val currentInput = input.text.toString()

        // Check if the last character is a digit
        if (currentInput.isNotEmpty() && currentInput.last().isDigit()) {
            input.append("/100")
        }
    }

    fun scientistModeSwitchButton() {
        // Get references to the scientific mode buttons
        val degreeButton: Button = findViewById(id.degreeButton)
        val sineButton: Button = findViewById(id.sineButton)
        val cosineButton: Button = findViewById(id.cosineButton)
        val tangentButton: Button = findViewById(id.tangentButton)
        val invButton: Button = findViewById(id.invButton)
        val eButton: Button = findViewById(id.eButton)
        val naturalLogarithmButton: Button = findViewById(id.naturalLogarithmButton)
        val logarithmButton: Button = findViewById(id.logarithmButton)

        // Create a list of the scientific mode buttons
        val scientificModeButtons = listOf(degreeButton, sineButton, cosineButton, tangentButton, invButton, eButton, naturalLogarithmButton, logarithmButton)

        // Toggle the visibility of each button
        for (button in scientificModeButtons) {
            button.visibility = if (button.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }


}