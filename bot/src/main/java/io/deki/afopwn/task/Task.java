package io.deki.afopwn.task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.deki.afopwn.cache.commons.Category;
import io.deki.afopwn.cache.item.Asset;
import io.deki.afopwn.commons.CryptoUtil;
import io.deki.afopwn.dto.*;
import io.deki.afopwn.repository.RepositoryContext;
import io.deki.afopwn.task.task.RedeemTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Task {

    public final String URL_BASE = "http://avafight.appspot.com/";

    private Logger logger = LoggerFactory.getLogger(Task.class);

    private Account account;

    public abstract String directory();

    public abstract int act();

    public abstract JsonObject construct();

    public abstract void response(JsonObject object);

    public void post() throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .build();

        HttpPost post = new HttpPost(URL_BASE + directory());

        if (getAccount().getSessionId() != null) {
            post.addHeader("Cookie", "JSESSIONID=" + getAccount().getSessionId());
            post.addHeader("Cookie2", "$Version=1");
        }

        post.setEntity(new UrlEncodedFormEntity(generatePairs()));

        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equals("JSESSIONID")) {
                account.setSessionId(cookie.getValue());
            }
        }

        int code = response.getStatusLine().getStatusCode();
        String data = EntityUtils.toString(entity);

        if (data.length() == 0) {
            return;
        }

        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
        if (object.has("MAIN") && object.getAsJsonObject("MAIN").has("AVA")) {
            JsonObject avatar = object.getAsJsonObject("MAIN").getAsJsonObject("AVA");
            handleAvatarInfo(avatar);
        }
        if (object.has("HB") && object.getAsJsonObject("HB").has("PRG")) {
            for (JsonElement element : object.getAsJsonObject("HB").getAsJsonArray("PRG")) {
                getAccount().postTask(new RedeemTask(element.getAsJsonObject().get("KEY").getAsString()));
            }
        }

        response(object);

        if (object.has("MAIN") && object.getAsJsonObject("MAIN").has("ERR")) {
            int errorCode = object.getAsJsonObject("MAIN").get("ERR").getAsInt();
            //logged in from another device
            if (errorCode == 12) {
                getAccount().setSessionId(null);
            }
            return;
        }
    }

    private void handleAvatarInfo(JsonObject object) {
        if (object.has("NAME")) {
            getAccount().setUsername(object.get("NAME").getAsString());
        }
        if (object.has("PWD")) {
            getAccount().setPasswordHash(object.get("PWD").getAsString());
        }

        handleAvatarInfo(object, getAccount().getAvatarInfo());
    }

    public void handleAvatarInfo(JsonObject object, AvatarInfo info) {
        if (object.has("NAME")) {
            info.setName(object.get("NAME").getAsString());
        }

        if (object.has("KEY")) {
            info.setKey(object.get("KEY").getAsString());
        }

        if (object.has("LST")) {
            info.setLastLogin(object.get("LST").getAsLong());
        }

        if (object.has("DNXT")) {
            info.setNextDailyReward(object.get("DNXT").getAsLong());
        }

        if (object.has("EXP")) {
            info.setExp(object.get("EXP").getAsInt());
        }

        if (object.has("LV")) {
            info.setLevel(object.get("LV").getAsInt());
        }

        if (object.has("ENGY")) {
            info.setEnergy(object.get("ENGY").getAsInt());
        }

        if (object.has("GLD")) {
            info.setGold(object.get("GLD").getAsInt());
        }

        if (object.has("DMON")) {
            info.setDiamonds(object.get("DMON").getAsInt());
        }

        if (object.has("SKIN")) {
            info.setSkin(object.get("SKIN").getAsInt());
        }

        if (object.has("AGL")) {
            info.setAgility(object.get("AGL").getAsInt());
        }

        if (object.has("HP")) {
            info.setHp(object.get("HP").getAsInt());
        }

        if (object.has("STR")) {
            info.setStrength(object.get("STR").getAsInt());
        }

        if (object.has("SPD")) {
            info.setSpeed(object.get("SPD").getAsInt());
        }

        if (object.has("ITEM")) {
            info.setEquipment(new ArrayList<>());
            String[] split = object.get("ITEM").getAsString().split(":");
            for (String data : split) {
                int id = Integer.parseInt(data);
                if (id < 1) {
                    continue;
                }
                BagItem item = new BagItem();
                item.setType(Category.EQUIPMENT.getId());
                item.setId(id);
                item.setAmount(1);
                info.getEquipment().add(item);
            }
        }

        if (object.has("WPN")) {
            info.setWeapons(new ArrayList<>());
            for (JsonElement element : object.getAsJsonArray("WPN")) {
                BagItem item = new BagItem();
                item.setType(Category.WEAPON.getId());
                item.setId(Integer.parseInt(element.getAsString()));
                item.setAmount(1);
                info.getWeapons().add(item);
            }
        }

        if (object.has("BAG")) {
            info.setBag(new ArrayList<>());
            for (JsonElement element : object.getAsJsonArray("BAG")) {
                String data = element.getAsString();
                String[] split = data.split(":");
                BagItem item = new BagItem();
                item.setType(Integer.parseInt(split[0]));
                item.setId(Integer.parseInt(split[1]));
                item.setAmount(Integer.parseInt(split[2]));
                info.getBag().add(item);
            }
        }

        if (object.has("SKL")) {
            info.setSkills(new ArrayList<>());
            for (JsonElement element : object.getAsJsonArray("SKL")) {
                int id = Integer.parseInt(element.getAsString());
                for (Asset asset : RepositoryContext.cache.getAssets()) {
                    if (asset.getType() == Category.SKILL.getId() && asset.getId() == id) {
                        info.getSkills().add(asset);
                    }
                }
            }
        }

        if (object.has("INST")) {
            info.setMaps(new ArrayList<>());
            for (JsonElement element : object.getAsJsonArray("INST")) {
                JsonObject mapObject = element.getAsJsonObject();
                ExploreMap map = new ExploreMap();
                if (mapObject.has("ID")) {
                    map.setId(mapObject.get("ID").getAsInt());
                }
                if (mapObject.has("STEP")) {
                    map.setStep(mapObject.get("STEP").getAsInt());
                }
                if (mapObject.has("NTS")) {
                    map.setReady(mapObject.get("NTS").getAsLong());
                }
                info.getMaps().add(map);
            }
            if (getAccount().getAvatarInfo().getLevel() >= 14
                    && !getAccount().getAvatarInfo().getMap(1).isPresent()) {
                ExploreMap hellfire = new ExploreMap();
                hellfire.setId(1);
                getAccount().getAvatarInfo().getMaps().add(hellfire);
            }
            if (getAccount().getAvatarInfo().getLevel() >= 24
                    && !getAccount().getAvatarInfo().getMap(2).isPresent()) {
                ExploreMap wildlands = new ExploreMap();
                wildlands.setId(2);
                getAccount().getAvatarInfo().getMaps().add(wildlands);
            }
        }

        if (object.has("TSK")) {
            info.setTasks(new ArrayList<>());
            for (JsonElement element : object.getAsJsonArray("TSK")) {
                JsonObject taskObject = element.getAsJsonObject();
                AvatarTask task = new AvatarTask();

                if (taskObject.has("ID")) {
                    task.setId(taskObject.get("ID").getAsInt());
                }

                if (taskObject.has("NAME")) {
                    task.setName(taskObject.get("NAME").getAsString());
                }

                if (taskObject.has("DES")) {
                    task.setDescription(taskObject.get("DES").getAsString());
                }

                if (taskObject.has("KEY")) {
                    task.setKey(taskObject.get("KEY").getAsString());
                }

                if (taskObject.has("PRG")) {
                    task.setProgress(taskObject.get("PRG").getAsInt());
                }

                info.getTasks().add(task);
            }
        }
    }

    private List<NameValuePair> generatePairs() {
        List<NameValuePair> params = new ArrayList<>();
        JsonObject json = construct();
        if (json == null) {
            json = new JsonObject();
        }

        long time = getTime();
        json.addProperty("TS", time);
        String data = json.toString();

        params.add(new BasicNameValuePair("ACT", String.valueOf(act())));
        params.add(new BasicNameValuePair("CRC", CryptoUtil.md5(time + getAccount().getAvatarInfo().getKey() + "ZMLM601")));
        params.add(new BasicNameValuePair("CTT", data));

        return params;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
