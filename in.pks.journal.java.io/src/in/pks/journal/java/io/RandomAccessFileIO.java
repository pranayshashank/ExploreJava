package in.pks.journal.java.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;


public class RandomAccessFileIO {
    public static void main(String[] args) {
        readWriteFile("/Users/pranay/Downloads/text-B69A282BB77D-1.txt");
    }

    public static List<String> readWriteFile(String filePath){

        Path path = Paths.get(filePath);
        

        return Collections.emptyList();
    }
}
