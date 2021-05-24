public class PlayerProgression {
    public static final int MAX_STAGE = 6;
    public static final int WAVES_PER_STAGE = 19;
    private int stage, wave, luckMultiplier, wavesTried, luckRoundsSkipped;
    private double luck, waveSkipChance;

    public PlayerProgression() {
        stage = 0;
        wave = 0;
        wavesTried = 0;
        luckRoundsSkipped = 0;
        luckMultiplier = 1;
        waveSkipChance = 0.0;
    }

    public void failWave() {
        double random = ((double)((int) (Math.random() * 20) + 1) / 100); // random # 0 -> .2
        luck += random * luckMultiplier;
        System.out.println("+ " + random + " luck");
    }

    public void finishWave() {
        wave++;
    }

    public int startWave() {
        wavesTried++;
        //TODO: rename wave skip chance to stage skip chance
        if (wave >= WAVES_PER_STAGE) {
            wave = 0;
            stage++;
        }

        if (luck >= 1.0) {
            System.out.println("You lucked out, you skip this level.");
            wave++;
            luckRoundsSkipped++;
            luck = 0;
            return 0;
        }

        return (int) (Math.random() * (((stage + 1) * 50) * wave) + 59);
    }

    public void setWaveSkipChance (double n) {
        waveSkipChance = n;
    }

    public double getWaveSkipChance () {
        return waveSkipChance;
    }

    public int getStage() {
        return stage;
    }

    public String getStageName() {
        String name = "";
        switch (stage) {
            case 0: 
                name = "Heat springs";
                break;
            case 1: 
                name = "Dry canyons";
                break;
            case 2: 
                name = "Cool canyons";
                break;
            case 3: 
                name = "Natural lakes";
                break;
            case 4: 
                name = "Exquisite extensions";
                break;
            case 5: 
                name = "Rapid rivers";
                break;
            case 6: 
                name = "Intense icicles";
                break;
        }
        return name;
    }

    public int getWave() {
        return wave;
    }

    public int getLuckMultiplier(){
        return luckMultiplier;
    }

    public void setLuckMultiplier(int n){
        luckMultiplier = n;
    }

    @Override
    public String toString() {
        return "Player wins with " + wavesTried + " waves attempted, and " + luckRoundsSkipped + " rounds beat by luck.";
    }
}
