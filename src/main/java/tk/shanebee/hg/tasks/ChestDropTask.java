package tk.shanebee.hg.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import tk.shanebee.hg.HG;
import tk.shanebee.hg.data.Config;
import tk.shanebee.hg.game.Bound;
import tk.shanebee.hg.game.Game;
import tk.shanebee.hg.listeners.ChestDrop;
import tk.shanebee.hg.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.WorldBorder;

public class ChestDropTask implements Runnable {

    private final Game game;
    private final int timerID;
    private final List<ChestDrop> chests = new ArrayList<>();
    private final int chestRange = Config.randomChestRange;

    public ChestDropTask(Game game) {
        this.game = game;
        timerID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(HG.getPlugin(), this, Config.randomChestInterval, Config.randomChestInterval);
    }

    public void run() {
        Bound bound = game.getGameArenaData().getBound();
        Integer[] i = getPos();
        
        
           
        int x = i[0];
        int y = i[1];
        int z = i[2];
        World w = bound.getWorld();

        while (w.getBlockAt(x, y, z).getType() == Material.AIR) {
            y--;

            if (y <= 0) {
                i = getPos();

                x = i[0];
                y = i[1];
                z = i[2];
            }
        }

        y = y + 10;

        Location l = new Location(w, x, y, z);

        FallingBlock fb = w.spawnFallingBlock(l, Bukkit.getServer().createBlockData(Material.STRIPPED_SPRUCE_WOOD));

        chests.add(new ChestDrop(fb));

        for (UUID u : game.getGamePlayerData().getPlayers()) {
            Player p = Bukkit.getPlayer(u);
            if (p != null) {
                Util.scm(p, HG.getPlugin().getLang().chest_drop_1);
                Util.scm(p, HG.getPlugin().getLang().chest_drop_2
                        .replace("<x>", String.valueOf(x))
                        .replace("<y>", String.valueOf(y))
                        .replace("<z>", String.valueOf(z)));
                Util.scm(p, HG.getPlugin().getLang().chest_drop_1);
            }
        }
    }

    public void shutdown() {
        Bukkit.getScheduler().cancelTask(timerID);
        for (ChestDrop cd : chests) {
            if (cd != null) cd.remove();
        }
    }
    
    private Integer[] getPos()
    {
        Bound bound = game.getGameArenaData().getBound();
        WorldBorder border = bound.getWorld().getWorldBorder();
        boolean Playerclose = false; 
        Integer[] i = bound.getRandomLocs();
        Util.log("New POS: " + i[0].toString() + " " +i[2].toString());
        Location lBorder = new Location(bound.getWorld(), (double)i[0], (double)i[1], (double)i[2]);
        if(chestRange >0)
        {
        for(UUID p : game.getGamePlayerData().getPlayers())
            {
                Player player = Bukkit.getPlayer(p);
                if(player != null)
                {
                    Util.log("New Player Name: " + player.getName());
                    Location lplayer = player.getLocation();
                    int x = (int)lplayer.getX();
                    int z = (int)lplayer.getZ();
                    Util.log("New POS Player: " + Integer.toString(x)  + " " + Integer.toString(z));
                    if((i[0]-chestRange) <= x && x <= (i[0]+chestRange))
                    {
                        Util.log("New Pos X");
                        if((i[2]-chestRange) <= z && z <= (i[2]+chestRange))
                        {
                            Util.log("New Pos Z");
                            Playerclose = true;
                        }                            
                    }
                }
            }
        }else{
            Playerclose = true;
        }
        int retry = 0;
        while(!border.isInside(lBorder) || (!Playerclose && retry <= 100))
        {
            retry++;
            Playerclose = false;
            i = bound.getRandomLocs();
            Util.log("New POS: " + i[0].toString() + " " +i[2].toString());
            lBorder = new Location(bound.getWorld(), (double)i[0], (double)i[1], (double)i[2]);
            if(chestRange >0)
            {
            for(UUID p : game.getGamePlayerData().getPlayers())
                {
                    Player player = Bukkit.getPlayer(p);
                    
                    if(player != null)
                    {
                        Util.log("New Player Name: " + player.getName());
                        Location lplayer = player.getLocation();
                        int x = (int)lplayer.getX();
                        int z = (int)lplayer.getZ();
                        Util.log("New POS Player: " + Integer.toString(x)  + " " + Integer.toString(z));
                        if((i[0]-chestRange) <= x && x <=(i[0]+chestRange))
                        {
                            Util.log("New Pos X");
                            if((i[2]-chestRange) <= z && z <=(i[2]+chestRange))
                            {   
                                Util.log("New Pos Z");
                                Playerclose = true;
                            }                            
                        }
                    }
                }
            }else
            {
                Playerclose = true;
        }
                
        }
        return i; 
    }
}
