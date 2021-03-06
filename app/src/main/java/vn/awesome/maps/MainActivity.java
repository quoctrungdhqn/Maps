package vn.awesome.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
	}
	
	private void init() {
		
		// set ToolBar as action bar
		setSupportActionBar(findViewById(R.id.toolbar));
		
		// set title for toolbar
		getSupportActionBar().setTitle(R.string.app_name);
	}
	
	@OnClick(R.id.btn_search)
	void onSearch() {
		
		// search
		startActivity(new Intent(this, SearchActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.map, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			
			case R.id.action_map:
				
				// nearby
				startActivity(new Intent(this, MapActivity.class));
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
