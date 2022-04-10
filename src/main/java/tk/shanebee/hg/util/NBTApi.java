package tk.shanebee.hg.util;


import org.bukkit.inventory.ItemStack;

/**
 * NBT class for adding NBT to items
 * <p>(Mainly for internal use)</p>
 */
public class NBTApi {

    private boolean enabled = false;

    public NBTApi() {
        //MinecraftVersion.replaceLogger(HgLogger.getLogger());
        //if (!isEnabled()) {
        //    warning();
        //}
    }

    /**
     * Set the NBT of an item
     *
     * @param item  The item to set
     * @param value The NBT string to add to the item
     * @return Returns the ItemStack with the new NBT
     */
    public ItemStack getItemWithNBT(ItemStack item, String value) {
        if (!enabled) {
            return item;
        }
        //NBTItem nbtItem = new NBTItem(item);
        //try {
        //    nbtItem.mergeCompound(new NBTContainer(Util.getColString(value)));
        //} catch (Exception ignore) {
        //}
        //return nbtItem.getItem();
        return item;
    }

    /**
     * Get the NBT string from an item
     *
     * @param item Item to grab NBT from
     * @return NBT string from item
     */
    public String getNBT(org.bukkit.inventory.ItemStack item) {
        if (!enabled) {
            return "NBT-API not available";
        }
        //NBTItem nbtItem = new NBTItem(item);
        //return nbtItem.getCompound().toString().replace("§", "&");
        return "NBT-API not available";
    }
    
    final public boolean isEnabled() {
        try {
            //ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
            //NBTItem nbtItem = new NBTItem(itemStack);
            //nbtItem.mergeCompound(new NBTContainer("{Damage:0}"));
            this.enabled = false;
            return false;
        } catch (Exception ignore) {
            this.enabled = false;
            return false;
        }
    }

    final public void warning() {
        Util.warning("NBT-API unavailable for your server version.");
        Util.warning(" - Some items may not be loaded correctly if you are using the 'data' option");
    }

}
