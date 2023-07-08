package com.example.convert

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.convert.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var inputText: String = ""
    private lateinit var outputText: String
    private lateinit var selected: String
    private val map = mapOf(
        "Bin" to 2,
        "Oct" to 8,
        "Dec" to 10,
        "Hex" to 16
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make the app fullscreen I guess applied on lower API levels only.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()
    }

    private fun addCallBacks() {
        // You can use binding.apply{} to avoid repeating binding. every time
        binding.buttonDel.setOnClickListener { delClick() }
        binding.buttonBin.setOnClickListener { binClick() }
        binding.buttonDec.setOnClickListener { decClick() }
        binding.buttonOct.setOnClickListener { octClick() }
        binding.buttonHex.setOnClickListener { hexClick() }
        binding.select.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = parent?.getItemAtPosition(position).toString()

                binding.output.text = "0"
                binding.input.text = ""
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun View.displayInputText() {
        inputText = binding.input.text.toString() + value(this)
        binding.input.text = inputText
    }

    private fun displayInputTextHex(s: String) {
        inputText = binding.input.text.toString() + s
        binding.input.text = inputText
    }

    private fun displayOutputText() {
        binding.output.text = outputText
    }

    private fun value(view: View): String {
        val temp = (view as Button).text.toString()
        return if (map[selected]!! > temp.toInt()) temp else {
            // makeToast("Invalid Input")
            ""
        }
    }

    fun click(view: View) {
        //if (handleInputSize())
        view.displayInputText()
    }

    fun hexClick(view: View) {
        // handle input size for hex
        if (selected == "Hex" /* && binding.input.text.length < 7 */)
            displayInputTextHex((view as Button).text.toString())
//        else if (selected == "Hex")
//            makeToast("Max input size reached")
//        else
//            makeToast("Invalid Input")
    }

    private fun convertToDec(): Int {
        return if (selected != "Dec")
            Integer.parseInt(inputText, map[selected]!!)
        else inputText.toInt()
    }

    private fun binClick() {
        outputText = if (selected != "Bin" && inputText != "")
            Integer.toBinaryString(convertToDec())
        else
            inputText
        displayOutputText()
    }

    private fun decClick() {
        outputText = if (selected != "Dec" && inputText != "") {
            Integer.parseInt(inputText, map[selected]!!).toString()
            // convertToDec().toString()
        } else
            inputText
        displayOutputText()
    }

    private fun octClick() {
        outputText = if (selected != "Oct" && inputText != "")
            Integer.toOctalString(convertToDec())
        else
            inputText
        displayOutputText()
    }

    private fun hexClick() {
        outputText = if (selected != "Hex" && inputText != "")
            Integer.toHexString(convertToDec())
        else
            inputText
        displayOutputText()
    }

    private fun delClick() {
        inputText = inputText.dropLast(1)
        binding.input.text = inputText
        binding.output.text = "0"
    }

//    private fun makeToast(msg: String) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun handleInputSize(): Boolean {
//        val maxSize = when (selected) {
//            "Bin" -> 31
//            "Oct" -> 10
//            "Dec" -> 9
//            "Hex" -> 7
//            else -> 7
//        }
//        return if (maxSize == inputText.length) {
//            makeToast("Max input size reached")
//            false
//        } else {
//            true
//        }
//    }
}
