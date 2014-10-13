import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws Throwable {

        Document doc = Jsoup.connect("http://www.hs.fi/fingerpori").get();
        Elements links = doc.select("img[src*=/sarjis/]");
        String fingUrl = links.first().attr("src");
        String imgName = fingUrl.substring(fingUrl.lastIndexOf('/') + 1, fingUrl.lastIndexOf('?')).concat(".jpg");
        System.out.println("Downloading new Fing: " + imgName);
        saveImage(fingUrl, imgName);

    }

    public static void saveImage(String fingUrl, String imgName) throws IOException {
        URL url = new URL(fingUrl);
        OutputStream output;
        try (InputStream input = url.openStream()) {
            output = new FileOutputStream(imgName);
            byte[] b = new byte[2048];
            int length;
            while ((length = input.read(b)) != -1) {
                output.write(b, 0, length);
            }
        }
        output.close();
    }
}
