package com.advay.projectqpm

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.advay.projectqpm.databinding.ActivityMainBinding
import com.advay.projectqpm.homePage.MainHomePage
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    lateinit var pins: Array<EditText>
    private val correctPasscode = "1234"

    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pins = arrayOf(binding.passcode1, binding.passcode2, binding.passcode3, binding.passcode4)
        pins.forEachIndexed { index, editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editText.setBackgroundResource(R.drawable.dot_filled_partial)
                } else {
                    if (editText.text.isNotEmpty()) {
                        editText.setBackgroundResource(R.drawable.dot_filled)
                    } else {
                        editText.setBackgroundResource(R.drawable.dot_empty)
                    }
                }
            }

            editText.addTextChangedListener {
                if (it?.length == 1) {
                    editText.setTextColor(Color.TRANSPARENT)

                    if (index < pins.size - 1) {
                        pins[index + 1].requestFocus()
                    } else {
                        checkPin()
                    }
                }
            }

            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText.text.isEmpty() && index > 0) {
                        pins[index - 1].requestFocus()
                        pins[index - 1].setText("")
                    } else if (editText.text.isNotEmpty()) {
                        editText.setText("")
                    }
                }
                false
            }
        }

        MobileAds.initialize(this) {}

    }


    private fun checkPin() {
        val enteredPin = pins.joinToString("") { it.text.toString() }

        if (enteredPin == correctPasscode) {
            Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show()
            pins.forEach { it.clearFocus() }
            hideKeyboard(binding.passcode4)

            binding.pinLayoutContainer.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainHomePage()).commit()
        } else {
            Toast.makeText(this, "Wrong Pin", Toast.LENGTH_SHORT).show()
            resetAll()
        }
    }

    private fun resetAll() {
        pins.forEach {
            it.setText("")
            it.setBackgroundResource(R.drawable.dot_empty)
            it.setTextColor(Color.BLACK)
        }
        pins[0].requestFocus()
    }

    private fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}