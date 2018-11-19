package vn.awesome.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
	
	private GoogleMap mMap;
	private static final int REQUEST_ACCESS_LOCATION = 1000;
	private double latitude;
	private double longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		ButterKnife.bind(this);
		init();
		initMap();
	}
	
	private void init() {
		
		// set ToolBar as action bar
		setSupportActionBar(findViewById(R.id.toolbar));
		
		// set title for toolbar
		getSupportActionBar().setTitle(R.string.nearby);
	}
	
	private void initMap() {
		
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		if (mapFragment != null) {
			mapFragment.getMapAsync(this);
		}
		checkPermission();
	}
	
	@OnClick(R.id.current_location)
	void onCurrentLocation() {
		
		getCurrentLocation();
	}
	
	@Override
	public void onMapReady(GoogleMap googleMap) {
		
		mMap = googleMap;
		
		getCurrentLocation();
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		boolean isPermissionGranted = handlePermissionResponse(requestCode, grantResults, REQUEST_ACCESS_LOCATION);
		if (isPermissionGranted) {
			getLocationGPS();
		}
	}
	
	private void checkPermission() {
		
		if (checkPermissionGranted()) {
			getLocationGPS();
		}
		else {
			requestPermission(REQUEST_ACCESS_LOCATION);
		}
	}
	
	private void getLocationGPS() {
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (lm != null) {
			
			for (String provider : lm.getAllProviders()) {
				
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					return;
				}
				Location location = lm.getLastKnownLocation(provider);
				if (location != null) {
					
					latitude = location.getLatitude();
					longitude = location.getLongitude();
				}
				break;
			}
		}
	}
	
	private void getCurrentLocation() {
		
		LatLng latLng = new LatLng(latitude, longitude);
		Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
		marker.setVisible(true);
		
		CameraUpdate cu = CameraUpdateFactory.newLatLng(latLng);
		mMap.animateCamera(cu);
	}
	
	public boolean checkPermissionGranted() {
		
		return ContextCompat.checkSelfPermission(this,
												 android.Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED;
	}
	
	public void requestPermission(int requestCode) {
		
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
				}
			}
			else {
				// No explanation needed, we can request the permission.
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
				}
				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}
	}
	
	public boolean handlePermissionResponse(int requestCode, int[] grantResults, int permissionRequestCode) {
		
		return permissionRequestCode == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
	}
}
