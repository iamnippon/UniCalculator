package com.zxdeveloper.myapplication


import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
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
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


class MainActivity : AppCompatActivity() {

    private val piButton: Button by lazy { findViewById<Button>(id.piButton) }
    private val squareButton: Button by lazy { findViewById<Button>(id.squareButton) }
    private val clearButton: Button by lazy { findViewById<Button>(id.clearButton) }
    private val scientistModeSwitchButton: ImageButton by lazy { findViewById<ImageButton>(id.scientistModeSwitchButton) }
    private val factorialButton: Button by lazy { findViewById<Button>(id.factorialButton) }
    private val logarithmButton: Button by lazy { findViewById<Button>(id.logarithmButton) }
    private val naturalLogarithmButton: Button by lazy { findViewById<Button>(id.naturalLogarithmButton) }
    private val eButton: Button by lazy { findViewById<Button>(id.eButton) }
    private val exponentButton: ImageButton by lazy { findViewById<ImageButton>(id.exponentButton) }
    private val invButton: Button by lazy { findViewById<Button>(id.invButton) }
    private val tangentButton: Button by lazy { findViewById<Button>(id.tangentButton) }
    private val cosineButton: Button by lazy { findViewById<Button>(id.cosineButton) }
    private val sineButton: Button by lazy { findViewById<Button>(id.sineButton) }
    private val degreeButton: Button by lazy { findViewById<Button>(id.degreeButton) }
    private val percentButton: Button by lazy { findViewById<Button>(id.divideBy100Button) }
    private val pointButton: ImageButton by lazy { findViewById<ImageButton>(id.pointButton) }
    private val backspaceButton: ImageButton by lazy { findViewById<ImageButton>(id.backspaceButton) }
    private val subtractButton: Button by lazy { findViewById<Button>(id.subtractButton) }
    private val addButton: Button by lazy { findViewById<Button>(id.addButton) }
    private val multiplyButton: Button by lazy { findViewById<Button>(id.multiplyButton) }
    private val divideButton: Button by lazy { findViewById<Button>(id.divideButton) }
    private val input: TextView by lazy { findViewById<EditText>(id.input) }
    private val parenthesesButton: Button by lazy { findViewById<Button>(id.parenthesesButton) }
    private val leftParenthesisButton: Button by lazy { findViewById<Button>(id.leftParenthesisButton) }
    private val rightParenthesisButton: Button by lazy { findViewById<Button>(id.rightParenthesisButton) }
    private val resultDisplay: TextView by lazy { findViewById<TextView>(id.resultDisplay) }
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private var isDegreeMode = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        soundId = soundPool.load(this, R.raw.keyboard_sound, 1)

        divideButton.setOnClickListener { operatorButton(it) }
        multiplyButton.setOnClickListener { operatorButton(it) }
        subtractButton.setOnClickListener { operatorButton(it) }
        addButton.setOnClickListener { operatorButton(it) }
        backspaceButton.setOnClickListener { backspaceButton() }
        pointButton.setOnClickListener { pointButton(it) }
        percentButton.setOnClickListener { percentButton(it) }
        clearButton.setOnClickListener { clearButton(it) }
        scientistModeSwitchButton.setOnClickListener { scientistModeSwitchButton(it) }

        degreeButton.setOnClickListener {
            isDegreeMode = !isDegreeMode
            if (isDegreeMode) {
                degreeButton.text = "DEG"
            } else {
                degreeButton.text = "RAD"
            }
        }
        sineButton.setOnClickListener {
            playSound()
            input.append("sin(")
        }
        cosineButton.setOnClickListener {
            playSound()
            input.append("cos(")
        }
        tangentButton.setOnClickListener {
            playSound()
            input.append("tan(")
        }
        invButton.setOnClickListener {
            playSound()
            input.append("^(-1)")
        }
        exponentButton.setOnClickListener {
            playSound()
            input.append("^(")
        }
        eButton.setOnClickListener {
            playSound()
            input.append("e")
        }
        naturalLogarithmButton.setOnClickListener {
            playSound()
            input.append("ln(")
        }
        logarithmButton.setOnClickListener {
            playSound()
            input.append("log(")
        }
        factorialButton.setOnClickListener {
            playSound()
            input.append("!")
        }
        squareButton.setOnClickListener {
            playSound()
            input.append("sqrt(")
        }
        piButton.setOnClickListener {
            playSound()
            input.append("3.1416")
        }

