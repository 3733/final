package sample;

//import com.sun.speech.freetts.VoiceManager;

// This is used to make sure that the singleton always implements the correct functionality.
// The singleton uses the logic here in the singleton class.
public interface Voice {

    public void say(String tosay);
    // private String name;

    // private com.sun.speech.freetts.Voice voice;

//    private Voice(String name)
//    {
//        this.name = name;
//        this.voice = VoiceManager.getInstance().getVoice(this.name);
//        this.voice.allocate();
//    }

//    private void say(String tosay)
//    {
//        this.voice.speak(tosay);
//    }


}
