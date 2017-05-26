package serializerteam.serializer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.adapter.WidgetListAdapter;
import serializerteam.serializer.adapter.WidgetService;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.database.ShowsDbAdapter;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.ShowDto;

import static java.util.Collections.min;

/**
 * Implementation of App Widget functionality.
 */
public class NextShowWidget extends AppWidgetProvider {


    private static ListView listView ;
    private static ArrayAdapter<String> adapter ;
    private static volatile int completedTasks = 0;
    public static final HashMap<String,ShowDto> list = new HashMap<>();

    public static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        ShowsDbAdapter showsDbAdapter = new ShowsDbAdapter(context).getDbContext();
        final List<Integer> showIds = showsDbAdapter.getFavouriteShowsIds();
        showsDbAdapter.closeDbContext();

        Call<ShowDto>[] tasks = new Call[showIds.size()];


        for (int i = 0; i < showIds.size(); i++) {
            tasks[i] = ApiSettings.showsApiService.getShow(showIds.get(i));
            tasks[i].enqueue(new Callback<ShowDto>() {
                @Override
                public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
                    if (response.body() != null) {
                        getShows(response.body(), context, appWidgetManager, appWidgetId, showIds.size());
                    }
                }

                @Override
                public void onFailure(Call<ShowDto> call, Throwable t) {
                    Log.e("ERR", t.toString());
                }
            });
        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void updateWidgetData(Context context, int[] ids, AppWidgetManager appWidgetManager) {
        //TODO - refactor this to handle list of shows
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.next_show_widget);
            String e=min(list.keySet());
            views.setTextViewText(R.id.appwidget_text, list.get(e).getName());
            views.setTextViewText(R.id.widget_next_episode, e);

            String imageUri = "";
            if (list.get(e).getImage() != null)
                imageUri =list.get(e).getImage().values().iterator().next();
            if (!imageUri.isEmpty()) {
                Picasso.with(context).load(imageUri).into(views, R.id.widget_image, ids);
            } else {
                //Picasso.with(context).load(imageUrl).into(views, R.id.widget_image, ids); // tu się wywala pusty URL
            }


//        listView = (ListView) LayoutInflater.from(context).inflate(R.layout.next_show_widget,null).findViewById(R.id.listView);
//        String[] gupiaLista = new String[2*list.size()];
//        int counter=0;
//        for(ShowDto e : list.keySet()){
//            gupiaLista[counter]=e.getName();
//            gupiaLista[list.size()+counter]=list.get(e);
//            counter++;
//        }
//        adapter = new WidgetListAdapter(context,gupiaLista);
//        listView.setAdapter(adapter);
//       // views.setRe
//        views.setRemoteAdapter(ids[0],R.id.listView,new Intent(context,WidgetService.class));

       // views.addView(R.id.listView,listView);
        appWidgetManager.updateAppWidget(ids[0], views);
    }

    private static void getShows(final ShowDto showDto, final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final int size) {
        final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (showDto.getLinks().get("nextepisode") != null && showDto.getLinks().get("nextepisode").href.length() > 0) {
            ApiSettings.urlApi.getResponse(showDto.getLinks().get("nextepisode").href).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        EpisodeDto episode = new Gson().fromJson(response.body().charStream(), EpisodeDto.class);
                        String date;
                        if(!episode.getAirtime().isEmpty()) {

                            date = episode.getAirdate() + " " + episode.getAirtime();
                            //date = simpleDateTimeFormat.parse(date);
                        }
                        else {
                            date = episode.getAirdate();
                            //date = simpleDateFormat.format(date);
                        }
                        list.put( date,showDto);
                        completedTasks++;
                        if (completedTasks == size) {
                            int[] ids = new int[1];
                            ids[0] = appWidgetId;
                            //CharSequence widgetText = context.getString(R.string.appwidget_text);
                           // CharSequence nextEpisode = "Next episode: " + list.values().toArray()[0];
                          //  String imageUrl = "";

                            updateWidgetData(context, ids, appWidgetManager);
                        }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("WIDGET","Can not get show.");
                        completedTasks++;
                }
            });
        }
        else {
            completedTasks++;
            //list.put(showDto, null);
            if (completedTasks == size) {
                int[] ids = new int[1];
                ids[0] = appWidgetId;
                updateWidgetData(context, ids, appWidgetManager);
            }
        }
    }
}

