package com.example.googlefblogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginFragment : Fragment(), View.OnClickListener {

    private val TAG = "LoginScreen"
    private val RC_SIGN_IN = 9001
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var signInLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(this.getString(R.string.server_client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInLayout = view.findViewById(R.id.layout_google)
        signInLayout.setOnClickListener(this)

        view.findViewById<TextView>(R.id.txt_forgot_password).setOnClickListener(this)
        view.findViewById<ConstraintLayout>(R.id.layout_continue_login).setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity().applicationContext)
        updateUI(account)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_forgot_password -> {
                findNavController().navigate(R.id.action_login_to_forgot)
            }

            R.id.layout_continue_login->{
                val intent = Intent(requireActivity(), UserActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            R.id.layout_google->{
                signIn()
            }
        }
    }

    private fun updateUI(@Nullable account: GoogleSignInAccount?) {
        if (account != null) {
            // Launch user operation activity activity
            signInLayout.setVisibility(View.GONE)

        } else {
            signInLayout.setVisibility(View.VISIBLE)
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // [START onActivityResult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    // [END onActivityResult]

    // [END onActivityResult]
    // [START handleSignInResult]
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            (activity as MainActivity).updateUI(account)
            Log.d(TAG, "signInResult:Login successful")
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Log.w(TAG, "signInResult:failed code=",e)
            updateUI(null)
        }
    }
}
