package sample;

import com.sun.speech.freetts.VoiceManager;

public class SingletonTTS implements Voice{

    private volatile static SingletonTTS INSTANCE;
    private static String name = "kevin16";
    private com.sun.speech.freetts.Voice voice = VoiceManager.getInstance().getVoice(this.name);

    private SingletonTTS() {}

    /*public void printSquat() {
        System.out.println("Hello World");
    }*/

    public static SingletonTTS getInstance() {
        // Double Checked Locking: google this on wikipedia
        if (INSTANCE == null) { // Single check
            synchronized (SingletonTTS.class)
            {
                if(INSTANCE == null) // double check
                {
                    INSTANCE = new SingletonTTS();
                }
            }
//            INSTANCE = new SingletonTTS();
        }
        return INSTANCE;
    }

    // This is the method that actually says swag.
    public void say(String tosay) {
        this.voice.allocate();
        this.voice.speak(tosay);
        System.out.println(tosay); // For troubleshooting

    }
// Voice voice = new Voice("kevin16");
    // String sayme = "I suck because I just don't work.";
    // voice.say(sayme);

}
