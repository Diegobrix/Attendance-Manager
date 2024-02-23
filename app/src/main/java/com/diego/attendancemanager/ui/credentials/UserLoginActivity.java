package com.diego.attendancemanager.ui.credentials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.diego.attendancemanager.R;
import com.diego.attendancemanager.model.Endpoints;
import com.diego.attendancemanager.model.credentials.CredentialsHelper;
import com.diego.attendancemanager.model.credentials.FieldsController;
import com.diego.attendancemanager.ui.MainActivity;
import com.diego.attendancemanager.ui.credentials.register.UserRegisterActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class UserLoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPass;
    Button btnLogin, btnChangeCredentialsMethod;
    CredentialsHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);

        btnLogin = findViewById(R.id.btnLogin);
        btnChangeCredentialsMethod = findViewById(R.id.btnChangeCredentialsMethod);

        helper = new CredentialsHelper(this);

        btnLogin.setOnClickListener(this::onLogin);
        btnChangeCredentialsMethod.setOnClickListener(this::changeCredentialsMethod);
    }

    private void onLogin(View view)
    {
        FieldsController fieldsController = new FieldsController(this);
        if(fieldsController.showCheckResult(FieldsController.checkFields(new EditText[]{edtEmail, edtPass})))
        {
            AtomicReference<JSONObject> responseData = new AtomicReference<>(new JSONObject());
            helper.onCredentials(Endpoints.userLogin, setData(), responseData)
                    .thenAccept(response -> {
                        if(response)
                        {
                            Log.i("MyRequisitionReturn", String.valueOf(responseData.get()));
                            goToMain(responseData.get());
                        }
                    });
        }
    }

    private void changeCredentialsMethod(View view)
    {
        clearFields();

        Intent changeMethod = new Intent(this, UserRegisterActivity.class);
        startActivity(changeMethod);
    }

    private HashMap<String, String> setData()
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("userEmail", edtEmail.getText().toString());
        data.put("userPass", edtPass.getText().toString());

        return data;
    }

    private void goToMain(JSONObject data)
    {
        String userId = data.optString("userEmail");

        Intent homePage = new Intent(this, MainActivity.class);
        startActivity(homePage);
        this.finish();
    }

    private void clearFields()
    {
        edtEmail.setText("");
        edtPass.setText("");
    }
}