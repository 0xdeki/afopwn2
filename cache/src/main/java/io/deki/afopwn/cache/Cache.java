package io.deki.afopwn.cache;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.deki.afopwn.cache.commons.Category;
import io.deki.afopwn.cache.item.Asset;
import io.deki.afopwn.cache.item.GenericAsset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    private final String BASE_DIR = "AllInOne" + File.separator;

    private Map<String, String> dictionary = new HashMap<>();

    private List<Asset> assets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new Cache().load();
    }

    public void load() throws IOException {
        loadDictionary();
        loadAssets();
        for (Asset asset : assets) {
            System.out.println(asset.toString());
        }
    }

    private void loadDictionary() throws IOException {
        for (String line : Files.readAllLines(resourceAsFile("en.txt").toPath())) {
            if (line.startsWith("\"")) {
                String key = line.split("=")[0];
                key = key.substring(1);
                key = key.substring(0, key.indexOf("\""));

                String value = line.split("=")[1];
                value = value.substring(value.indexOf("\"") + 1);
                value = value.substring(0, value.indexOf("\""));
                getDictionary().put(key, value);
            }
        }
    }

    private void loadAssets() throws IOException {
        StringBuilder indexes = new StringBuilder();
        for (String line : Files.readAllLines(resourceAsFile(BASE_DIR + "info.dat").toPath())) {
            indexes.append(line);
        }
        JsonObject index = JsonParser.parseString(indexes.toString()).getAsJsonObject();
        for (Map.Entry<String, JsonElement> set : index.entrySet()) {
            JsonArray entry = set.getValue().getAsJsonArray();
            Category category = Category.findByName(set.getKey());
            for (JsonElement element : entry) {
                StringBuilder file = new StringBuilder();
                for (String line : Files.readAllLines(resourceAsFile(BASE_DIR + set.getKey() + File.separator
                        + element.getAsJsonObject().get("name").getAsString() + File.separator + "data").toPath())) {
                    file.append(line);
                }
                JsonObject indexEntry = JsonParser.parseString(file.toString()).getAsJsonObject();
                getAssets().add(parseAsset(category, indexEntry));
            }
        }
    }

    private Asset parseAsset(Category category, JsonObject object) {
        Asset asset;

        switch (category) {
            default:
                asset = new GenericAsset();
                break;
        }

        asset.setType(category.getId());

        if (object.has("VER")) {
            asset.setVersion(object.get("VER").getAsInt());
        }

        if (object.has("ID")) {
            asset.setId(object.get("ID").getAsInt());
        }

        if (object.has("REQLV")) {
            asset.setRequiredLevel(object.get("REQLV").getAsInt());
        }

        if (object.has("PRC")) {
            asset.setPrice(object.get("PRC").getAsInt());
        }

        if (object.has("NAME")) {
            String string = object.get("NAME").getAsString();
            asset.setName(getDictionary().get(string));
            if (asset.getName() == null) {
                asset.setName(string);
            }
        }

        if (object.has("DES")) {
            String string = object.get("DES").getAsString();
            asset.setDescription(getDictionary().get(string));
            if (asset.getDescription() == null) {
                asset.setDescription(string);
            }
        }

        return asset;
    }

    public File resourceAsFile(String path) {
        return new File(System.getProperty("user.home") + File.separator + "afopwn" + File.separator + path);
        //return new File(getClass().getClassLoader().getResource(path).getFile());
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public List<Asset> getAssets() {
        return assets;
    }
}
