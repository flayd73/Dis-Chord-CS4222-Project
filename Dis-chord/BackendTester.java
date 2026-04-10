public class BackendTester {
    public static void main(String[] args) {
        // 1. Create your two tools
        NoteRandomizer randomizer = new NoteRandomizer();
        AudioPlayer player = new AudioPlayer();
        
        // 2. Get a random note
        String currentNote = randomizer.getRandomNote();
        System.out.println("The random note picked is: " + currentNote);
        
        // 3. Play that exact note
        player.playNote(currentNote);
        
        // (Keep the program alive for a second so the sound can finish)
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}