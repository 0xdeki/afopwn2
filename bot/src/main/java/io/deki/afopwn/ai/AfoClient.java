package io.deki.afopwn.ai;

import io.deki.afopwn.cache.commons.Category;
import io.deki.afopwn.commons.AfoCommons;
import io.deki.afopwn.commons.Random;
import io.deki.afopwn.commons.Time;
import io.deki.afopwn.dto.*;
import io.deki.afopwn.dto.game.*;
import io.deki.afopwn.repository.RepositoryContext;
import io.deki.afopwn.task.account.Login;
import io.deki.afopwn.task.account.Register;
import io.deki.afopwn.task.account.Rename;
import io.deki.afopwn.task.arena.Fight;
import io.deki.afopwn.task.contest.EnrollContest;
import io.deki.afopwn.task.explore.Explore;
import io.deki.afopwn.task.group.DonateItem;
import io.deki.afopwn.task.group.GetGroupByName;
import io.deki.afopwn.task.group.JoinGroup;
import io.deki.afopwn.task.group.LeaveGroup;
import io.deki.afopwn.task.inventory.EquipWeapon;
import io.deki.afopwn.task.inventory.UnequipWeapon;
import io.deki.afopwn.task.inventory.UseItem;
import io.deki.afopwn.task.land.*;
import io.deki.afopwn.task.social.AddFriend;
import io.deki.afopwn.task.store.BuyItem;
import io.deki.afopwn.task.task.DailyTask;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Getter
@Setter
public class AfoClient implements Runnable {

    private boolean working = true;

    private Account account;

    public AfoClient(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while (working) {
            try {
                Time.sleep(loop());
                RepositoryContext.accountRepository.save(getAccount());
            } catch (Exception e) {
                e.printStackTrace();
                Time.sleep(1000);
            }
        }
    }

    public int loop() {
        if (getAccount().getSessionId() == null) {
            if (getAccount().getUsername() == null) {
                getAccount().postTask(new Register());
            } else {
                getAccount().postTask(new Login());
            }
            return 2000;
        }

        if (getAccount().getAvatarInfo().getNextDailyReward() < System.currentTimeMillis()) {
            getAccount().postTask(new DailyTask());
            getAccount().postTask(new Login());
            return 1000;
        }

        for (AvatarTask task : getAccount().getAvatarInfo().getTasks()) {
            if (handleTask(task)) {
                return 1000;
            }
        }

        if (handleChests()) {
            return 1000;
        }

        if (joinGuild("kule kids klubb")) {
            return 1000;
        }

        if (donateValuablesToGuild()) {
            return 1000;
        }

        if (findBestWeaponOrder()) {
            return 1000;
        }

        if (handleSkills()) {
            return 1000;
        }

        if (handleWeapons()) {
            return 1000;
        }

        if (handleMines()) {
            return 1000;
        }

        if (buyNewMine()) {
            return 1000;
        }

        if (unlockNewLand()) {
            return 1000;
        }

        if (getAccount().getAvatarInfo().getEnergy() < 1) {
            Time.sleep(60 * 60 * 1000);
            getAccount().postTask(new Login());
            return 1000;
        }

        for (ExploreMap map : getAccount().getAvatarInfo().getMaps()) {
            if (map.getReady() < System.currentTimeMillis()) {
                getAccount().postTask(new Explore(map.getId()));
                return 1000;
            }
        }

        if (getAccount().getAvatarInfo().getLevel() < 14) {
            getAccount().postTask(new Fight());
            return 1000;
        }

        return 10 * 60 * 1000;
    }