        input.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
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

        input.showSoftInputOnFocus = false
    }


    fun parenthesesButton(view: View) {
        // Show the leftParenthesisButton and rightParenthesisButton when parenthesesButton is clicked
        leftParenthesisButton.visibility = View.VISIBLE
        rightParenthesisButton.visibility = View.VISIBLE
        playSound()
    }

    fun leftParenthesisButton(view: View) {
        // Append "(" to the input and hide the leftParenthesisButton and rightParenthesisButton
        val input = findViewById<EditText>(R.id.input)
        val cursorPosition = input.selectionStart

        input.text.insert(cursorPosition, "(")

        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
        playSound()
    }

    fun rightParenthesisButton(view: View) {
        // Append ")" to the input and hide the leftParenthesisButton and rightParenthesisButton
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart

        inputEditText.text.insert(cursorPosition, ")")
        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
        playSound()
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

        if (operator == "×") {
            operator = "*"
        } else if (operator == "÷") {
            operator = "/"
        }

        // Check if the cursor is not at the start and the character before the cursor is a digit or a closing bracket
        if (cursorPosition > 0 && (currentInput[cursorPosition - 1].isDigit() || currentInput[cursorPosition - 1] == ')')) {
            // Insert the operator at the cursor position
            inputEditText.text.insert(cursorPosition, operator)
        }

        playSound()
    }


    // controls the button clicked and display the value
    fun keyDigitPadMappingToDisplay(view: View) {
        val button = view as Button
        val inputEditText = findViewById<EditText>(R.id.input)
        val cursorPosition = inputEditText.selectionStart
        inputEditText.text.insert(cursorPosition, button.text)
        playSound()
    }

    fun equalsButton(view: View) = lifecycleScope.launch {
        var currentInput = input.text.toString()

        // Count the number of open and closed parentheses
        val openParentheses = currentInput.count { it == '(' }
        val closedParentheses = currentInput.count { it == ')' }


        // Check if the input is only an operator or a function without a number
        if (!isValidExpression(currentInput)) {
            Toast.makeText(this@MainActivity, "Invalid input", Toast.LENGTH_SHORT).show()
            return@launch
        }

        // If there are more open parentheses than closed parentheses, append the necessary number of closed parentheses
        if (openParentheses > closedParentheses) {
            currentInput += ")".repeat(openParentheses - closedParentheses)
        }
        if (input.text.isEmpty()) {
            parenthesesButton.visibility = View.VISIBLE
            leftParenthesisButton.visibility = View.GONE
            rightParenthesisButton.visibility = View.GONE
            return@launch

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
        playSound()
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
            val newInput = currentInput.substring(0, cursorPosition - 1) + currentInput.substring(
                cursorPosition
            )
            inputEditText.setText(newInput)
            inputEditText.setSelection(cursorPosition - 1)
        }
        playSound()
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
        playSound()
    }

    fun percentButton(view: View) {
        // Append "/100" to the input when the divideBy100Button is clicked
        val currentInput = input.text.toString()

        // Check if the last character is a digit
        if (currentInput.isNotEmpty() && currentInput.last().isDigit()) {
            input.append("/100")
        }
        playSound()
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
        playSound()
    }

    fun clearButton(view: View) {
        input.text = ""
        resultDisplay.text = ""
        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

        // Reset the visibility of the parentheses buttons
        parenthesesButton.visibility = View.VISIBLE
        leftParenthesisButton.visibility = View.GONE
        rightParenthesisButton.visibility = View.GONE
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

    // This function checks if the input is a valid mathematical expression
    fun isValidExpression(input: String): Boolean {
        // Check if the input ends with an operator
        if (input.matches(Regex(".*[+\\-*/%^]$"))) {
            return false
        }

        // Check if there are any operators without operands
        if (input.contains("()")) {
            return false
        }

        // Check if there are any operators followed by a closing parenthesis
        if (input.contains(Regex("[+\\-*/%^]\\)"))) {
            return false
        }

        // Check if there are any unmatched parentheses
        val openParentheses = input.count { it == '(' }
        val closedParentheses = input.count { it == ')' }
        if (openParentheses != closedParentheses) {
            return false
        }

        if (input.contains("^(-1)^(-1)")) {
            return false
        }

        return true
    }

    fun openAppMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.app_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_menu_about_button -> {
                    openAbout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    fun openAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

//  play sound when button is clicked
    fun playSound() {
    soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
    }



}




