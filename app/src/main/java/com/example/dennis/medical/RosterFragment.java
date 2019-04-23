package com.example.dennis.medical;


import android.content.ComponentCallbacks;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosterFragment extends Fragment {


    public RosterFragment() {
        // Required empty public constructor
    }

    private TextView dutyresult, name, position, DRMON, DRTUE, DRWED, DRTHU, DRFRI, DRSAT, DRSUN;
    private RequestQueue mQueue;

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private long backPressedTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roster, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        name = view.findViewById(R.id.txtName);
        position = view.findViewById(R.id.txtPosition);
        DRMON = view.findViewById(R.id.txtMON);
        DRTUE = view.findViewById(R.id.txtTUE);
        DRWED = view.findViewById(R.id.txtWED);
        DRTHU = view.findViewById(R.id.txtTHU);
        DRFRI = view.findViewById(R.id.txtFRI);
        DRSAT = view.findViewById(R.id.txtSAT);
        DRSUN = view.findViewById(R.id.txtSUN);

//        dutyresult = view.findViewById(R.id.txtdutyresult);
        mQueue = Volley.newRequestQueue(getActivity());
        jsonParse();


    return view;
    }

    private void jsonParse() {
        String url = "http://10.114.44.244/Medical/secret/dutytableapi.php";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray dutyroster = new JSONArray(response);
//                    for (int i = 0; i < dutyroster.length(); i++) {
//                        JSONObject dutyrosterobject = dutyroster.getJSONObject(i);
////                        int ID = dutyrosterobject.getInt("ID");
//                        String Name = dutyrosterobject.getString("Name");
//                        String Position = dutyrosterobject.getString("Position");
//                        String MON = dutyrosterobject.getString("MON");
//                        String TUE = dutyrosterobject.getString("TUE");
//                        String WED = dutyrosterobject.getString("WED");
//                        String THU = dutyrosterobject.getString("THU");
//                        String FRI = dutyrosterobject.getString("FRI");
//                        String SAT = dutyrosterobject.getString("SAT");
//                        String SUN = dutyrosterobject.getString("SUN");
//
//                        dutyresult.append(Name + "," + Position + "," + MON + "," + TUE + "," + WED
//                                + "," + THU + "," + FRI + "," + SAT + "," + SUN);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(request);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray roster = new JSONArray(response);
                    for(int i = 0; i<roster.length(); i++){
                        JSONObject rosterobject = roster.getJSONObject(i);
                        String Name = rosterobject.getString("Name");
                        String Position = rosterobject.getString("Position");
                        String MON = rosterobject.getString("MON");
                        String TUE = rosterobject.getString("TUE");
                        String WED = rosterobject.getString("WED");
                        String THU = rosterobject.getString("THU");
                        String FRI = rosterobject.getString("FRI");
                        String SAT = rosterobject.getString("SAT");
                        String SUN = rosterobject.getString("SUN");

//                        dutyresult.append(Name + "," + Position + "," + MON + "," + TUE + "," + WED
//                                + "," + THU + "," + FRI + "," + SAT + "," + SUN);

                        name.append(Name + "\n\n");
                        position.append(Position + "\n\n");
                        DRMON.append(MON + "\n\n");
                        DRTUE.append(TUE + "\n\n");
                        DRWED.append(WED + "\n\n");
                        DRTHU.append(THU + "\n\n");
                        DRFRI.append(FRI + "\n\n");
                        DRSAT.append(SAT + "\n\n");
                        DRSUN.append(SUN + "\n\n");


                    }

//                    notificationAdapter = new NotificationAdapter(getContext(), notificationmodels);
//                    NotificationRecycleView.setAdapter(notificationAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

}
