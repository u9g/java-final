public class Player {
    private int weaponLevel, baseDamage;
    private double damageMultiplier, critChance;

    public Player(){
        weaponLevel = 1;
        baseDamage = 50;
        damageMultiplier = 1;
        critChance = 0;
    }

    public boolean tryKillMonster(int health) {
        boolean crit = Math.random() <= critChance;
        return getTotalDamage() * (crit ? 2 : 1) >= health; // multiply damage by 2 on crit
    }

    public double upgradeBaseStats(int stage) {
        weaponLevel++;
        int dmgIncrease = (int) (Math.random() * stage) + 1;
        System.out.println("+ " + dmgIncrease + " damage");
        baseDamage += dmgIncrease;
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

    public double getCritChance () {
        return critChance;
    }

    public void setCritChance (double n) {
        critChance = n;
    }

    @Override
    public String toString() {
        return "Player had a weapon level " + weaponLevel + ", it did " + getTotalDamage() + " damage.";
    }
}