    private boolean buyNewMine() {
        int wood = 0, stone = 0, iron = 0;
        for (Land land : getAccount().getAvatarInfo().getLand()) {
            switch (land.getType()) {
                case 1:
                    ++wood;
                    break;
                case 2:
                    ++stone;
                    break;
                case 3:
                    ++iron;
                    break;
            }
        }
        if (getAccount().getAvatarInfo().getLevel() >= 20 && getAccount().getAvatarInfo().getGold() >= 2000
                && getAccount().getAvatarInfo().getIndexOfEmptyLand() != -1) {
            if (wood == 0 && stone == 0 && iron == 0) {
                getAccount().postTask(new BuyLand(Random.nextInt(1, 3)));
            } else if (wood < 1) {
                getAccount().postTask(new BuyLand(1));
            } else if (stone < 1) {
                getAccount().postTask(new BuyLand(2));
            } else if (iron < 1) {
                getAccount().postTask(new BuyLand(3));
            } else if (wood < 2) {
                getAccount().postTask(new BuyLand(1));
            } else if (stone < 2) {
                getAccount().postTask(new BuyLand(2));
            } else if (iron < 2) {
                getAccount().postTask(new BuyLand(3));
            }
            return true;
        }

        return false;
    }

    private boolean unlockNewLand() {
        if (getAccount().getAvatarInfo().getLevel() >= 20 && getAccount().getAvatarInfo().getLand().size() < 2) {
            getAccount().postTask(new FightLandRival());
            return true;
        }

        return false;
    }

    private boolean handleMines() {
        for (Land land : getAccount().getAvatarInfo().getLand()) {
            if (land.getStarted() != 0 && land.getFinished() < System.currentTimeMillis()) {
                getAccount().postTask(new CollectLandProduction(land.getIndex()));
                return true;
            }
            if (land.getType() >= 1 && land.getType() <= 3 && land.getStarted() == 0) {
                getAccount().postTask(new StartLandProduction(land.getIndex()));
                return true;
            }
        }

        return false;
    }

    private boolean joinGuild(String name) {
        if (getAccount().getAvatarInfo().getLevel() < 20 || (getAccount().getAvatarInfo().getGroup().getName() != null
                && getAccount().getAvatarInfo().getGroup().getName().equalsIgnoreCase(name))) {
            return false;
        }

        if (getAccount().getAvatarInfo().getGroup().getName() != null) {
            getAccount().postTask(new LeaveGroup());
            return true;
        }

        GetGroupByName getGroup = new GetGroupByName(name);
        getAccount().postTask(getGroup);
        if (getGroup.getId() != null) {
            getAccount().postTask(new JoinGroup(getGroup.getId()));
            return true;
        }

        return false;
    }

    private boolean donateValuablesToGuild() {
        if (getAccount().getAvatarInfo().getLevel() < 20 || getAccount().getAvatarInfo().getGroup().getName() == null) {
            return false;
        }

        AtomicBoolean donated = new AtomicBoolean();
        getAccount().getAvatarInfo().getBagItem(item ->
                item.getAsset().getName().endsWith(" Gem") || item.getAsset().getName().endsWith(" Crystal"))
                .ifPresent(item -> {
                    getAccount().postTask(new DonateItem(getAccount().getAvatarInfo().getGroup().getId(), item.format(),
                            item.getAmount()));
                    donated.set(true);
                });

        return donated.get();
    }

    private boolean handleChests() {
        if (getAccount().getAvatarInfo().getLevel() < 5) {
            return false;
        }

        return useItem("wooden chest");
    }

