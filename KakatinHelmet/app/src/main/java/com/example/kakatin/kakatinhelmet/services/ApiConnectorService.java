package com.example.kakatin.kakatinhelmet.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kakatin.kakatinhelmet.models.BroadcastConstants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by lorenzo on 6.12.2015.
 */
public class ApiConnectorService extends IntentService {
    private final static String TAG = ApiConnectorService.class.getSimpleName();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("text; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ApiConnectorService(String name) {
        super(name);
    }
    public ApiConnectorService(){
        super("Fireeee");
    }

    @Override
    protected void onHandleIntent(Intent workIntent){
        String dataString = workIntent.getDataString();
        if(dataString == null){
            return;
        }
        try {
            getJSON(dataString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getJSON(String URI) throws Exception {
        Request request = new Request.Builder()
                        .url(URI)
                        .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e(TAG, "Response caught.");
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                String testi = "";
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.e(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                testi = response.body().string();
                Log.e(TAG, "Siis onko se oikeesti näiden välissä?");
                Log.e(TAG, response.body().string());
                ResponseBody body = response.body();
                Log.e(TAG, "Testiksi: " + testi);

                sendBroadCast(BroadcastConstants.BC_TEMP, testi);
            }
        });
    }

    //TODO: Make structure better for handling different kinds of broadcasts.
    private void sendBroadCast(String type, String data){
        Log.e(TAG, "Currently action is: " + type + " and data is " + data);

        Intent localIntent =
                new Intent(type)
                        .putExtra(BroadcastConstants.BC_TEMP_DATA, data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    public void notifyServer() throws Exception {
        String postBody = "";

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_JSON, postBody))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

}
