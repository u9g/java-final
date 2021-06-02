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
        while (p.getStage() != PlayerProgression.MAX_STAGE + 1) {
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
        ClearWindow();
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

    public static void ClearWindow() {
        for (int i = 0; i < 100; i++){
            System.out.println();
        }
    }
    
    public static void AttackMonster(Scanner scanner, PlayerProgression p, Player player) {
        ClearWindow();
        int monsterHealth = p.startWave();
        boolean finishedWave = player.canKillMonster(monsterHealth);
        
        if (DEBUG && monsterHealth > 0) {
            System.out.println("Weapon Damage: " + truncate(player.getTotalDamage(), 0) + " | Monster Health: " + monsterHealth);
            String contMsg = "Type \"continue\" to go to try this wave or \"skip\" to use luck to skip this round.";
            System.out.println(contMsg);
            System.out.print(": ");
            String commandInput = scanner.nextLine();
            
            if (!commandInput.equals("continue")) {
                commandInput = awaitInput(scanner, new String[] { "continue" }, contMsg);
            }
        } else { // monster had 0 health (skipped)
            System.out.println("Type \"continue\" to go to the next wave.");
            System.out.print(": ");
            String commandInput = scanner.nextLine();
            
            if (!commandInput.equals("continue")) {
                commandInput = awaitInput(scanner, new String[] { "continue" }, "Type \"continue\" to go to the next wave.");
            }
        }
            
        if (finishedWave) {
            p.finishWave();
            player.upgradeBaseStats(p.getStage());
            System.out.println("You " 
            + (finishedWave ? "finished" : "didn't finish") 
            + " the wave. You have " + (PlayerProgression.WAVES_PER_STAGE - p.getWave() + 1) 
            + " waves to finish " + p.getStageName());
        } else {
            p.failWave();
            player.upgradeBaseStats(p.getStage());
            System.out.println("You didn't finish the wave. "
            + "You have " + (PlayerProgression.WAVES_PER_STAGE - p.getWave() + 1) 
            + " waves to finish " + p.getStageName());
        }
        
        ClearWindow();
        if (Math.random() <= .4) { // 40% to find a new artifact
            // TODO: crit chance
            int luckMult = (int) (Math.random() * p.getStage()) + 1;
            double damageMult = truncate((Math.random() * p.getStage()) * .1, 2) + 1;
            double waveSkipChance = truncate(Math.random() * p.getStage(), 2)  / 100;
            if (luckMult != p.getLuckMultiplier() || damageMult != player.getDamageMultiplier() || waveSkipChance != p.getWaveSkipChance()) {   
                System.out.println("Congrats, you found a artifact on the ground.\n\nIf you chose to equip this artifact, your new stats will:");
                System.out.println("Luck Multiplier: " + luckMult + " | Base Damage Multiplier: " + damageMult + " | Wave Skip Chance: " + waveSkipChance);
                System.out.println();
                System.out.println("Your old artifact stats: ");
                System.out.println("Luck Multiplier: " + p.getLuckMultiplier() + " | Base Damage Multiplier: " + player.getDamageMultiplier() + " | Wave Skip Chance: " + p.getWaveSkipChance());
                System.out.println();
                System.out.println("To pickup the item, type \"upgrade\", to skip this item, type \"skip\".");

                String artifactResponse = "";
                if (!artifactResponse.equals("upgrade") && !artifactResponse.equals("skip")) {
                    artifactResponse = awaitInput(scanner, new String[] {"upgrade", "skip"}, "To pickup the item, type \"upgrade\", to skip this item, type \"skip\".");
                }
                
                if (artifactResponse.equals("upgrade")) {
                    p.setLuckMultiplier(luckMult);
                    player.setDamageMultiplier(damageMult);
                    p.setWaveSkipChance(waveSkipChance);
                    System.out.println("Good choice, you equipt the new artifact.");
                } else {
                    System.out.println("You skipped this artifact, don't worry, there will probably be more along the way.");
                }
            }   
        }
        
    }

    public static String awaitInput(Scanner scanner, String[] inputs, String continueMessage) {
        String commandInput = "";
        while (true) {
            for (String input : inputs) {
                if (input.equals(commandInput)){
                    return commandInput;
                }
            }
            System.out.println("Invalid command.");
            System.out.println(continueMessage);
            System.out.print(": ");
            commandInput = scanner.nextLine();
        }
    }
    
    public static double truncate(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;    
    }
}