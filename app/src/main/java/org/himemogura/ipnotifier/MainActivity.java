package org.himemogura.ipnotifier;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
	public static final String PREF_MUNUTE = "PREF_MUNUTE";
	public static final String PREF_AUTO_START = "PREF_AUTO_START";
	public static final String PREF_MONITOR = "PREF_CHECK_MONITOR";
	private CheckBox checkAutoStart;
	private CheckBox checkMonitor;
	private EditText editMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		checkAutoStart = (CheckBox) findViewById(R.id.checkAutoStart);
		checkAutoStart.setOnCheckedChangeListener(this);
		checkMonitor = (CheckBox) findViewById(R.id.checkMonitor);
		checkMonitor.setOnCheckedChangeListener(this);
		editMinute = (EditText) findViewById(R.id.editMinute);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == checkAutoStart) {
		} else if (buttonView == checkMonitor) {
			if (isChecked) {
				ScheduleManager.setSchedule(this);
				savePreferences();
			} else {
				ScheduleManager.cancelSchedule(this);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		editMinute.setText(pref.getString(PREF_MUNUTE, "60"));
		checkAutoStart.setChecked(pref.getBoolean(PREF_AUTO_START, false));
		checkMonitor.setChecked(pref.getBoolean(PREF_MONITOR, false));
	}

	@Override
	protected void onPause() {
		super.onPause();
		savePreferences();
	}

	private void savePreferences() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(PREF_MUNUTE, editMinute.getText().toString());
		editor.putBoolean(PREF_AUTO_START, checkAutoStart.isChecked());
		editor.putBoolean(PREF_MONITOR, checkMonitor.isChecked());
		editor.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		ScheduleManager.cancelSchedule(this);
	}
}
