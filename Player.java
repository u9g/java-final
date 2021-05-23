public class Player {
    private int weaponDamage, weaponLevel;

    public Player(){
        weaponLevel = 1;
        weaponDamage = 50;
    }

    public boolean canKillMonster(int health) {
        return weaponDamage >= health;
    }

    public int upgradeWeapon() {
        weaponLevel++;
        weaponDamage += 50;
        return weaponDamage;
    }

    public int getWeaponLevel () {
        return weaponLevel;
    }

    public int getWeaponDamage () {
        return weaponDamage;
    }

    @Override
    public String toString() {
        return "Player had a weapon level " + weaponLevel + ", it did " + weaponDamage + " damage.";
    }
}
