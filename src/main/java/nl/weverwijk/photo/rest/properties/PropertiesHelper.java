package nl.weverwijk.photo.rest.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    public static final String PHOTO_BASE_LOCATION = "/Users/rvanweverwijk/Projects/rweverwijk/restPhotoService/src/main/webapp/images";


    public static Properties getProperties(String dir) {
        // Read properties file.
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PHOTO_BASE_LOCATION + (dir != null ? dir : "") + "/.rwAlbum"));
        } catch (IOException e) {
        }
        return properties;
    }

    public static void saveProperties(String dir, Properties properties) {
        // Write properties file.
        try {
            properties.store(new FileOutputStream(PHOTO_BASE_LOCATION + (dir != null ? dir : "") + "/.rwAlbum"), null);
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        Properties properties = PropertiesHelper.getProperties(null);
        properties.put("test", "testValue");
        PropertiesHelper.saveProperties(null, properties);
        System.out.println("saven propertie kost: " + (System.currentTimeMillis() - currentTimeMillis));

        properties = PropertiesHelper.getProperties(null);
        String value = properties.getProperty("IMG_3004.jpg.name");
        System.out.println(value);
    }
}
