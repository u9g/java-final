public class PlayerProgression {
    public static final int MAX_STAGE = 10;
    public static final int WAVES_PER_STAGE = 30;
    private int stage, wave, luckMultiplier, wavesTried, luckRoundsSkipped, artifactSkips;
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
        if (wave == WAVES_PER_STAGE - 1) {
            wave = 0;
            stage++;
        }

        if (Math.random() <= waveSkipChance) {
            System.out.println("You skipped this wave with your artifact.");
            artifactSkips++;
            wave++;
            return 0;
        }

        return (int) (Math.random() * (((stage + 1) * 50) * wave) + 59);
    }

    public void setWaveSkipChance (double n) {
        waveSkipChance = n;
    }

    public boolean canUseLuck () {
        return luck >= 1.0;
    }

    public double getLuck () {
        return luck;
    }

    public double getWaveSkipChance () {
        return waveSkipChance;
    }

    public void useLuck () {
        luck -= 1;
        luckRoundsSkipped++;
        System.out.println("You skipped this wave with luck.");
    }

    public int getStage() {
        return stage;
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
        return "Player wins with " + wavesTried + " waves attempted, " + artifactSkips + " waves skipped by artifacts, " + "and " + luckRoundsSkipped + " rounds beat by luck.";
    }
}
