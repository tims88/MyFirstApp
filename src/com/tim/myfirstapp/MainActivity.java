package com.tim.myfirstapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	IntentFilter intentFilter;

	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            String text = SMSes.getText().toString();
            text += "\n" + intent.getExtras().getString("sms");
            SMSes.setText(text);
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//---intent to filter for SMS messages received---
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
	    // Do something in response to button
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		//SmsManager sms = SmsManager.getDefault();
		//sms.sendTextMessage("3035034184", null,message , null, null);
		
		new SendMessageToServerTask().execute(message);
		
		
		
		TextView textHistory = (TextView) findViewById(R.id.textView1);
		String text = textHistory.getText().toString();
		text += "\nMe: " + message;
		textHistory.setText(text);
		
//		Intent i = new
//                Intent(android.content.Intent.ACTION_VIEW);
//            i.putExtra("address", "3035034184");
//            i.putExtra("sms_body", message);
//            i.setType("vnd.android-dir/mms-sms");
//            startActivity(i);
	}
	
	@Override
    protected void onResume() {
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }
    @Override
    protected void onPause() {
        //---unregister the receiver---
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}

class SendMessageToServerTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... message) {
		
		
		Socket client;
		PrintWriter printwriter;
		try {
			client = new Socket("192.168.1.103", 4444); //connect to server
			printwriter = new PrintWriter(client.getOutputStream(),true);
			
			printwriter.write(message[0]);  //write the message to output stream

			printwriter.flush();
			printwriter.close();
			client.close();   //closing the connection
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("UnkownHostException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException");
		}  
		
		return null;
	}
	
}
