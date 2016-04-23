package org.nasaappschallenge2016.looneymoony.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.nasaappschallenge2016.looneymoony.backend.lmApi.LmApi;
import org.nasaappschallenge2016.looneymoony.backend.lmApi.model.FunFact;

import java.io.IOException;

/**
 * Created by vhblasco on 23/04/16.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static LmApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            LmApi.Builder builder = new LmApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.1.208:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {

            /* Concept test. The idea is return the Object */
            FunFact funFact = myApiService.getFanFact().execute();
            return funFact.getTitle();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        /* Concept Test. The idea is show the information in the activity */
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}