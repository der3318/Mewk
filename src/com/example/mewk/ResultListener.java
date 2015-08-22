package com.example.mewk;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ResultListener implements OnItemClickListener {
	
	private Activity myAct;
	private final ArrayList<ItemData> items_temp;
	
	public ResultListener(ArrayList<ItemData> _items_temp, Activity _myAct) {
		items_temp = _items_temp;
		myAct = _myAct;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final ItemData item = items_temp.get((int)id);
		String content = new String("MEWK BEFORE\n    ");
		content = content + item.deadline + "\n\nDESCRIPTION\n    " + item.description + "\n\nLOCATION\n    " + item.location
				+ "\n\nCONTACT ME\n    Username: " + item.username + "\n    Phone: " + item.phone + "\n    Email: " + item.email;
		AlertDialog.Builder dialog = new AlertDialog.Builder(myAct);
		dialog.setTitle(item.itemname);
		dialog.setPositiveButton("Call Now", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent call = new Intent("android.intent.action.CALL", Uri.parse("tel:" + item.phone) );
				myAct.startActivity(call);
			}
		});
		dialog.setNeutralButton("Google Map here", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Uri uri = Uri.parse( "http://maps.google.com?q=" + item.location );
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				myAct.startActivity(intent);
			}
		});
		dialog.setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog.setMessage(content);
		dialog.show();
	}

}
