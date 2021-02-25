package chavales.los.practica1android.actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import chavales.los.practica1android.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button boton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        boton = findViewById(R.id.login);
        boton.setOnClickListener(v -> {

            String email = username.getText().toString();
            String passw = password.getText().toString();

            if (email.equals("") || passw.equals("")) {
                Toast.makeText(this, "Rellena todos los campos, por favor", Toast.LENGTH_LONG).show();
            } else if (!email.matches("\\w+@\\w+[.]\\w+")) {
                Toast.makeText(this, "El e-mail está incorrectamente formateado", Toast.LENGTH_LONG).show();
            } else if (passw.length() < 6) {
                Toast.makeText(this, "La contraseña debe tener, al menos, 6 caracteres", Toast.LENGTH_LONG).show();
            } else {
                // credenciales bien

                Task<AuthResult> task = auth.signInWithEmailAndPassword(email, passw);

                task.addOnSuccessListener(r -> { // void fun(TResult r) {}
                    lanzarMainActivity();
                });

                task.addOnFailureListener(r ->
                    auth.createUserWithEmailAndPassword(email, passw).addOnCompleteListener(resul -> {
                            if (resul.isSuccessful()) {
                                lanzarMainActivity();
                            } else {
                                Toast.makeText(this, "Algo ha ido mal.\n" + resul.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    )
                );
            }
        });

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            lanzarMainActivity();
        }
    }

    private void lanzarMainActivity() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
}