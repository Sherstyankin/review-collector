package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class ReviewCollector {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("reviews.csv")) {
            // Write the headline
            writer.write("Title,Text,Author,Date\n");
            for (int i = 1; i <= 3; i++) {
                // Connect to every single page and get the data
                Document doc = Jsoup.connect("https://www.kinopoisk.ru/film/326/reviews/" +
                        "ord/date/status/all/perpage/200/page/" + i).get();
                // Get all reviews per page
                Elements reviews = doc.select("div.reviewItem");
                // Write the reviews to a CSV file
                for (Element review : reviews) {
                    String title = review.select("p.sub_title").text();
                    String text = review.select("span._reachbanner_").text();
                    String author = review.select("p.profile_name").text();
                    String date = review.select("span.date").text();
                    writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\"%n", title, text, author, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}