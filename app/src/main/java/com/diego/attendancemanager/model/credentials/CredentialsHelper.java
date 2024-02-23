package com.diego.attendancemanager.model.credentials;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class CredentialsHelper {
    Context context;
    RequestQueue queue;

    public CredentialsHelper(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public CompletableFuture<Boolean> onCredentials(String url, HashMap<String, String> data, AtomicReference<JSONObject> userData)
    {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(data),
                response -> {
                    try
                    {
                        String status = response.getString("status");
                        Log.d("MyRequisitionStatus", status);

                        if(Objects.equals(status, "failed"))
                        {
                            if(response.has("error"))
                            {
                                Toast.makeText(context.getApplicationContext(), response.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            JSONObject responseData = new JSONObject();
                            responseData.put("userEmail", response.getString("userId"));
                            if(response.has("accessLvl"))
                            {
                                responseData.put("accessLvl", response.getString("accessLvl"));
                            }

                            Log.d("MyRequisitionData", String.valueOf(responseData));
                            userData.set(responseData);
                            future.complete(true);

                        }
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                        future.complete(false);
                    }
                },
                error -> {
                    Log.e("MyRequisitionError", error.toString());
                    future.complete(false);
                }
        );

        queue.add(request);
        return future;
    }
}
