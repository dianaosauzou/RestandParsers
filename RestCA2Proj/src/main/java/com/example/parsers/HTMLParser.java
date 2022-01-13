package com.example.parsers;

import com.example.dao.SystemDao;
import com.example.entities.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class HTMLParser {
//    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    SystemDao dao = new SystemDao();
    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("DianasPU");//


    File input = new File("users (3).html");
    Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

    public HTMLParser() throws IOException, ParseException {

        List<Element> elementsList = new ArrayList<Element>();
        Elements users = doc.getElementsByTag("tr");
        Elements userAtts = new Elements();
        Elements userEls = new Elements();

        for (Element user : users) {

            userAtts = user.getElementsByTag("td");
            for (Element userdetail : userAtts) {
                userEls = userdetail.getElementsByTag("td");

                for (Element userels : userEls) {
                    elementsList.add(userels);
                }

            }

        }

        //Partitions the data
        Map<Integer, List<Element>> groups =
                elementsList.stream().collect(Collectors.groupingBy(s -> (s.siblingIndex() / 2)));
        List<List<Element>> subSets = new ArrayList<List<Element>>(groups.values());
        assignValues(subSets);

    }

    public Consumer<? super List<Element>> assignValues(List<List<Element>> name) throws ParseException {
        List<List<Element>> newList = new ArrayList<>();
        for (List<Element> o : name) {
            newList.add(o);
        }

        //need to add in something that iterates over the four that isnt an integer
        for (int a = 0; a < 4; a++) {
            User user = new User();
            for (int x = 0; x < newList.size(); x++) {
                switch (x) {
                    case 0:
                        user.setName(newList.get(x).get(a).text());
                        break;
                    case 1:
                        user.setEmail(newList.get(x).get(a).text());
                        break;
                    case 2:
                        user.setLibraryID(newList.get(x).get(a).text());
                        break;
                    case 3:
                        user.setUsername(newList.get(x).get(a).text());
                        break;
                    case 4:
                        user.setJoinDate(Integer.parseInt(newList.get(x).get(a).text()));
                        break;
                    case 5:
                        user.setCountry(newList.get(x).get(a).text());
                        break;
                    case 6:
                        user.setAuth(Boolean.parseBoolean(newList.get(x).get(a).text()));
                        break;
                    default:
                }

            }
            dao.persistObject(user);
            System.out.println(user.getUsername() + user.getJoinDate());

        }
        return null;
    }

    public static void main (String[] args) throws IOException, ParseException {
        HTMLParser parser = new HTMLParser();

    }


}
