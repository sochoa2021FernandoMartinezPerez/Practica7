package net.iesseveroochoa.fernandomartinezperez.practica7;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.foundation.layout.Direction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.Query;

import net.iesseveroochoa.fernandomartinezperez.practica7.adapter.ChatAdapter;
import net.iesseveroochoa.fernandomartinezperez.practica7.model.Mensaje;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comprobarAutenticacion();


    }

    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    result -> onSignInResult(result)
            );


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {

            startActivity(new Intent(this, InicioAppActivity.class));
        } else {

            String msg_error = "";
            if (response == null) {

                msg_error = "Es necesario autenticarse";
            } else if (response.getError().getErrorCode() ==
                    ErrorCodes.NO_NETWORK) {
                msg_error = "No hay red disponible para autenticarse";
            } else {
                msg_error = "Error desconocido al autenticarse";
            }
            Toast.makeText(
                    this,
                    msg_error,
                    Toast.LENGTH_LONG)
                    .show();
        }
        finish();
    }

    public void getConferenciaActual(){

    }

    public void createSignInIntent() {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.logo)
                .setIsSmartLockEnabled(false)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void comprobarAutenticacion() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, InicioAppActivity.class));
        } else {
            createSignInIntent();
        }
    }




}