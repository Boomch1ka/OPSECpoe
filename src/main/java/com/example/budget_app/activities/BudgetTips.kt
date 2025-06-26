package com.example.budget_app.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.budget_app.R

class BudgetTips : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_tips)

        val tipsView = findViewById<TextView>(R.id.tipsText)
        val everydayButton = findViewById<Button>(R.id.everydayTipsButton)
        val studentButton = findViewById<Button>(R.id.studentTipsButton)

        everydayButton.setOnClickListener {
            tipsView.text = """
                💼 Everyday Budget Tips:

                1. Set monthly spending goals and track them weekly.
                2. Automate bill payments to avoid late fees.
                3. Plan meals and shop with a grocery list—impulse buys drain cash.
                4. Review subscriptions—cut those you rarely use.
                5. Round up daily purchases and drop the change into savings.
                6. Schedule a weekly “money check-in” with yourself or your partner.

                A mindful budget is your roadmap to peace of mind.
            """.trimIndent()
        }

        studentButton.setOnClickListener {
            tipsView.text = """
                🎓 Student Budget Tips:

                1. Always ask for student discounts—movies, tech, even food.
                2. Avoid credit card traps—use debit or cash if possible.
                3. Carpool or bike to reduce transport costs.
                4. Buy second-hand textbooks or use the library.
                5. Use apps to track allowance, bursaries, and side hustle income.
                6. Learn to cook basic meals—you’ll save more than you think.

                Build smart habits now, reap financial freedom later.
            """.trimIndent()
        }
    }
}