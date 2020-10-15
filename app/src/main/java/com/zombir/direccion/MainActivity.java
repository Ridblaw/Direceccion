package com.zombir.direccion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button b_mapa;
    TextView mensaje_id;
    UbicacionHtml ubicacionHtml;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mensaje_id= (TextView)findViewById(R.id.mensaje_id);
        b_mapa = (Button)findViewById(R.id.b_mapa);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }

        b_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(MainActivity.this, "Conectar porfavor el GPS", Toast.LENGTH_LONG).show();
                    mensaje_id.setText("");
                    return;
                }
                 MiUbicacion();

            }
        });



    }

    public void MiUbicacion(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
                    Log.e("TAG","lO");
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        /*vDireccion= "WA";*/
                        mensaje_id.setText(
                                Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>"
                                        +addresses.get(0).getLatitude()+"\n\n"+
                                        Html.fromHtml("<font color='#6200EE'><b>Longitude :</b><br></font>"
                                                +addresses.get(0).getLongitude()) +"\n\n"+
                                        Html.fromHtml("<font color='#6200EE'><b>CountryName :</b><br></font>"
                                                +addresses.get(0).getCountryName()) +"\n\n"+
                                        Html.fromHtml("<font color='#6200EE'><b>Locality :</b><br></font>"
                                                +addresses.get(0).getLocality()) +"\n\n"+
                                        Html.fromHtml("<font color='#6200EE'><b>AddressLine :</b><br></font>"
                                                +addresses.get(0).getAddressLine(0))));

                    }catch (Exception e){
                        Log.e("TAG",e.toString());
                    }
                }
            }
        });
    }
}
