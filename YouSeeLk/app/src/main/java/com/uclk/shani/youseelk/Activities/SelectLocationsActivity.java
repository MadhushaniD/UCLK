package com.uclk.shani.youseelk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.uclk.shani.youseelk.R;
import com.uclk.shani.youseelk.taglib.Tag;
import com.uclk.shani.youseelk.taglib.TagView;

import java.util.ArrayList;
import java.util.List;


public class SelectLocationsActivity extends AppCompatActivity
{


    AutoCompleteTextView searchView;

    public static List<String> locations = new ArrayList<String>(){
        {add("Kandy");
        add("Matale");
        add("Kaduwela");
        add("Colombo");
        add("Kalutara");
        add("Gampaha");
        add("Nuwaraeliya");
            add("Anuradhapura");
        }
    };

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


        final TagView tags = (TagView) findViewById(R.id.tagGroup);
        tags.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(TagView view, Tag tag, int position) {
                tags.remove(position);

            }
        });

        searchView = (AutoCompleteTextView) findViewById(R.id.searchPlaces);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations);
        searchView.setAdapter(adapter);
        Log.w("Here ","giving suggestions");

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
                if(locations.contains(s.toString()) && !tags.getTags().contains(s.toString())){
                    Tag tag = new Tag(s.toString());
                    tag.tagTextSize=20;
                    tag.isDeletable=true;
                    tags.addTag(tag);
                    Log.w("Here ","Adding tag");
                    searchView.setText("");
                }
            }
        });

    }

    private List<String> getStringTags(List<Tag> tags){
        List<String> stringTags = new ArrayList<>();
        for(Tag tag: tags){
            stringTags.add(tag.text);
        }
        Toast.makeText(getApplicationContext(), stringTags.get(0)+"",Toast.LENGTH_SHORT).show();
        return stringTags;
    }

    /*@Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }*/
}
