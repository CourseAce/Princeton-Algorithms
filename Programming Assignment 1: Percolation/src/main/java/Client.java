import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Daniel on 06/07/15.
 */
public class Client {
    File f;
    public Client(String path) throws IOException, URISyntaxException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        this.f = new File(classLoader.getResource(path).toURI());
    }

    public Percolation run() throws IOException {
        FileReader fileReader = new FileReader(this.f);
        BufferedReader buffReader = new BufferedReader(fileReader);
        String l = buffReader.readLine();
        Percolation percolation = new Percolation(Integer.parseInt(l));
        while ((l=buffReader.readLine())!=null) {
            String[] s = l.trim().split(" ");
            percolation.open(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
        return percolation;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Client c = new Client("percolation/input4.txt");
        Percolation percolation = c.run();
        System.out.println(percolation.isFull(4, 4));
    }
}
