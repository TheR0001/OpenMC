package fr.communaywen.core.economy;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EconomyManager {
    private final Map<Player, Double> balances = new HashMap<>();

    public double getBalance(Player player) {
        return balances.getOrDefault(player, 0.0);
    }

    public void addBalance(Player player, double amount) {
        balances.put(player, getBalance(player) + amount);
    }

    public boolean withdrawBalance(Player player, double amount) {
        double balance = getBalance(player);
        if (balance >= amount) {
            balances.put(player, balance - amount);
            return true;
        } else {
            return false;
        }
    }

    public boolean transferBalance(Player from, Player to, double amount) {
        if (withdrawBalance(from, amount)) {
            addBalance(to, amount);
            return true;
        } else {
            return false;
        }
    }
}
