package com.example.googlefblogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    fun updateUI(@Nullable account: GoogleSignInAccount?) {
        findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
        findViewById<View>(R.id.main_layout).visibility = View.GONE

        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
            if (account != null) {
                //Launch user operation activity
                val intent = Intent(this, UserActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                findViewById<View>(R.id.main_layout).visibility = View.VISIBLE
            }
        }, 1000)
    }
}