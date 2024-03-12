package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class CardInputAdapter(
    private val editText: EditText,
    private val symbol: String,
    private val separator: Int
) : TextWatcher {

    override fun afterTextChanged(editable: Editable?) {
        // Removes any previous symbols and spaces
        val cleanedString = editable.toString().replace(symbol, "")
        val formattedString = StringBuilder()

        for (index in cleanedString.indices) {
            if (index % separator == 0 && index > 0) {
                formattedString.append(symbol)
            }
            formattedString.append(cleanedString[index])
        }

        editText.also {
            it.removeTextChangedListener(this)
            it.setText(formattedString.toString())
            it.setSelection(formattedString.length)
            it.addTextChangedListener(this)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}