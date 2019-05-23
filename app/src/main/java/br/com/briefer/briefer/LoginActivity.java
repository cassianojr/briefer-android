package br.com.briefer.briefer;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.JWTPayload;
import br.com.briefer.briefer.model.User;
import br.com.briefer.briefer.util.PreferencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        TextView signupText = findViewById(R.id.login_form_signup_text);
        signupText.getPaint().setUnderlineText(true);

        signupText.setOnClickListener(view -> {
            //Opening the signup activity
            Intent signupIntent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(signupIntent);
        });

        mPasswordView =findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {

            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {

            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            authenticate(user);

        }
    }

    private void authenticate(User user) {
        Call<JWTPayload> login = new RetrofitConfig().getUserService().loginUser(user);
        login.enqueue(new Callback<JWTPayload>() {
            @Override
            public void onResponse(@NonNull Call<JWTPayload> call, @NonNull Response<JWTPayload> response) {


                JWTPayload payload = response.body();
                if(payload != null){
                    PreferencesUtility.setLoggedIn(getApplicationContext(), true, payload.getToken(), payload.getId(), payload.getEmail());
                    Intent spashIntent = new Intent(LoginActivity.this, SplashActivity.class);
                    startActivity(spashIntent);
                    finish();
                }else{
                    if(response.code() == 401){
                        try {
                            //TODO check response for show correct error
                            assert response.errorBody() != null;
                            String strResponse = response.errorBody().string();

                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.requestFocus();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull  Call<JWTPayload> call, @NonNull Throwable t) {
                Log.e("PayloadService   ", "Error on loggin user: "+t.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

}

