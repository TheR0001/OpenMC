package fr.communaywen.core.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EconomyData {
    private final File file;
    private final FileConfiguration config;

    public EconomyData(File dataFolder) {
        file = new File(dataFolder, "economy.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveBalances(Map<Player, Double> balances) {
        for (Map.Entry<Player, Double> entry : balances.entrySet()) {
            config.set(entry.getKey().getUniqueId().toString(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Player, Double> loadBalances() {
        Map<Player, Double> balances = new HashMap<>();
        for (String key : config.getKeys(false)) {
            balances.put(Bukkit.getPlayer(UUID.fromString(key)), config.getDouble(key));
        }
        return balances;
    }
}
