public class Player {
    private int weaponLevel, baseDamage;
    private double damageMultiplier;

    public Player(){
        weaponLevel = 1;
        baseDamage = 50;
        damageMultiplier = 1;
    }

    public boolean canKillMonster(int health) {
        return getTotalDamage() >= health;
    }

    public double upgradeBaseStats() {
        weaponLevel++;
        baseDamage += 10;
        return getTotalDamage();
    }

    public double getTotalDamage() {
        return damageMultiplier * baseDamage;
    }

    public int getWeaponLevel () {
        return weaponLevel;
    }

    public double getDamageMultiplier () {
        return damageMultiplier;
    }

    public void setDamageMultiplier (double n) {
        damageMultiplier = n;
    }

    @Override
    public String toString() {
        return "Player had a weapon level " + weaponLevel + ", it did " + getTotalDamage() + " damage.";
    }
}
