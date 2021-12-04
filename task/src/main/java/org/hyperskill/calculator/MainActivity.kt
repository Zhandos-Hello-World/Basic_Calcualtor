package org.hyperskill.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var output:EditText? = null
    private var operator = false
    //4 operands + - * /
    private val operators = Array(4){false}
    private var num = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        output = findViewById(R.id.editText)

        //clear
        findViewById<Button>(R.id.clearButton).setOnClickListener {
            clear()
        }

        dot()
        operator()
        numbers()
        result()
    }
    private fun dot() {
        findViewById<Button>(R.id.dotButton).setOnClickListener {
            val current = output?.text.toString()
            if (current.trim().isEmpty()) {
                output?.append("0.")
            }
            else {
                output?.append(".")
            }
        }
    }

    private fun operator() {
        val operators = mutableListOf<Button>(
            findViewById(R.id.addButton),
            findViewById(R.id.subtractButton),
            findViewById(R.id.multiplyButton),
            findViewById(R.id.divideButton)
        )
        for (i in operators.withIndex()) {
            i.value.setOnClickListener {
                output?.hint = output?.text
                if (!operator) {
                    num = getValue()
                    output?.setText("")
                }
                setDefaultOperators()
                operator = true
                this.operators[i.index] = true
            }
        }
    }
    private fun result() {
        findViewById<Button>(R.id.equalButton).setOnClickListener {
            val num2 = output?.text.toString().toInt()
            when {
                operators[0] -> num += num2
                operators[1] -> num -= num2
                operators[2] -> num *= num2
                else -> num /= num2
            }
            output?.setText(format().toString())
        }
    }

    private fun numbers() {
        val btns = mutableListOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )
        for (i in btns) {
            i.setOnClickListener {
                if (operator) {
                    output?.setText("")
                }
                if (output?.text.toString() == "0") {
                    output?.setText("")
                }
                operator = false
                output?.append(i.text)
            }
        }
    }
    private fun setDefaultOperators() {
        for (t in this.operators.indices) {
            this.operators[t] = false
        }
    }
    private fun clear() {
        setDefaultOperators()
        num = 0.0
        output?.setText("")
        output?.hint = "0"
        operator = false
    }
    private fun format(): Number {
        if (num == num.toInt().toDouble()) {
            return num.toInt()
        }
        return num
    }
    private fun getValue(): Double {
        val res = output?.text.toString()
        return if (res.isEmpty()) {
            0.0
        } else {
            res.toDouble()
        }
    }
}
