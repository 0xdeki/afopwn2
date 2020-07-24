package io.deki.afopwn.commons;

import io.deki.afopwn.dto.Account;
import io.deki.afopwn.dto.StoreItem;
import io.deki.afopwn.task.account.Register;
import io.deki.afopwn.task.store.LoadSkills;
import io.deki.afopwn.task.store.LoadStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AfoCommons {

    private static List<StoreItem> itemStore = Collections.synchronizedList(new ArrayList<>());

    private static List<StoreItem> skillStore = Collections.synchronizedList(new ArrayList<>());

    public static Optional<StoreItem> getItem(String name) {
        return getItem(item -> item.getAsset() != null && item.getAsset().getName().equalsIgnoreCase(name));
    }

    public static Optional<StoreItem> getSkill(String name) {
        return getSkill(item -> item.getAsset() != null && item.getAsset().getName().equalsIgnoreCase(name));
    }

    public static Optional<StoreItem> getItem(Predicate<StoreItem> predicate) {
        for (StoreItem storeItem : getItemStore()) {
            if (predicate.test(storeItem)) {
                return Optional.of(storeItem);
            }
        }
        return Optional.empty();
    }

    public static Optional<StoreItem> getSkill(Predicate<StoreItem> predicate) {
        for (StoreItem storeItem : getSkillStore()) {
            if (predicate.test(storeItem)) {
                return Optional.of(storeItem);
            }
        }
        return Optional.empty();
    }

    public static List<StoreItem> getItemStore() {
        while (itemStore.size() < 1) {
            Account decoy = new Account();
            decoy.postTask(new Register());
            LoadStore store = new LoadStore();
            decoy.postTask(store);
            itemStore = store.getItems();
        }
        return itemStore;
    }

    public static List<StoreItem> getSkillStore() {
        while (skillStore.size() < 1) {
            Account decoy = new Account();
            decoy.postTask(new Register());
            LoadSkills store = new LoadSkills();
            decoy.postTask(store);
            skillStore = store.getItems();
        }
        return skillStore;
    }
}
