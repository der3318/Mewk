package com.example.mewk;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	protected static final int MENU_ABOUT = Menu.FIRST;
	protected static final int MENU_Quit = Menu.FIRST + 1;

	private void openIntroDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setTitle("About Us");
		String content = new String("Mewk, a HackNTU App\n\n");
		content = content + "Members:\n    簡瑋德\n    Elise Cheng\n    張以白\n    李鈺昇"; 
		dialog.setMessage(content);
		dialog.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "vsZrd0ICONJnsGFD4fpNfvP3bsDjhCTzaAlli8fz", "JhTtRkmfzwoCk4mFteN4O1c9vYtP8vwWGuFcqM1g");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
				.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add( 0, MENU_ABOUT, 0, "About" );
		menu.add(0, MENU_Quit, 0, "Quit" );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() ==  MENU_ABOUT)	openIntroDialog();
		else if(item.getItemId() == MENU_Quit)	finish();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private static int number;
		private EditText username, phone, mail, itemname, description, day, hour, location, edt_search;
		private Button mewk, btn_search;
		private ListView l_results;
		private TextView t_results;
		private ArrayList<ItemData> items;
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			number = sectionNumber;
			return fragment;
		}

		public void findViews(View rootView) {
			if(number == 2) {
				l_results = (ListView)rootView.findViewById(R.id.listView_results);
				t_results = (TextView)rootView.findViewById(R.id.textView_results);
				edt_search = (EditText)rootView.findViewById(R.id.edt_search);
				btn_search = (Button)rootView.findViewById(R.id.btn_search);
			}
			if(number == 3) { 
				username = (EditText)rootView.findViewById(R.id.username);
				phone = (EditText)rootView.findViewById(R.id.phone);
				mail = (EditText)rootView.findViewById(R.id.mail);
				itemname = (EditText)rootView.findViewById(R.id.itemname);
				description = (EditText)rootView.findViewById(R.id.description);
				day = (EditText)rootView.findViewById(R.id.day);
				hour = (EditText)rootView.findViewById(R.id.hour);
				location = (EditText)rootView.findViewById(R.id.location);
				mewk = (Button)rootView.findViewById(R.id.mewk);
			}
		}
		
		public void setListeners(final View rootView) {
			if(number == 2)	btn_search.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					show( rootView, true, edt_search.getText().toString() );
				}
			});
			if(number == 3)	mewk.setOnClickListener( new MewkListener( username, phone, mail, itemname, description, day, hour, location, this) );
		}
		
		public void show(final View rootView, boolean flag, String key) {
			if(number != 2)	return;
			ParseQuery<ParseObject> query_browse = ParseQuery.getQuery("Thing");
			query_browse.orderByAscending("deadline");
			if(flag)	query_browse.whereStartsWith("itemname", key);
			query_browse.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
//			             data found, list data
						TextView t_results = (TextView)rootView.findViewById(R.id.textView_results);
						t_results.setText("All results corresponding to query:");
						items = new ArrayList<ItemData>();
						for(ParseObject o : objects)
							items.add(new ItemData( 
								o.getString("itemname"), o.getString("description"), o.getString("deadline"),
								o.getString("username"), o.getString("phone"), o.getString("mail"), o.getString("location")
							));
						final ItemsAdapter itemsAdapter = new ItemsAdapter(getActivity(), items);
			        	l_results.setAdapter(itemsAdapter);
			        	l_results.setOnItemClickListener( new ResultListener( items, getActivity() ) );
					}
					else {
//						no data
						TextView t_results = (TextView)rootView.findViewById(R.id.textView_results);
						t_results.setText("Nothing here!");
					}
				}
			});
		}
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			if(number == 2)	rootView = inflater.inflate(R.layout.fragment_view, container, false);
			if(number == 3)	rootView = inflater.inflate(R.layout.fragment_give, container, false);
			findViews(rootView);
			show( rootView, false, new String("") );
			setListeners(rootView);
			return rootView;
		}
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
