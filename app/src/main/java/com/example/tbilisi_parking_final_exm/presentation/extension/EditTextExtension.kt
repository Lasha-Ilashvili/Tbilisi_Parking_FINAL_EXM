package com.example.tbilisi_parking_final_exm.presentation.extension

import android.widget.EditText
import com.example.tbilisi_parking_final_exm.presentation.common.adapter.CardInputAdapter


fun EditText.applyFormatting(symbol: String, separator: Int) {
    val cardInputAdapter = CardInputAdapter(editText = this, symbol = symbol, separator = separator)
    addTextChangedListener(cardInputAdapter)
}