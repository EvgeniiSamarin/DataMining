import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LinksParcer {

    private static WebDriver driver;
    private static HashMap<String, ArrayList<String>> linksMap;

    public static Map<String, ArrayList<String>> getLinks(String link, int level) {

        // Link settings
        List<String> list = new ArrayList<>();
        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;

        // Driver settings
        String chromedriver = "/usr/local/bin/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromedriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(link);

        // Get links
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();

        // Add parent lin
        switch (level) {
            case 0:
            case 1:
                while(it.hasNext()){
                    System.out.println("Level " + level + " " + link);
                    url = it.next().getAttribute("href");

                    if(url == null || url.isEmpty() || url.contains("https://www.facebook.com") || url.contains("https://www.youtube.com") || url.contains("https://www.instagram.com") || url.contains("https://vk.com") || url.contains("https://galos.ru")) {
                        continue;
                    }

                    try {
                        huc = (HttpURLConnection)(new URL(url).openConnection());
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        respCode = huc.getResponseCode();
                        if(respCode < 400){
                            getLinks(url, level + 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                ArrayList<String> listLinks = new ArrayList();
                if (!linksMap.containsKey(link)) {
                    while (it.hasNext()) {
                        url = it.next().getAttribute("href");

                        if (url == null || url.isEmpty() || linksMap.containsKey(url) || url.contains("https://www.facebook.com") || url.contains("https://www.youtube.com") || url.contains("https://www.instagram.com") || url.contains("https://vk.com") || url.contains("https://galos.ru")) {
                            System.out.println("URL is either not configured for anchor tag or it is empty");
                            continue;
                        }

                        try {
                            huc = (HttpURLConnection) (new URL(url).openConnection());
                            huc.setRequestMethod("HEAD");
                            huc.connect();
                            respCode = huc.getResponseCode();
                            if (respCode < 400) {
                                if (!listLinks.contains(url) && !url.contains("https://www.facebook.com") && !url.contains("https://www.youtube.com") && !url.contains("https://www.instagram.com") && !url.contains("https://vk.com") && !url.contains("https://galos.ru")) {
                                    System.out.println(url);
                                    listLinks.add(url);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    linksMap.put(link, listLinks);
                }
                driver.quit();
                break;
            case 2:
                System.out.println("Level " + level + " " + link);
                ArrayList<String> listLinks2 = new ArrayList();
                if (!linksMap.containsKey(link)) {
                    while (it.hasNext()) {
                        url = it.next().getAttribute("href");

                        if (url == null || url.isEmpty() || url.contains("https://www.facebook.com") || url.contains("https://www.youtube.com") || url.contains("https://www.instagram.com") || url.contains("https://vk.com") || url.contains("https://galos.ru")) {
                            System.out.println("URL is either not configured for anchor tag or it is empty");
                            continue;
                        }

                        try {
                            huc = (HttpURLConnection) (new URL(url).openConnection());
                            huc.setRequestMethod("HEAD");
                            huc.connect();
                            respCode = huc.getResponseCode();
                            if (respCode < 400) {
                                if (!listLinks2.contains(url) && (url.contains("https://www.facebook.com") || url.contains("https://www.youtube.com") || url.contains("https://www.instagram.com") || url.contains("https://vk.com") || url.contains("https://galos.ru"))) {
                                    System.out.println(url);
                                    listLinks2.add(url);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    linksMap.put(link, listLinks2);
                }
                driver.quit();
                break;
            default:
                break;
        }

        return linksMap;
    }


    public static void main(String[] args) {
        linksMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String link = scanner.nextLine();
        Map<String, ArrayList<String>> list = getLinks(link, 0);
        for( Map.Entry entry : list.entrySet()) {

            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }
    }
}
