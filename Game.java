import java.util.Scanner;

class Game {
    public static final boolean DEBUG = true;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ContinueDemo(input);
        Backstory(input);
        Intro(input);
        PlayerProgression p = new PlayerProgression();
        Player player = new Player();

        System.out.println("Starting at " + p.getStageName() + " with a level " + player.getWeaponLevel() + " weapon.");
        while (p.getStage() != PlayerProgression.MAX_STAGE + 1){
            AttackMonster(input, p, player);
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("You have successfully finished the forest of monsters. Pat yourself on the back!");
        System.out.println(p.toString());
        System.out.println(player.toString());
    }

    public static void ContinueDemo (Scanner input) {
        System.out.println("Throughtout this game, there will sometimes be prompts where you will have to click enter to continue.");
        System.out.println("These prompts will be denoted by ending with >>.");
        System.out.println("When seeing these symbols at the end of a line, simply press enter to continue. >>");
        input.nextLine();
        System.out.print("See? Very simple, anyways, enjoy the game! >>");
        input.nextLine();
        for (int i = 0; i < 20; i++){
            System.out.println();
        }
    }

    public static void Backstory(Scanner input) {
        System.out.println("It's finally that time, that you have been training for all your life. >>");
        input.nextLine();
        System.out.println("Your parents think you are ready for your own adventure in the forest of monsters. >>");
        input.nextLine();
        System.out.println("Your parents have given you a wooden sword and their hope in your future. >>");
        input.nextLine();
    }

    public static void Intro(Scanner input) {
        System.out.println("To start: a bit about the forest.");
        System.out.println("You will fight waves of mobs with slowly increasing health, each having a chance of giving you a better weapon.");
        System.out.println("If you fail a round, don't worry, you gain luck. When you get 1.0 luck, you will skip a wave, and your luck will be reset down to 0.");
        System.out.println("Good luck :) >>");
        input.nextLine();
    }
    
    public static void AttackMonster(Scanner input, PlayerProgression p, Player player) {
        int monsterHealth = p.startWave();
        boolean finishedWave = player.canKillMonster(monsterHealth);
        
        if (Math.random() <= (.1 * p.getStage())){ // 10% to upgrade weapon
            System.out.println("Congrats, your weapon just leveled up. It is now " + player.getWeaponLevel() + " level!");
            player.upgradeWeapon();
        }

        if (DEBUG) {
            System.out.println("Weapon Damage: " + player.getWeaponDamage() + " | Monster Health: " + monsterHealth);
        }

        if (finishedWave) {
            p.finishWave();

            System.out.println("You " 
            + (finishedWave ? "finished" : "didn't finish") 
            + " the wave. You have " + (PlayerProgression.WAVES_PER_STAGE - p.getWave() + 1) 
            + " waves to finish " + p.getStageName() + " >>");
        } else {
            p.failWave();

            System.out.println("You have " + (PlayerProgression.WAVES_PER_STAGE - p.getWave() + 1) 
            + " waves to finish " + p.getStageName() + " >>");
        }


        input.nextLine();

    }
}