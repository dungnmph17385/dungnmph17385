/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class XImage {
    public static Image getAppIcon() {
        URL url = XImage.class.getResource("src/image_icon/CD.png");
        return new ImageIcon(url).getImage();
    }

    public static void save(File src) {
        //tao thu muc logo neu chua ton tai
        File dst = new File("src/Logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            //copy file vao thu muc logos
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error save Image");
            e.printStackTrace();
        }
    }

    public static ImageIcon read(String fileName) {
        File path = new File("src/Logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
