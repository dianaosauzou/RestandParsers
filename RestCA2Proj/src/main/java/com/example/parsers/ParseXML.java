package com.example.parsers;

import com.example.dao.SystemDao;
import com.example.entities.PlayList;
import com.example.entities.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseXML {
    File xmlFile ;
    NodeList elementList;


    SystemDao dao = new SystemDao();
    int trackId;
    String name;
    String genre;
    String artist;
    NodeList elementList1;

    List<Node> tracks = new ArrayList();
    List<Node> playlists = new ArrayList();

    public void setFile(Long id) throws ParserConfigurationException, IOException, SAXException {
        int intID = id.intValue();
        if (intID == 1) {
            xmlFile = new File("iTunes Music Library1 (2).xml");
        }
        if (intID == 2) {
            xmlFile = new File("iTunes Music Library2 (1) (1).xml");
        }
        if (intID == 3) {
            xmlFile = new File("iTunes Music Library3.xml");
        }
        if (intID == 4) {
            xmlFile = new File("iTunes Music Library4.xml");
        }
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        elementList = doc.getElementsByTagName("key");
    }


    public ParseXML() throws ParserConfigurationException, IOException, SAXException, NullPointerException {

    }

    //Parses the tracks
    public List<Track> parseTracks() {
        List <Track> trackList = new ArrayList<>();
        String type = "tracks";
        List<Node> list = parser(type, tracks);
        //Unable to do a for loop on element list so i'm copying the nodes to an arraylist
        //Gets individual tracks
        for (Node s : list) {
            trackList.add(extractInfo(s));
        }

        return trackList;

    }

    public List<PlayList> parsePlaylists() {
        String type = "playlists";
        List<Node> list = parser(type, playlists);
        List<PlayList> playList = new ArrayList();


        for (Node s : list) {
            int g = 0;
            //gs max is the length of s.getchildnodes.length
//            int length = s.getChildNodes().getLength();
           playList.add(extractPlaylist(s,g));

        }

        return playList;
    }

    public PlayList extractPlaylist(Node s, int g) {
        List<Track> list = new ArrayList<>();
        int trackID = 0;
        String name = null;
        do {
            for (int a = 0; a < s.getChildNodes().item(g).getChildNodes().getLength(); a++) {
                String bx = s.getChildNodes().item(g).getChildNodes().item(a).getTextContent();

                if (s.getChildNodes().item(g).getTextContent().equalsIgnoreCase("name"))
                    name = s.getChildNodes().item(g).getNextSibling().getTextContent();

                if (bx.equalsIgnoreCase("playlist items")) {
                    for (int oh = 0; oh < s.getChildNodes().item(g).getNextSibling().getNextSibling().getChildNodes().getLength(); oh++)
                        for (int ah = 0; ah < s.getChildNodes().item(g).getNextSibling().getNextSibling().getChildNodes().item(oh).getChildNodes().getLength(); ah++)
                            if (s.getChildNodes().item(g).getNextSibling().getNextSibling().getChildNodes().item(oh).getChildNodes().item(ah).getTextContent().equalsIgnoreCase("Track ID")) {
                                trackID = Integer.parseInt(s.getChildNodes().item(g).getNextSibling().getNextSibling().getChildNodes().item(oh).getChildNodes().item(ah).getNextSibling().getTextContent());
                                Track track = new Track((long) trackID);
                                list.add(track);
                            }
                }
            }
            g++;

        } while (g < s.getChildNodes().getLength());

        PlayList playList = new PlayList(name, list);

        return playList;

    }

    private List<Node> parser(String type, List<Node> list) {
        for (int x = 0; x < elementList.getLength(); x++) {
            Node elem = elementList.item(x);
            if (elem.getNodeType() == Node.ELEMENT_NODE) {
                if (elem.getTextContent().equalsIgnoreCase(type)) {
                    //Gets all the children in the dictionary tag
                    elementList1 = elem.getNextSibling().getNextSibling().getChildNodes();
                }
            }
        }
        for (int a = 0; a < elementList1.getLength(); a++) {
            if (elementList1.item(a).getNodeName().equalsIgnoreCase("dict")) {
                Node newNode = elementList1.item(a);
                list.add(newNode);
            }
        }
        return list;
    }


    //So we know it successfully parses each indivudal node, now we have to assign it and create it in the dao
    public Track extractInfo(Node s) {
        //s is my node in tracks list
        String bx = new String();
        Node a = null;
        Track newTrack = new Track();

        //only looping over one node
        for (int x = 0; x < s.getChildNodes().getLength(); x++) {

            if (s.getChildNodes().item(x) != null) {
                bx = s.getChildNodes().item(x).getTextContent();
                a = s.getChildNodes().item(x);
            }
            if (bx.equalsIgnoreCase("Track ID")) {
                trackId = (Integer.parseInt(a.getNextSibling().getTextContent()));
                newTrack.setTrackId((long) trackId);
            }
            if (bx.equalsIgnoreCase("Name")) {
                name = a.getNextSibling().getTextContent();
                newTrack.setName(name);
            }
            if (bx.equalsIgnoreCase("Genre") && s.getChildNodes().item(x).getNextSibling().getPreviousSibling().getTextContent().equalsIgnoreCase("Genre")) {
                genre = a.getNextSibling().getTextContent();
                newTrack.setGenre(genre);
            }
            if (bx.equalsIgnoreCase("Artist") && s.getChildNodes().item(x).getNextSibling().getPreviousSibling().getTextContent().equalsIgnoreCase("Artist")) {
                artist = a.getNextSibling().getTextContent();
                newTrack.setArtist(artist);
            }

        }

//        dao.persistObject(newTrack);
        //Will call the dao after rest calls the interface implementation
        return newTrack;
    }


}
