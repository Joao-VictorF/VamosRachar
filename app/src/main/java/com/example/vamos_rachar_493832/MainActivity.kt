package com.example.vamos_rachar_493832

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat
import android.util.Log
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val qtd = findViewById<EditText>(R.id.qtd)
        val valor = findViewById<EditText>(R.id.valor)
        val result = findViewById<TextView>(R.id.result)

        qtd.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("PDM23","qtd.setOnFocusChangeListener, ${!qtd.text.isNullOrEmpty() && !valor.text.isNullOrEmpty()}")
                if (!qtd.text.isNullOrEmpty() && !valor.text.isNullOrEmpty()) {
                    calculate();
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

        valor.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("PDM23","valor.setOnFocusChangeListener, ${!qtd.text.isNullOrEmpty() && !valor.text.isNullOrEmpty()}")
                if (!qtd.text.isNullOrEmpty() && !valor.text.isNullOrEmpty()) {
                    calculate();
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

        val buttonShare = findViewById<ImageButton>(R.id.share)
        val buttonSay = findViewById<ImageButton>(R.id.say)

        buttonShare.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                val shareResult = result.text.toString()
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Fica $shareResult reais pra cada!")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        buttonSay.setOnClickListener {
            val shareResult = result.text.toString()
            tts = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
                tts.language = Locale("pt", "BR")
                if (status != TextToSpeech.ERROR) {
                    tts.language = Locale.getDefault()
                    tts.speak("Fica $shareResult reais pra cada!", TextToSpeech.QUEUE_FLUSH, null, null)
                }
            })
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        tts.shutdown()
    }

    private fun calculate() {
        val qtd = findViewById<EditText>(R.id.qtd)
        val valor = findViewById<EditText>(R.id.valor)
        val result = findViewById<TextView>(R.id.result)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        val value = valor.text.toString().toFloat();
        val quantity = qtd.text.toString().toFloat();
        val calculo = df.format((value/quantity).toBigDecimal())
        Log.d("PDM23","qtd values, $value $quantity $calculo");

        result.text = calculo.toString()
    }

}
