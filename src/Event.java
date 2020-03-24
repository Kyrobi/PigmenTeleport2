import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Event implements Listener {
    public Event(Main plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void on(CreatureSpawnEvent event) {
        if ((event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) || (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL)) {
            if (event.getEntityType() == EntityType.PIG_ZOMBIE) {
                LivingEntity entity = event.getEntity();
                Location loc = entity.getLocation();
                Block block = loc.getBlock();

                if (block.getType() == Material.NETHER_PORTAL) {
                    switch (((Orientable) block.getBlockData()).getAxis()) {
                        case X:
                            loc.add(0, 0, 1);
                            break;
                        case Z:
                            loc.add(1, 0, 0);
                            break;
                    }
                    entity.teleport(loc);
                    entity.setSilent(true);
                    entity.setCollidable(false);
                    //entity.getWorld().spawnEntity(loc,EntityType.EXPERIENCE_ORB);
                }
            }
        }
    }
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if ((event.getEntityType() == EntityType.PIG_ZOMBIE) || (event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL)) {
                Entity entity = event.getEntity();
                if (entity.isSilent()) {
                    //event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL
                    Location loc = event.getEntity().getLocation();
                    int exp = 5;
                    event.getDrops().clear();
                    //entity.getWorld().spawnEntity(loc, EntityType.EXPERIENCE_ORB);
                    ((ExperienceOrb) event.getEntity().getWorld().spawn(event.getEntity().getLocation(loc), ExperienceOrb.class)).setExperience(exp);
                }
        }
    }
}
