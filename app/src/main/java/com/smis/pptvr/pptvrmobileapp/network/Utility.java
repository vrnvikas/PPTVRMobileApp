package com.smis.pptvr.pptvrmobileapp.network;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.smis.pptvr.pptvrmobileapp.ActionBroadcastReceiver;
import com.smis.pptvr.pptvrmobileapp.CustomChromeTabs.CustomTabActivityHelper;
import com.smis.pptvr.pptvrmobileapp.CustomChromeTabs.WebviewFallback;
import com.smis.pptvr.pptvrmobileapp.R;
import com.smis.pptvr.pptvrmobileapp.pojo.Projects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vikas Kumar on 01-02-2017.
 */

public class Utility {


    public static WebService createAppWebService(final String token) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("x-access-token", token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        WebService service = retrofit.create(WebService.class);
        return service;
    }


    public static void openCustomTab(Context context, String link) {

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        //intentBuilder.setToolbarColor(Color.parseColor(String.valueOf(R.color.colorPrimary)));
        //intentBuilder.setSecondaryToolbarColor(Color.parseColor(String.valueOf(R.color.colorPrimary)));

        //Generally you do not want to decode bitmaps in the UI thread. Decoding it in the
        //UI thread to keep the example short.
        String actionLabel = context.getString(R.string.label_action);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_menu_share);
        PendingIntent pendingIntent = createPendingIntent(ActionBroadcastReceiver.ACTION_ACTION_BUTTON, context);
        intentBuilder.setActionButton(icon, actionLabel, pendingIntent);

        intentBuilder.addDefaultShareMenuItem();
        intentBuilder.setShowTitle(true);

        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabActivityHelper.openCustomTab(
                (Activity) context, intentBuilder.build(), Uri.parse(link), new WebviewFallback());
    }


    public static PendingIntent createPendingIntent(int actionSourceId, Context context) {
        Intent actionIntent = new Intent(
                context.getApplicationContext(), ActionBroadcastReceiver.class);
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSourceId);
        return PendingIntent.getBroadcast(
                context.getApplicationContext(), actionSourceId, actionIntent, 0);
    }

    public static void shareWebLink(Context context, String webLink) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "WebLink PPTVR");
        i.putExtra(android.content.Intent.EXTRA_TEXT, webLink);
        context.startActivity(Intent.createChooser(i, "Share via"));
    }

    public static List<Projects> bubblesort(List<Projects> projectsList, String filter) {

        final List<Projects> filteredModelList = new ArrayList<>();

        for(int i=0;i<projectsList.size();i++){
            filteredModelList.add(projectsList.get(i));
        }

        int min;
        if(filter.equals("most_starred")){
            for (int i = 0; i < filteredModelList.size() - 1; i++) {
                min = i;
                for (int j = i + 1; j < filteredModelList.size(); j++) {

                    if (filteredModelList.get(j).getStars() < filteredModelList.get(min).getStars()) {

                        min = j;

                    }

                }

                if (i != min) {
                    Projects tmpProject = filteredModelList.get(i);
                    filteredModelList.set(i, filteredModelList.get(min));
                    filteredModelList.set(min, tmpProject);
                }

            }
        }if(filter.equals("most_viewed")) {
            for (int i = 0; i < filteredModelList.size() - 1; i++) {
                min = i;
                for (int j = i + 1; j < filteredModelList.size(); j++) {

                    if (filteredModelList.get(j).getViews() < filteredModelList.get(min).getViews()) {

                        min = j;

                    }

                }

                if (i != min) {
                    Projects tmpProject = filteredModelList.get(i);
                    filteredModelList.set(i, filteredModelList.get(min));
                    filteredModelList.set(min, tmpProject);
                }

            }
        }else {

        }

        return filteredModelList;
    }

}
