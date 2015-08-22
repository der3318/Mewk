package com.example.mewk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.mewk.MainActivity.PlaceholderFragment;
import com.parse.ParseObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MewkListener implements OnClickListener {

	private EditText username, phone, mail, itemname, description, day, hour, location;
	private PlaceholderFragment myFrag;
	
	MewkListener(EditText _username, EditText _phone, EditText _mail, EditText _itemname, EditText _description, EditText _day, EditText _hour, EditText _location, PlaceholderFragment _myFrag) {
		username = _username;
		phone = _phone;
		mail = _mail;
		itemname = _itemname;
		description = _description;
		day = _day;
		hour = _hour;
		location = _location;
		myFrag = _myFrag;
	}
	
	@Override
	public void onClick(View v) {
		ParseObject thing = new ParseObject("Thing");
		if( username.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty() ||
				mail.getText().toString().trim().isEmpty() || itemname.getText().toString().trim().isEmpty() ||
				description.getText().toString().trim().isEmpty() || day.getText().toString().trim().isEmpty() ||
				hour.getText().toString().trim().isEmpty() || location.getText().toString().isEmpty() ) {
			Toast popup = Toast.makeText(myFrag.getActivity(), "Blank Space Found", Toast.LENGTH_SHORT);
			popup.show();
			return;
		}
		Calendar cal = Calendar.getInstance();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.setTime( new Date() );
		cal.add( Calendar.HOUR_OF_DAY, Integer.parseInt( day.getText().toString() ) * 24 + Integer.parseInt( hour.getText().toString() ) );  
		thing.put( "username", username.getText().toString() );
		thing.put( "phone", phone.getText().toString() );
		thing.put( "mail", mail.getText().toString() );
		thing.put( "itemname", itemname.getText().toString() );
		thing.put( "description", description.getText().toString() );
		thing.put( "deadline", df.format( cal.getTime() ) );
		thing.put( "location", location.getText().toString() );
		thing.saveInBackground();
		Toast popup = Toast.makeText(myFrag.getActivity(), "Updating", Toast.LENGTH_SHORT);
		popup.show();
		popup = Toast.makeText(myFrag.getActivity(), "Succeed", Toast.LENGTH_SHORT);
		popup.show();
		username.setText("");
		phone.setText("");
		mail.setText("");
		itemname.setText("");
		description.setText("");
		day.setText("");
		hour.setText("");
		location.setText("");
	}

}
