package swaglabs.utils;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtil {

    public static String getJsonData(String filename, String... keys) {
        InputStream inputStream = DataUtil.class.getClassLoader().getResourceAsStream(filename + ".json");

        if (inputStream == null) {
            throw new RuntimeException("❌ الملف غير موجود في resources: " + filename + ".json");
        }

        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
        JsonObject currentObject = jsonElement.getAsJsonObject();

        for (int i = 0; i < keys.length - 1; i++) {
            if (currentObject.has(keys[i])) {
                currentObject = currentObject.getAsJsonObject(keys[i]);
            } else {
                throw new RuntimeException("❌ المفتاح غير موجود: " + keys[i]);
            }
        }

        String lastKey = keys[keys.length - 1];
        if (currentObject.has(lastKey)) {
            return currentObject.get(lastKey).getAsString();
        } else {
            throw new RuntimeException("❌ المفتاح الأخير غير موجود: " + lastKey);
        }
    }
}

