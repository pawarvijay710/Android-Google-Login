package com.example.googlefblogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }

    fun updateUI(@Nullable account: GoogleSignInAccount?) {
        if (account != null) {
            // not able to logout error message
            Toast.makeText(this, "Not able to logout, something went wrong!!", Toast.LENGTH_SHORT)

        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}