import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        System.out.println("[Pigmen Teleport] Loaded!");
        new Event(this);
    }
}
