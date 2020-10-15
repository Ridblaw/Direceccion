package com.zombir.direccion;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class UbicacionHtml {
    private Context mContext;
    public UbicacionHtml(Context mContext) {
        this.mContext = mContext;
    }

    FusedLocationProviderClient fusedLocationProviderClient;
    String vDireccion="";

    public void MiUbicacion(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                    if (location != null){
                        Log.e("TAG","lO");
                        try {
                            Geocoder geocoder = new Geocoder(mContext,
                                    Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(),location.getLongitude(),1
                            );
                            /*vDireccion= "WA";*/
                            vDireccion= ""+
                                    Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>"
                                                    +addresses.get(0).getLatitude()+"\n\n"+
                                    Html.fromHtml("<font color='#6200EE'><b>Longitude :</b><br></font>"
                                                    +addresses.get(0).getLongitude()) +"\n\n"+
                                    Html.fromHtml("<font color='#6200EE'><b>CountryName :</b><br></font>"
                                                    +addresses.get(0).getCountryName()) +"\n\n"+
                                    Html.fromHtml("<font color='#6200EE'><b>Locality :</b><br></font>"
                                                    +addresses.get(0).getLocality()) +"\n\n"+
                                    Html.fromHtml("<font color='#6200EE'><b>AddressLine :</b><br></font>"
                                                    +addresses.get(0).getAddressLine(0)));
                            Log.e("TAG","l1"+vDireccion);
                        }catch (Exception e){
                            Log.e("TAG",e.toString());
                        }
                    }
            }
        });
    }
    public String getvDireccion() {
        return vDireccion;
    }

}
