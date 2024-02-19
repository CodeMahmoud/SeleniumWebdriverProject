import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebsiteScraper {
    private WebDriver driver;

    public WebsiteScraper(WebDriver driver) {
        this.driver = driver;
    }

    public void searchAndScrape(String websiteUrl, String searchKeyword) {
        driver.get(websiteUrl);
        WebElement searchInput = driver.findElement(By.xpath("//input[@type='text']"));
        searchInput.sendKeys(searchKeyword, Keys.RETURN);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        List<WebElement> products = driver.findElements(By.xpath("//div[@class='s-item__info']")); // Update XPath
        try {
            FileWriter writer = new FileWriter("output.txt");
            for (WebElement product : products) {
                String title = product.findElement(By.xpath(".//h3[@class='s-item__title']")).getText();
                String price = product.findElement(By.xpath(".//span[@class='s-item__price']")).getText();
                writer.write(title + " - " + price + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        WebDriver driver = new ChromeDriver();
        WebsiteScraper scraper = new WebsiteScraper(driver);

        //eBay
        scraper.searchAndScrape("https://www.ebay.com/", "iphone");

        //Target
        scraper.searchAndScrape("https://www.target.com/", "iphone");

        driver.quit();
    }
}
