
/**
 * @author Elton Fonseca, Felipe Hercules, Gabriel Castelo, Gean Matos
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sun.org.apache.xpath.internal.SourceTree;

public class Log {

    private String path;
    private String os;
    private File file;
    private FileOutputStream outputStream;
    private PrintWriter writer;
    private BufferedReader buffer;
    private FileReader reader;

    /**
     * Construtor
     * @param fileName Nome do arquivo
     * @param path Caminho do arquivo
     */
    public Log(String fileName, String path) {
        this.path = path + fileName;
        buildPath();
    }

    /**
     * Construtor
     * @param fileName Nome do arquivo
     */
    public Log(String fileName) {
        getOS();
        if (isWindows()) {
            this.path = "logs\\" + fileName;
        } else if (isMac()) {
            this.path = "logs/" + fileName;
        } else if (isUnix()) {
            this.path = "logs/" + fileName;
        } else {
            System.err.println("Error: File System not Support the Log class");
        }
        buildPath();
    }

    /**
     * Modifica o caminho
     * @param path Caminho
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Salva uma mensagem no arquivo
     * @param message Mensagem
     */
    public void save(String message) {
        try {
            outputStream = new FileOutputStream(file, true);
            writer = new PrintWriter(outputStream);
        } catch (FileNotFoundException e) {
            System.err.println("Log error: Wron't write in file!");
        }
        writer.println(message);
        writer.close();
    }

    public String search(int index) {
        String line = null;
        try {
            reader = new FileReader(this.file);
            buffer = new BufferedReader(reader);
            while((line = buffer.readLine()) != null) {
                String[] strings = line.split(";");
                if(Integer.parseInt(strings[0]) == index)
                    return strings[1];
            }

        } catch (IOException e) {
            System.err.println("Log error: Won't read from file!");
        }
        return null;
    }

    /**
     * Verifica se existe o arquivo
     */
    private void buildPath() {
        file = new File(path);
        if(!file.exists()) {
            if(file.getParentFile().mkdirs()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.err.println("Log Error: Not create file");
                }
            }
        }
    }

    /**
     * Pega o sistema operacional
     */
    private void getOS() {
        this.os = System.getProperty("os.name").toLowerCase();
    }

    /**
     * Se Windows
     * @return Windows
     */
    private boolean isWindows() {
        return this.os.indexOf("win") >= 0;
    }

    /**
     * Se Mac
     * @return Mac
     */
    private boolean isMac() {
        return this.os.indexOf("mac") >= 0;
    }

    /**
     * Se Linux ou Unix
     * @return Linux ou Unix
     */
    private boolean isUnix() {
        return this.os.indexOf("nix") >= 0 || this.os.indexOf("nux") >= 0 || this.os.indexOf("aix") >= 0;
    }
}