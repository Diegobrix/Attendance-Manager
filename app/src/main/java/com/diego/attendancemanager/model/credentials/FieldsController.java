package com.diego.attendancemanager.model.credentials;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class FieldsController {
    Context context;
    public FieldsController(Context context)
    {
        this.context = context;
    }

    public static String checkFields(EditText[] fields)
    {
        for(EditText field : fields)
        {
            String fieldData = field.getText().toString();
            if(!fieldData.matches(".*\\S+.*"))
            {
                return "Todos os Campos Devem ser Preenchidos!";
            }
        }

        return "Ok";
    }

    public static String checkFields(EditText[] fields, EditText[] passConfirmation)
    {
        if(!Objects.equals(passConfirmation[0].getText().toString(), passConfirmation[1].getText().toString()))
        {
            return "As Senhas Devem Coincidir!";
        }

        return checkFields(fields);
    }

    public boolean showCheckResult(String msg)
    {
        if(!Objects.equals(msg, "Ok"))
        {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
