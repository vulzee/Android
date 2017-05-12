package serializerteam.serializer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.api.StatusCodes;
import serializerteam.serializer.dto.CastDto;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.dto.UserShowDto;
import serializerteam.serializer.model.cast.CastListAdapter;

public class ShowDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CastDto> list;
    private ShowDto showDto;
    private EpisodeDto episode;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isFavourite = false;
    private String userId;
    Activity activity = this;

    private static final String EXTRA_IMAGE = "extra.image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_show_details);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int showId = getIntent().getIntExtra("show_id", 0);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        userId = getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null);

        final FloatingActionButton starButton = (FloatingActionButton) findViewById(R.id.star_fab);
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClickHandler(starButton);
            }
        });

        FloatingActionButton shareButton = (FloatingActionButton) findViewById(R.id.share_fab);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        checkIfInFavourites(starButton, showId);

        list = new ArrayList<>();
        ApiSettings.showsApiService.getShowWithCast(showId).enqueue(new Callback<ShowDto>() {
            @Override
            public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
                if (response.body() != null) {

                    showDto = response.body();
                    collapsingToolbarLayout.setTitle(showDto.getName());
                    if (showDto.getSummary() != null)
                        ((TextView) findViewById(R.id.item_long_description)).setText(Jsoup.parse(showDto.getSummary()).text());
                    if (showDto.getImage() != null && showDto.getImage().size() > 0)
                        Picasso.with(ShowDetailsActivity.this).load(showDto.getImage().values().iterator().next()).into((ImageView) findViewById(R.id.toolbar_image), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap bitmap = ((BitmapDrawable) ((ImageView) findViewById(R.id.toolbar_image)).getDrawable()).getBitmap();
                                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        supportStartPostponedEnterTransition();
                                    }
                                });
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    else
                        Picasso.with(ShowDetailsActivity.this).load(R.mipmap.ic_launcher).into((ImageView) findViewById(R.id.toolbar_image));
                    list.addAll(showDto.getEmbedded().getCast());
                    setCastListAdapter();
                    if (showDto.getLinks().get("nextepisode") != null && showDto.getLinks().get("nextepisode").href.length() > 0)
                        getNextEpisode(showDto.getLinks().get("nextepisode").href);
                    else {
                        ((TextView) findViewById(R.id.next_episode_description)).setText(R.string.no_episodes_in_future);
                    }
                }
            }

            @Override
            public void onFailure(Call<ShowDto> call, Throwable t) {
                Log.e("ERR", t.toString());
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.show_cast_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void startClickHandler(final FloatingActionButton starButton) {
        if (!isFavourite) {
            addToFavourites(starButton);
        } else {
            removeFromFavourites(starButton);
        }
    }

    private void addToFavourites(final FloatingActionButton starButton) {
        ApiSettings.usersApi.addShowForUser(userId, Integer.toString(showDto.getId())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == StatusCodes.OK) {
                    changeStarIcon(starButton, true);
                    isFavourite = true;
                } else {
                    displayError("Already in favourites");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                displayError(t.getMessage());
            }

            private void displayError(String errorMessage) {
                Log.e("ERR", errorMessage);
                Toast.makeText(activity, "Couldn't add to favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromFavourites(final FloatingActionButton starButton) {
        ApiSettings.usersApi.deleteShowForUser(userId,Integer.toString(showDto.getId())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == StatusCodes.OK) {
                    changeStarIcon(starButton, false);
                    isFavourite = false;
                } else {
                    displayError("Already removed from favourites");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                displayError(t.getMessage());
            }

            private void displayError(String errorMessage) {
                Log.e("ERR",errorMessage);
                Toast.makeText(activity, "Couldn't remove from favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfInFavourites(final FloatingActionButton starButton, int showId) {

        ApiSettings.usersApi.isShowInUsersFavourites(userId, Integer.toString(showId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == StatusCodes.OK) {
                    changeStarIcon(starButton, true);
                    isFavourite = true;
                } else {
                    isFavourite = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                Toast.makeText(activity, "Can't connect to favourites database", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void changeStarIcon(FloatingActionButton starButton, boolean favourite) {
        if (favourite) {
            starButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp));
        } else {
            starButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp));
        }

    }

    private void share() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(Intent.EXTRA_TEXT, showDto.getUrl());
        startActivity(Intent.createChooser(share, getString(R.string.share_using)));
    }

    public static void navigate(AppCompatActivity activity, View transitionImage, ShowDto showListItem) {
        Intent intent = new Intent(activity, ShowDetailsActivity.class);
        intent.putExtra("show_id", showListItem.getId());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        try {
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "navigate:", e);
        }
    }

    private void getNextEpisode(String url) {
        ApiSettings.urlApi.getResponse(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    episode = new Gson().fromJson(response.body().charStream(), EpisodeDto.class);
                    ((TextView) findViewById(R.id.next_episode)).setText("Episode " + episode.getSeason() + "x" + episode.getNumber());
                    if (episode.getSummary() != null)
                        ((TextView) findViewById(R.id.next_episode_description)).setText(Jsoup.parse(episode.getSummary()).text());
                    ((TextView) findViewById(R.id.next_episode_time)).setText(episode.getAirdate() + ", " + episode.getAirtime());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERR", t.toString());
            }
        });
    }

    private void setCastListAdapter() {
        CastListAdapter castListAdapter = new CastListAdapter(list, ShowDetailsActivity.this, getSupportFragmentManager());
        recyclerView.setAdapter(castListAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!isFavourite) MyShowsFragment.removeShowFromFavourites(showDto.getId());
        finish();
        return super.onOptionsItemSelected(item);
    }


}
