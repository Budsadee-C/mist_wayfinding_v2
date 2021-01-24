package com.juniper.mist_wayfinding_v2;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonFectData extends AsyncTask<Void, Void, Void> {
    public String data="";
    public ArrayList<String> zone = new ArrayList<String>();
    public ArrayList<Double> zone_vertical_x = new ArrayList<Double>();
    public ArrayList<Double> zone_vertical_y = new ArrayList<Double>();


    public ArrayList<Double> value_x = new ArrayList<Double>();
    public ArrayList<Double> value_y = new ArrayList<Double>();

    public ArrayList<Double> ori_value_x = new ArrayList<Double>();
    public ArrayList<Double> ori_value_y = new ArrayList<Double>();

    public ArrayList<String> map = new ArrayList<String>();
    public ArrayList<String> node_name = new ArrayList<String>();
    public ArrayList<Double> node_x = new ArrayList<Double>();
    public ArrayList<Double> node_y = new ArrayList<Double>();

    public JSONObject map_edge=null;

    double min_y_zone =0;
    double max_y_zone =0;
    double min_x_zone =0;
    double max_x_zone =0;

    public double size_zone_width=0.0;
    public double size_zone_height=0.0;
    public String way_str="";


    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection httpsURLConnection = null;

        try {
            //URL url = new URL("https://api.mist.com/api/v1/sites/7bf7d46b-ebf7-4bfc-b3c8-47f5ca784e1f/zones"); //JNPR
            URL url = new URL("https://api.mist.com/api/v1/sites/93c9f1da-c1de-4db2-b7e5-301114f25e80/zones"); //PPLUS
            httpsURLConnection =(HttpURLConnection) url.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Authorization", "Token h1bloiMLLduKqF1HeRWoIDrXHhD3RPIecoFSYy89rYccpGHieEkIPpX3i2SDecy5RUDs4OXmkUD5wWh1va3TpRguctYnjaPt");
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                data = data + line;
            }
            JSONArray Array_zone = new JSONArray(data);
            JSONArray Array_vertical= null;
            JSONObject zone_object = null;
            JSONObject vertical_object = null;
            for(int i =0; i<Array_zone.length();i++)
            {
                zone_object = Array_zone.getJSONObject(i);

                //if(zone_object.getString("map_id").toString().equals("d25f5676-cb71-4519-b9e2-4c267cbdf9a4")) // JNPR
                if(zone_object.getString("map_id").toString().equals("593cbfa7-4ac8-4ffb-a712-982f1ec77dca")) //PPlus
                {
                    zone.add((String) zone_object.getString("name"));
                    Array_vertical = new JSONArray(zone_object.get("vertices").toString());
                    for (int j = 0; j < Array_vertical.length(); j++) {
                        vertical_object = Array_vertical.getJSONObject(j);
                        zone_vertical_x.add((Double) vertical_object.getDouble("x"));
                        zone_vertical_y.add((Double) vertical_object.getDouble("y"));
                    }
                }

                //Array_vertical = zone_object.optJSONArray("vertices");
            }
            bufferedReader.close();
            httpsURLConnection.disconnect();
            fectMap();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*finally {
            if (httpsURLConnection != null) {
                try {
                    httpsURLConnection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }*/


        return null;
    }
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
    public String getDataZone()
    {
        return this.data;
    }
    protected void fectMap()
    {
        HttpURLConnection httpsURLConnection = null;
        data = "";
        try {
            //URL url = new URL("https://api.mist.com/api/v1/sites/7bf7d46b-ebf7-4bfc-b3c8-47f5ca784e1f/maps"); //JNPR
            URL url = new URL("https://api.mist.com/api/v1/sites/93c9f1da-c1de-4db2-b7e5-301114f25e80/maps"); // PPLUS
            httpsURLConnection =(HttpURLConnection) url.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Authorization", "Token h1bloiMLLduKqF1HeRWoIDrXHhD3RPIecoFSYy89rYccpGHieEkIPpX3i2SDecy5RUDs4OXmkUD5wWh1va3TpRguctYnjaPt");
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                data = data + line;
            }
            JSONArray Array_map = new JSONArray(data);
            JSONArray Array_way= null;
            JSONObject map_object = null;
            JSONObject way = null;
            way_str = "";
            JSONObject position_object = null;

            for(int i =0; i<Array_map.length();i++)
            {
                map_object = Array_map.getJSONObject(i);
                //if(map_object.getString("id").toString().equals("d25f5676-cb71-4519-b9e2-4c267cbdf9a4")) //JNPR
                if(map_object.getString("id").toString().equals("593cbfa7-4ac8-4ffb-a712-982f1ec77dca")) //PPlus
                {
                    map.add((String) map_object.getString("name"));
                    way_str += map_object.get("wayfinding_path").toString();
                }
            }
            way = new JSONObject(way_str);
            Array_way = way.getJSONArray("nodes");
            String map_node_str="";
            for(int j=0;j<Array_way.length();j++)
            {
                position_object = Array_way.getJSONObject(j);
                node_name.add((String)position_object.getString("name"));
                node_x.add((Double) position_object.getJSONObject("position").getDouble("x"));
                node_y.add((Double) position_object.getJSONObject("position").getDouble("y"));
                map_node_str +=position_object.get("edges").toString();
                System.out.println("Nodes name is: "+node_name.get(j));
            }
            map_edge = new JSONObject(map_node_str);
            bufferedReader.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int CalAround(int index)
    {
        calWidth(index);
        calHeight(index);
        int position = surround();
        if(position !=-1)
        {
            //System.out.println("Node of "+zone.get(index)+" is x: "+node_x.get(position)+" Y: "+node_y.get(position));
            return position;
        }
        return -1;
    }

    public void calWidth(int index)
    {
        value_x.clear();
        ori_value_x.clear();
        min_x_zone =0;
        max_x_zone =0;
        for(int i=(4*index)-3;i<=index*4;i++)
        {
            value_x.add(zone_vertical_x.get(i));
            ori_value_x.add(zone_vertical_x.get(i));
        }
        //System.out.println("Point of X in "+zone.get(index)+" is "+ori_value_x);
        Collections.sort(value_x);
        min_x_zone = value_x.get(0);
        max_x_zone = value_x.get(value_x.size()-1);
        size_zone_width = max_x_zone-min_x_zone;


    }
    public void calHeight(int index)
    {

        value_y.clear();
        ori_value_y.clear();
        min_y_zone =0;
        max_y_zone =0;
        for(int i=(4*index)-3;i<=index*4;i++)
        {
            value_y.add(zone_vertical_y.get(i));
            ori_value_y.add(zone_vertical_y.get(i));
        }
        //System.out.println("Point of Y in "+zone.get(index)+" is "+ori_value_y);
        Collections.sort(value_y);
        min_y_zone = value_y.get(0);
        max_y_zone = value_y.get(value_y.size()-1);
        size_zone_height = max_y_zone-min_y_zone;


    }
    public int surround()
    {
        for(int i=0;i<node_x.size();i++)
        {
            if(min_x_zone<=node_x.get(i)&&max_x_zone>=node_x.get(i))
            {
                if(min_y_zone<=node_y.get(i)&&max_y_zone>=node_y.get(i))
                {
                    return i;
                } // this node is in a scope
            }
        }
        return -1;
    }
    public int clickSuround(float x ,float y)
    {
        for(int i=0;i<node_x.size();i++)
        {
            if(min_x_zone<=node_x.get(i)&&max_x_zone>=node_x.get(i))
            {
                if(min_y_zone<=node_y.get(i)&&max_y_zone>=node_y.get(i))
                {
                    return i;
                } // this node is in a scope
            }
        }
        return -1;
    }
}

