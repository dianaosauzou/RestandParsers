package com.example.parsers;

import com.example.dao.SystemDao;
import com.example.entities.Reccomendations;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.parser.JSONParser;


public class ParseJSON {

    private String jsonString = "";
    Reccomendations song;
    List recommendations;


    public List<Reccomendations> ParseJSON() throws IOException, ParseException {

        URL url = new URL("http://davidpots.com/jakeworry/017%20JSON%20Grouping,%20part%203/data.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
//        SystemDao dao = new SystemDao();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200)
            throw new RuntimeException("HTTP Response code " + responseCode);
        else {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                jsonString += scanner.nextLine();
            }

            JSONParser parser = new JSONParser();
            recommendations = new ArrayList();


            JSONObject jobj = (JSONObject) parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) jobj.get("songs");
            System.out.println("JSON as a JSONArray= posts collection");
            System.out.println(jsonArray);


                for(Object x: jsonArray) {
                    song = new Reccomendations();
                    JSONObject jo1 = (JSONObject) x;
                    Set<String> keys = jo1.keySet();
                    for (String l : keys) {
                        if (l.equalsIgnoreCase("img_url"))
                            song.setImg_url(jo1.get(l).toString());
                            if (l.equalsIgnoreCase("year"))
                                song.setYear((String) jo1.get(l));
                            if (l.equalsIgnoreCase("artist"))
                                song.setArtist((String) jo1.get(l));
                            if (l.equalsIgnoreCase("title"))
                                song.setTitle((String) jo1.get(l));
                        }
                        recommendations.add(song);
                    }
                }

            return recommendations;
        }


}
