package com.tim.myfirstapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu items for use in the action bar
    	MenuInflater inflater  = getMenuInflater();
    	inflater.inflate(R.menu.display_message, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle presses on the action bar items
    	switch (item.getItemId()) {
	    	case R.id.action_search:
	    		System.out.println("me");
	    		return true;
	
	    	case android.R.id.home:
	    		NavUtils.navigateUpFromSameTask(this);
	    		return true;
	    		
	    	default:
	    		return super.onOptionsItemSelected(item);
    	}
    }
}