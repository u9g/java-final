import java.util.Scanner;

class Game {
    public static void main(String[] args) {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);
        while (playAgain) {
            ContinueDemo(scanner);
            Backstory(scanner);
            Intro(scanner);
            PlayerProgression p = new PlayerProgression();
            Player player = new Player();
            
            while (p.getStage() != PlayerProgression.MAX_STAGE + 1) {
                AttackMonster(scanner, p, player);
            }
            
            System.out.println();
            System.out.println();
            System.out.println();
            
            System.out.println("You have successfully finished the forest of monsters. Pat yourself on the back!");
            System.out.println(p.toString());
            System.out.println(player.toString());

            String contMsg = "Would you like to play again? (type \"yes\" to play again, \"no\" to end the game.)";
            System.out.println(contMsg);
            System.out.print(": ");
            playAgain = AwaitInput(scanner, new String[] { "yes", "no" }, contMsg).equals("yes"); // continue when response is equal to yes
        }
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
        System.out.println("You will fight waves of monsters with slowly increasing health, each having a chance of giving you a better weapon.");
        System.out.println("If you fail a round, don't worry, you gain luck. When you get 1.0 luck, you will be able to skip a wave, and your luck will be reset down to 0. >>");
        input.nextLine();
        System.out.println("Each round you will be given the choice to attack the monster, or to use luck points to skip the monster.");
        System.out.println("If you are a bit below the health of a mob, you can try to count on the critical hit chance from an artifact you got.");
        System.out.println("If the critical hit happens, you do double damage for that hit.");
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
        boolean finishedWave = player.tryKillMonster(monsterHealth);
        
        if (monsterHealth > 0 && p.canUseLuck()) {
            System.out.println("Weapon Damage: " + Truncate(player.getTotalDamage(), 0) + " | Monster Health: " + monsterHealth + " | Stage: " + p.getStage() + " / " + PlayerProgression.MAX_STAGE + " | Wave: " + (p.getWave() + 1) + " / " + (PlayerProgression.WAVES_PER_STAGE));
            String contMsg = "Type \"try\" to try this wave or \"skip\" to use luck to skip this round.";
            System.out.println(contMsg);
            System.out.print(": ");
            String commandInput = scanner.nextLine();

            if (!commandInput.equals("try") && !commandInput.equals("skip")) {
                commandInput = AwaitInput(scanner, new String[] { "try", "skip" }, contMsg);
            }
                            
            if (commandInput.equals("try")) {
                if (finishedWave) FinishedWave(player, p, scanner);
                else FailWave(player, p, scanner);
            } else if (commandInput.equals("skip")) {
                p.useLuck();
            }
        } else if (monsterHealth > 0) {
            System.out.println("Weapon Damage: " + Truncate(player.getTotalDamage(), 0) + " | Monster Health: " + monsterHealth + " | Stage: " + p.getStage() + " / " + PlayerProgression.MAX_STAGE + " | Wave: " + (p.getWave() + 1) + " / " + (PlayerProgression.WAVES_PER_STAGE));
            String contMsg = "Type \"try\" to try this wave. (" + Truncate(p.getLuck(), 2) + " / 1.0 luck for next skip)";
            System.out.println(contMsg);
            System.out.print(": ");
            String commandInput = scanner.nextLine();

            if (!commandInput.equals("try")) {
                commandInput = AwaitInput(scanner, new String[] { "try" }, contMsg);
            }

            if (finishedWave) FinishedWave(player, p, scanner);
            else FailWave(player, p, scanner);
        } else { // monster had 0 health (skipped)
            System.out.println("Type \"continue\" to go to the next wave.");
            System.out.print(": ");
            String commandInput = scanner.nextLine();
            
            if (!commandInput.equals("continue")) {
                commandInput = AwaitInput(scanner, new String[] { "continue" }, "Type \"continue\" to go to the next wave.");
            }
        }

        ClearWindow();

        if (Math.random() <= .25) { // 25% to find a new artifact
            int luckMult = (int) (Math.random() * (p.getStage() + 1) + 1);
            double damageMult = (((int)(Math.random() * p.getStage() + 1)) / 10.0) + 1;
            double waveSkipChance = ((int)(Math.random() * p.getStage() + 1)) / 100.0;
            double critChance = ((int)(Math.random() * p.getStage())) / 10.0;
            if (luckMult != p.getLuckMultiplier() || damageMult != player.getDamageMultiplier() || waveSkipChance != p.getWaveSkipChance() || critChance != player.getCritChance()) {   
                System.out.println("Congrats, you found an artifact on the ground.\n\nIf you chose to equip this artifact, your new stats will:");
                System.out.println("Luck Multiplier: " + luckMult + " | Base Damage Multiplier: " + damageMult + " | Wave Skip Chance: " + waveSkipChance + " | Critical chance: " +  critChance);
                System.out.println();
                System.out.println("Your old artifact stats: ");
                System.out.println("Luck Multiplier: " + p.getLuckMultiplier() + " | Base Damage Multiplier: " + player.getDamageMultiplier() + " | Wave Skip Chance: " + p.getWaveSkipChance() + " | Critical chance: " + player.getCritChance());
                System.out.println();
                System.out.println("To pickup the item, type \"upgrade\", to skip this item, type \"skip\".");
                System.out.print(": ");

                String artifactResponse = scanner.nextLine();
                if (!artifactResponse.equals("upgrade") && !artifactResponse.equals("skip")) {
                    artifactResponse = AwaitInput(scanner, new String[] {"upgrade", "skip"}, "To pickup the item, type \"upgrade\", to skip this item, type \"skip\".");
                }
                
                if (artifactResponse.equals("upgrade")) {
                    p.setLuckMultiplier(luckMult);
                    p.setWaveSkipChance(waveSkipChance);
                    player.setDamageMultiplier(damageMult);
                    player.setCritChance(critChance);

                    System.out.println("Good choice, you equipt the new artifact.");
                } else {
                    System.out.println("You skipped this artifact, don't worry, there will probably be more along the way.");
                }
            }   
        }
        
    }

    public static void FinishedWave(Player player, PlayerProgression p, Scanner s) {
        p.finishWave();
        player.upgradeBaseStats(p.getStage());
        System.out.print("You finished this wave. >>");
        s.nextLine();
    }
    
    public static void FailWave(Player player, PlayerProgression p, Scanner s) {
        p.failWave();
        player.upgradeBaseStats(p.getStage());
        System.out.print("You failed this wave. >>");
        s.nextLine();
    }

    public static String AwaitInput(Scanner scanner, String[] inputs, String continueMessage) {
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
    
    public static double Truncate(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;    
    }
}