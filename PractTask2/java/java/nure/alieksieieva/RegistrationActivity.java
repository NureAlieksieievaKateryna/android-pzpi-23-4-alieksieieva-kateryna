package java.nure.alieksieieva;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (validateInputs(username, email, password)) {

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Valid email is required");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }
}