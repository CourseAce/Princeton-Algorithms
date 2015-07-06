import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Daniel on 06/07/15.
 */
public class Client {
    String path;
    public Client(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        this.path = classLoader.getResource(path).getPath();
    }

    public Percolation run() throws IOException {
        FileReader fileReader = new FileReader(this.path);
        BufferedReader buffReader = new BufferedReader(fileReader);
        String l = buffReader.readLine();
        Percolation percolation = new Percolation(Integer.parseInt(l));
        while ((l=buffReader.readLine())!=null) {
            String[] s = l.trim().split(" ");
            percolation.open(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
        return percolation;
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client("percolation/input4.txt");
        Percolation percolation = c.run();
        percolation.isFull(4, 4);
    }
}
