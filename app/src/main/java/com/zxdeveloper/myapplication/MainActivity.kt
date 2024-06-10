package com.zxdeveloper.myapplication

import android.media.SoundPool
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.udojava.evalex.Expression
import com.zxdeveloper.myapplication.R.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


class MainActivity : AppCompatActivity() {

    private val input: TextView by lazy { findViewById<TextView>(id.input) }
    private val leftParenthesisButton: Button by lazy { findViewById<Button>(id.leftParenthesisButton) }
    private val rightParenthesisButton: Button by lazy { findViewById<Button>(id.rightParenthesisButton) }
    private val resultDisplay: TextView by lazy { findViewById<TextView>(id.resultDisplay) }
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private var isDegreeMode = true




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)


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

        val degreeButton: Button = findViewById(id.degreeButton)
        degreeButton.setOnClickListener {
            isDegreeMode = !isDegreeMode
            if (isDegreeMode) {
                degreeButton.text = "DEG"
            } else {
                degreeButton.text = "RAD"
            }
        }





        val sineButton: Button = findViewById(id.sineButton)
        sineButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("sin(") }

        val cosineButton: Button = findViewById(id.cosineButton)
        cosineButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("cos(") }

        val tangentButton: Button = findViewById(id.tangentButton)
        tangentButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("tan(") }

        val invButton: Button = findViewById(id.invButton)
        invButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("^(-1)") }

        val exponentButton: ImageButton = findViewById(id.exponentButton)
        exponentButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("^(") }


        val eButton: Button = findViewById(id.eButton)
        eButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("e") }

        val naturalLogarithmButton: Button = findViewById(id.naturalLogarithmButton)
        naturalLogarithmButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("ln(") }

        val logarithmButton: Button = findViewById(id.logarithmButton)
        logarithmButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("log(") }
        val factorialButton: Button = findViewById(id.factorialButton)
        factorialButton.setOnClickListener {
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("!") }

        val scientistModeSwitchButton: ImageButton = findViewById(R.id.scientistModeSwitchButton)
        scientistModeSwitchButton.setOnClickListener{ scientistModeSwitchButton(it) }

        val clearButton: Button = findViewById(R.id.clearButton)
        clearButton.setOnClickListener{ clearButton(it) }

        val squareButton: Button = findViewById(R.id.squareButton)
        squareButton.setOnClickListener{
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("sqrt(") }

        val piButton: Button = findViewById(R.id.piButton)
        piButton.setOnClickListener{
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

            input.append("3.1416") }


        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        soundId = soundPool.load(this, R.raw.mechanical_sound, 1)



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
        val inputEditText = findViewById<EditText>(R.id.input)
        inputEditText.showSoftInputOnFocus = false
    }




    fun parenthesesButton(view: View) {
        // Show the leftParenthesisButton and rightParenthesisButton when parenthesesButton is clicked
        leftParenthesisButton.visibility = View.VISIBLE
        rightParenthesisButton.visibility = View.VISIBLE
    }

    fun leftParenthesisButton(view: View) {
        // Append "(" to the input and hide the leftParenthesisButton and rightParenthesisButton
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart

        inputEditText.text.insert(cursorPosition, "(")

        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

    }

    fun rightParenthesisButton(view: View) {
        // Append ")" to the input and hide the leftParenthesisButton and rightParenthesisButton
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart

        inputEditText.text.insert(cursorPosition, ")")
        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

    }


    fun operatorButton(view: View) {

        // Append the operator to the input when an operator button is clicked
        val button = view as Button


        var operator = button.text.toString()

        // Get the EditText and the current cursor position
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart

        // Get the current input
        val currentInput = inputEditText.text.toString()

        // Check if the cursor is not at the start and the character before the cursor is a digit or a closing bracket
        if (cursorPosition > 0 && (currentInput[cursorPosition - 1].isDigit() || currentInput[cursorPosition - 1] == ')')) {
            // Insert the operator at the cursor position
            inputEditText.text.insert(cursorPosition, operator)
        }

        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
    }


    // controls the button clicked and display the value
    fun keyDigitPadMappingToDisplay(view: View) {
            val button = view as Button
            val inputEditText = findViewById<EditText>(R.id.input)
            val cursorPosition = inputEditText.selectionStart
            inputEditText.text.insert(cursorPosition, button.text)
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)    }

    fun equalsButton(view: View) = lifecycleScope.launch {
        var currentInput = input.text.toString()

        // Count the number of open and closed parentheses
        val openParentheses = currentInput.count { it == '(' }
        val closedParentheses = currentInput.count { it == ')' }


        // Check if the input is only an operator or a function without a number
        if (currentInput.matches(Regex(".*[+\\-*/%^]$|.*sin\\($|.*cos\\($|.*tan\\($|.*ln\\($|.*log\\($|.*sqrt\\($"))) {
            Toast.makeText(this@MainActivity, "Invalid input", Toast.LENGTH_SHORT).show()
            return@launch
        }


        // If there are more open parentheses than closed parentheses, append the necessary number of closed parentheses
        if (openParentheses > closedParentheses) {
            currentInput += ")".repeat(openParentheses - closedParentheses)
        }

        try {
            val result = withContext(Dispatchers.Default) { calculate(currentInput) }
            withContext(Dispatchers.Main) {
                resultDisplay.text = result.toString()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                resultDisplay.text = "Error: ${e.message}" // Display error message
            }
        }
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
        val result = calculate(currentInput)
        val threshold = 1E10 // Set a threshold for large numbers
        if (result.absoluteValue > threshold) {
            var formattedResult = formatScientific(result)
            formattedResult += ")"
            resultDisplay.text = formattedResult
        }


    }

    // This function needs to be implemented according to your requirements
    fun calculate(input: String): Double {
        val expression = Expression(input)
        expression.setPrecision(14) // Set the precision to 14 decimal places

        var modifiedInput = input
        if (isDegreeMode) {
            modifiedInput = modifiedInput
                .replace("sin(", "sin_deg(")
                .replace("cos(", "cos_deg(")
                .replace("tan(", "tan_deg(")
        } else {
            modifiedInput = modifiedInput
                .replace("sin(", "sin_rad(")
                .replace("cos(", "cos_rad(")
                .replace("tan(", "tan_rad(")
        }

        // Extend the Expression library to handle custom degree functions
        with(expression) {
            if (isDegreeMode) {
                modifiedInput = modifiedInput
                    .replace("sin(", "sin_deg(")
                    .replace("cos(", "cos_deg(")
                    .replace("tan(", "tan_deg(")
            } else {
                modifiedInput = modifiedInput
                    .replace("sin(", "sin_rad(")
                    .replace("cos(", "cos_rad(")
                    .replace("tan(", "tan_rad(")
            }

            // Extend the Expression library to handle custom degree functions
            addOperator(object : Expression.Operator("sin_deg", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(sin(Math.toRadians(v1.toDouble())))
                }
            })
            addOperator(object : Expression.Operator("cos_deg", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(cos(Math.toRadians(v1.toDouble())))
                }
            })
            addOperator(object : Expression.Operator("tan_deg", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(tan(Math.toRadians(v1.toDouble())))
                }
            })
            addOperator(object : Expression.Operator("sin_rad", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(sin(v1.toDouble()))
                }
            })
            addOperator(object : Expression.Operator("cos_rad", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(cos(v1.toDouble()))
                }
            })
            addOperator(object : Expression.Operator("tan_rad", 1, true) {
                override fun eval(v1: BigDecimal, v2: BigDecimal): BigDecimal {
                    return BigDecimal.valueOf(tan(v1.toDouble()))
                }
            })
        }
        expression.addOperator(FactorialOperator())




        return expression.eval().toDouble()
    }

    // It is used to delete the last character in the input.
    fun backspaceButton() {
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart
        if (cursorPosition > 0) {
            val currentInput = inputEditText.text.toString()
            val newInput = currentInput.substring(0, cursorPosition - 1) + currentInput.substring(cursorPosition)
            inputEditText.setText(newInput)
            inputEditText.setSelection(cursorPosition - 1)
        }
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
    }

    fun pointButton(view: View) {
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart
        val currentInput = input.text.toString()

        if (currentInput.contains(Regex("\\.[0-9]*$"))) {
            // If it does, do not append another decimal point
            return
        }

        // If the last character is not a decimal point, append a decimal point
        if (currentInput.isNotEmpty() && currentInput.last() != '.') {
            inputEditText.text.insert(cursorPosition, ".")
        }
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)


    }

    fun percentButton(view: View) {
        // Append "/100" to the input when the divideBy100Button is clicked
        val currentInput = input.text.toString()

        // Check if the last character is a digit
        if (currentInput.isNotEmpty() && currentInput.last().isDigit()) {
            input.append("/100")
        }
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

    }

   fun scientistModeSwitchButton(view: View) {
        val scientistModeRow2 = findViewById<TableRow>(R.id.scientistModeRow2)
        val scientistModeRow3 = findViewById<TableRow>(R.id.scientistModeRow3)
        if (scientistModeRow2.visibility and scientistModeRow3.visibility == View.VISIBLE) {
            scientistModeRow2.visibility = View.GONE
            scientistModeRow3.visibility = View.GONE
            return

        } else {
            scientistModeRow2.visibility = View.VISIBLE
            scientistModeRow3.visibility = View.VISIBLE
        }
       soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

   }

    fun clearButton(view: View) {
        input.text = ""
        resultDisplay.text = ""
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)


    }

    fun formatScientific(number: Double): String {
        val numberInScientific = "%.2E".format(number) // Convert the number to scientific notation
        return if ("E" in numberInScientific) { // Check if the number is in scientific notation
            numberInScientific.replace("E", "x10^(") // Replace 'E' with 'x10^'
        } else {
            numberInScientific
        }

    }

    fun insertTextAtCursorPosition(text: String) {
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart
        inputEditText.text.insert(cursorPosition, text)
    }




}



