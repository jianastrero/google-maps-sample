package dev.jianastrero.googlemapstest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dev.jianastrero.googlemapstest.api.PokemonApi;
import dev.jianastrero.googlemapstest.model.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;
    private RadioGroup rgMapType;
    private ImageView ivGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        rgMapType = findViewById(R.id.rgMapType);

    }

    @Override
    protected void onStart() {
        super.onStart();

        supportMapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            int mapTypeRbId;
            switch (googleMap.getMapType()) {
                case GoogleMap.MAP_TYPE_NORMAL:
                    mapTypeRbId = R.id.rbMapTypeNormal;
                    break;
                case GoogleMap.MAP_TYPE_HYBRID:
                    mapTypeRbId = R.id.rbMapTypeHybrid;
                    break;
                case GoogleMap.MAP_TYPE_SATELLITE:
                    mapTypeRbId = R.id.rbMapTypeSatellite;
                    break;
                case GoogleMap.MAP_TYPE_TERRAIN:
                    mapTypeRbId = R.id.rbMapTypeTerrain;
                    break;
                default:
                    mapTypeRbId = R.id.rbMapTypeNone;
                    break;
            }
            rgMapType.check(mapTypeRbId);

            MarkerOptions markerOptionsStart = new MarkerOptions()
                    .position(new LatLng(14.638935, 121.045847))
                    .draggable(true)
                    .title("Hello, PSA!");
            googleMap.addMarker(markerOptionsStart);


            googleMap.setOnMapClickListener(latLng -> {
//                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_home);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
//                        .anchor(0.5f, 0.5f) // default is 0.5f, 1.0f
//                        .icon(bitmapDescriptor)
                        .draggable(true)
                        .title("Marker");
                googleMap.addMarker(markerOptions);
            });
        });
        rgMapType.setOnCheckedChangeListener((group, checkedId) -> {
            int mapType = googleMap.getMapType();
            if (checkedId == R.id.rbMapTypeNone) {
                mapType = GoogleMap.MAP_TYPE_NONE;
            } else if (checkedId == R.id.rbMapTypeNormal) {
                mapType = GoogleMap.MAP_TYPE_NORMAL;
            } else if (checkedId == R.id.rbMapTypeHybrid) {
                mapType = GoogleMap.MAP_TYPE_HYBRID;
            } else if (checkedId == R.id.rbMapTypeSatellite) {
                mapType = GoogleMap.MAP_TYPE_SATELLITE;
            } else if (checkedId == R.id.rbMapTypeTerrain) {
                mapType = GoogleMap.MAP_TYPE_TERRAIN;
            }

            if (mapType != googleMap.getMapType()) {
                googleMap.setMapType(mapType);
            }
        });
        findViewById(R.id.btnToggleColorScheme).setOnClickListener(v -> {
            googleMap.setMapColorScheme(googleMap.getMapColorScheme() == 0 ? 1 : 0);
        });

        findViewById(R.id.btnAnimate).setOnClickListener(v -> {
            //14.629422, 121.023367
            LatLng latlng = new LatLng(14.629422, 121.023367);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16), 2000, null);
        });
    }
}
