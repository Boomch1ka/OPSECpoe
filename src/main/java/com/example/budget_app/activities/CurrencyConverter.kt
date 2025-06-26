package com.example.budget_app.activities

import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.budget_app.R
import com.google.android.material.navigation.NavigationView

class CurrencyConverter : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_converter)

        val toolbar: Toolbar = findViewById(R.id.currency_toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.currency_drawer_layout)
        navView = findViewById(R.id.currency_nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val resultView = findViewById<TextView>(R.id.resultText)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val currencySpinner = findViewById<Spinner>(R.id.currencySpinner)

        val currencies = listOf("Select Currency", "US Dollar (USD)", "British Pound (GBP)", "Japanese Yen (JPY)")
        val exchangeRates = mapOf(
            "US Dollar (USD)" to 18.5,
            "British Pound (GBP)" to 23.7,
            "Japanese Yen (JPY)" to 0.13
        )

        currencySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)

        convertButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            val selected = currencySpinner.selectedItem.toString()
            val rate = exchangeRates[selected]

            if (amount != null && rate != null) {
                val converted = amount * rate
                resultView.text = "Converted: R%.2f".format(converted)
            } else {
                Toast.makeText(this, "Please enter a valid amount and select a currency", Toast.LENGTH_SHORT).show()
            }
        }
    }
}