    private boolean findBestWeaponOrder() {
        Map<BagItem, Integer> weapons = new LinkedHashMap<>();
        for (BagItem weapon : getAccount().getAvatarInfo().getWeapons()) {
            weapons.put(weapon, weapon.getAsset().getPrice());
        }

        outer:
        for (BagItem item : getAccount().getAvatarInfo().getBag()) {
            if (item.getType() == Category.WEAPON.getId()
                    && item.getAsset().getRequiredLevel() <= getAccount().getAvatarInfo().getLevel()) {
                for (Map.Entry<BagItem, Integer> entry : weapons.entrySet()) {
                    if (entry.getKey().getId() == item.getId()) {
                        continue outer;
                    }
                }
                weapons.put(item, item.getAsset().getPrice());
            }
        }

        //sort weapons by price
        weapons = weapons.entrySet().stream()
                .sorted(Map.Entry.<BagItem, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        int index = 0;
        for (Map.Entry<BagItem, Integer> entry : weapons.entrySet()) {
            if (index == 8) {
                return false;
            }
            if (getAccount().getAvatarInfo().getWeapons().size() <= index) {
                getAccount().postTask(new EquipWeapon(entry.getKey().format(), false));
                return true;
            }
            if (getAccount().getAvatarInfo().getWeapons().get(index).getId() != entry.getKey().getId()) {
                getAccount().postTask(new UnequipWeapon(getAccount().getAvatarInfo().getWeapons().get(index).getId()));
                return true;
            }
            ++index;
        }

        return false;
    }

    private boolean handleSkills() {
        if (getAccount().getAvatarInfo().getLevel() < 5) {
            return false;
        }

        if (!getAccount().getAvatarInfo().getSkill("counter attack master").isPresent()) {
            return buySkill("counter attack master");
        }

        if (!getAccount().getAvatarInfo().getSkill("batter master").isPresent()) {
            return buySkill("batter master");
        }

        if (!getAccount().getAvatarInfo().getSkill("wrath").isPresent()) {
            return buySkill("wrath");
        }

        return false;
    }

    private boolean handleWeapons() {
        if (getAccount().getAvatarInfo().getLevel() < 6) {
            return false;
        }

        if (!hasWeapon("samurai sword")) {
            return buyItem("samurai sword");
        }

        if (!hasWeapon("trident")) {
            return buyItem("trident");
        }

        if (!hasWeapon("bow")) {
            return buyItem("bow");
        }

        if (!hasWeapon("rusty axe")) {
            return buyItem("rusty axe");
        }

        if (!hasWeapon("stone hammer")) {
            return buyItem("stone hammer");
        }

        return false;
    }

    private boolean handleTask(AvatarTask task) {
        switch (task.getId()) {
            //buy a helmet
            case 8:
                return buyItem("leather helmet");
            //learn a skill
            case 1004:
                return buySkill("last blood");
            //equip a helmet
            case 2005:
                return useItem("leather helmet");
            //fight once
            case 4007:
                getAccount().postTask(new Fight());
                return true;
            //name yourself
            case 4008:
                getAccount().postTask(new Rename());
                return true;
            //buy a weapon
            case 4009:
                return buyItem("dagger");
            //enroll in a contest
            case 7002:
                getAccount().postTask(new EnrollContest());
                return true;
            //buy a pet
            case 7003:
                return buyItem("cheetah");
            //add a friend
            case 21416556:
                getAccount().postTask(new AddFriend("endres eksperiment"));
                return true;
            //use an energy potion
            case 1593444988:
                return useItem("energy potion");
            //buy an energy potion
            case 1593945400:
                return buyItem("energy potion");
            default:
                return false;
        }
    }

    private boolean hasWeapon(String weapon) {
        return getAccount().getAvatarInfo().getWeapon(weapon).isPresent()
                || getAccount().getAvatarInfo().getBagItem(weapon).isPresent();
    }

    private boolean useItem(String name) {
        AtomicBoolean used = new AtomicBoolean(false);
        getAccount().getAvatarInfo().getBagItem(name).ifPresent(item -> used.set(useItem(item)));
        return used.get();
    }

    private boolean buyItem(String name) {
        AtomicBoolean bought = new AtomicBoolean(false);
        AfoCommons.getItem(name).ifPresent(item -> bought.set(buyItem(item)));
        return bought.get();
    }

    private boolean buySkill(String name) {
        AtomicBoolean bought = new AtomicBoolean(false);
        AfoCommons.getSkill(name).ifPresent(item -> bought.set(buyItem(item)));
        return bought.get();
    }

    private boolean useItem(BagItem item) {
        getAccount().postTask(new UseItem(item.format()));
        return true;
    }

    private boolean buyItem(StoreItem item) {
        if (getAccount().getAvatarInfo().getGold() < item.getGoldCost()
                || getAccount().getAvatarInfo().getDiamonds() < item.getDiamondCost()) {
            return false;
        }

        getAccount().postTask(new BuyItem(item.getStoreId()));
        return true;
    }
}
