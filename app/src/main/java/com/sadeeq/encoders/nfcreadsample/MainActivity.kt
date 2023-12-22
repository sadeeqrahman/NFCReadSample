package com.sadeeq.encoders.nfcreadsample

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var cardNumber:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        cardNumber = findViewById(R.id.cardNumber)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please enable NFC in device settings", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TECH_DISCOVERED == action) {
            // Handle the NFC card data here
            val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as? android.nfc.Tag
            // Example: Read the UID of the card
            val uid = tag!!.id
            val uidString = bytesToHexString(uid)
            cardNumber.text = "Card UID: $uidString"
            Toast.makeText(this, "Card UID: $uidString", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bytesToHexString(bytes: ByteArray): String {
        val builder = StringBuilder()
        for (b in bytes) {
            builder.append(String.format("%02x", b))
        }
        return builder.toString()
    }
}