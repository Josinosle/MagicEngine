package com.josinosle.magicengines.mana;

public class ClientManaData {

    private static int playerMana, playerMaxMana;

    public static void setMana (int mana) {
        ClientManaData.playerMana = mana;
    }

    public static void setMaxMana (int maxMana) {
        ClientManaData.playerMaxMana = maxMana;
    }

    public static int getPlayerMana() {
        return playerMana;
    }

    public static int getPlayerMaxMana() {
        return playerMaxMana;
    }
}
