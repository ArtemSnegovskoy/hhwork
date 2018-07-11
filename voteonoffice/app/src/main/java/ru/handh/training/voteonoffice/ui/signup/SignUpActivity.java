package ru.handh.training.voteonoffice.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.login.LogInActivity;

public class SignUpActivity extends BaseActivity implements View.OnClickListener, SignUpMvpView {

    @Inject
    SignUpPresenter signUpPresenter;

    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword;

    // private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_sign_up);

        signUpPresenter.attachView(this);
        editTextEmail = findViewById(R.id.editTextEmailsignUp);
        editTextPassword = findViewById(R.id.editTextPasswordsignUp);
        progressBar = findViewById(R.id.progressbar);

        // firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        signUpPresenter.detachView();
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                signUpPresenter.registerUser(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
                // registerUser();
                //signUpPresenter.addDbUser(editTextEmail.getText().toString().trim());
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }

    // MVP методы
    @Override
    public void showEmailEmptyError() {

        editTextEmail.setError(getText(R.string.error_need_email));
        editTextEmail.requestFocus();

    }

    @Override
    public void showEmailIncorrectError() {
        editTextEmail.setError(getText(R.string.error_valid_email));
        editTextEmail.requestFocus();
    }

    @Override
    public void showPasswordEmptyError() {
        editTextPassword.setError(getText(R.string.error_need_password));
        editTextPassword.requestFocus();
    }

    @Override
    public void showPasswordLenghtError() {
        editTextPassword.setError(getText(R.string.error_password_lenght));
        editTextPassword.requestFocus();
    }

    @Override
    public void showAlredyRegisterError() {
        Toast.makeText(getApplicationContext(), getText(R.string.error_already_registered), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSignUpSuccessful() {
        Toast.makeText(getApplicationContext(), getText(R.string.registration_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSignUpFailed(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showPasswordNoLetterError() {
        editTextPassword.setError(getText(R.string.error_letter_contain));
        editTextPassword.requestFocus();
    }

    @Override
    public void showPasswordOnlyLowerCaseError() {
        editTextPassword.setError(getText(R.string.error_upper_case));
        editTextPassword.requestFocus();
    }

    @Override
    public void showPasswordOnlyUpperCaseError() {
        editTextPassword.setError(getText(R.string.error_lower_case));
        editTextPassword.requestFocus();
    }
}



