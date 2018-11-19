package vn.awesome.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		ButterKnife.bind(this);
		init();
	}
	
	private void init() {
		
		// set ToolBar as action bar
		setSupportActionBar(findViewById(R.id.toolbar));
		
		// set title for toolbar
		getSupportActionBar().setTitle(R.string.search);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			
			case android.R.id.home:
				
				onBackPressed();
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
