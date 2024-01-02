package com.example.audiooff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.widget.Toast
import com.example.audiooff2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var audioManager: AudioManager
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                // Implement speech recognition callbacks here.
            }

            override fun onBeginningOfSpeech() {
                // Implement speech recognition callbacks here.
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Implement speech recognition callbacks here.
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Implement speech recognition callbacks here.
            }

            override fun onEndOfSpeech() {
                // Implement speech recognition callbacks here.
            }

            override fun onError(error: Int) {
                // Implement speech recognition callbacks here.
            }

            override fun onResults(results: Bundle?) {
                // Implement speech recognition callbacks here.
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Implement speech recognition callbacks here.
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Implement speech recognition callbacks here.
            }
        }

        speechRecognizer.setRecognitionListener(recognitionListener)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Guardar el nombre en la caja de texto.
            val name = editText.text.toString()
            Toast.makeText(this, "Nombre guardado: $name", Toast.LENGTH_SHORT).show()
        }

        val callReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Detectar llamadas entrantes y comparar con el nombre guardado.
                // Mostrar el mensaje si coincide.
                val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    val name = editText.text.toString()
                    if (incomingNumber == name) {
                        Toast.makeText(this@MainActivity, "Llamada entrante de $name", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(callReceiver, intentFilter)
    }

    override fun onRequestPermissionsResult