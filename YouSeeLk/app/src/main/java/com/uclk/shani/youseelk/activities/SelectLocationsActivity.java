package com.uclk.shani.youseelk.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.uclk.shani.youseelk.R;
import com.uclk.shani.youseelk.adapters.PlaceCardAdapter;
import com.uclk.shani.youseelk.objects.PlaceCard;

import java.util.ArrayList;
import java.util.List;


public class SelectLocationsActivity extends AppCompatActivity
{


    AutoCompleteTextView searchView;

    public static List<String> locations = new ArrayList<String>(){
        {   add("Alawatugoda");
            add("Kandy");
            add("Matale");
            add("Colombo");
            add("Pasikudah");
            add("Kaduwela");
            add("Kalutara");
            add("Gampaha");
            add("Nuwaraeliya");
            add("Anuradhapura");
        }
    };

    public static List<String> placeTags = new ArrayList<String>(){
        {   add("Chill");
            add("Hills");
            add("Historical");
            add("Beach");
        }
    };

    public static List<String> addedLocations = new ArrayList<String>();

    private RecyclerView rvAddedPlaces;
    private PlaceCardAdapter placeCardAdapter;
    private List<PlaceCard> placeCardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Button next1 = (Button) findViewById(R.id.btnNext1);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),DurationActivity.class);
                startActivity(intent);
            }
        });


        searchView = (AutoCompleteTextView) findViewById(R.id.searchPlaces);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations);
        searchView.setAdapter(adapter);


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check whether it is an accepted and not selected

            }

            @Override
            public void afterTextChanged(Editable s) {

                String place = s.toString();
                if(locations.contains(place)){

                    if(!(addedLocations.contains(place))){
                        addedLocations.add(place);
                        createCard(place);
                        searchView.setText("");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"You have already added " + place,Toast.LENGTH_LONG).show();
                        searchView.setText("");
                    }
                }

            }
        });

        rvAddedPlaces = (RecyclerView)findViewById(R.id.rvPlaceCards);
        placeCardsList = new ArrayList<>();
        placeCardAdapter = new PlaceCardAdapter(this,placeCardsList);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this,2);
        rvAddedPlaces.setLayoutManager(manager);
        rvAddedPlaces.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvAddedPlaces.setItemAnimator(new DefaultItemAnimator());
        rvAddedPlaces.setAdapter(placeCardAdapter);


    }

    //need to edit image sources
    private void createCard(String n){

        int[] thumbnails = new int[]{
                R.drawable.alawatugoda,
                R.drawable.kandy,
                R.drawable.matale,
                R.drawable.colombo,
                R.drawable.pasikudah};

        PlaceCard obj = new PlaceCard(n,"Province",thumbnails[1],placeTags);
        placeCardsList.add(obj);
        placeCardAdapter.notifyDataSetChanged();
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}




