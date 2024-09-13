package io.github.youngcrvg.ycbanco.powernbt;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NBT {
    public static int GetInt(final Player p, final String NBTkey) {
        final NBTCompound Forgadata = NBTManager.getInstance().readForgeData((Entity)p);
        final NBTCompound PlayerPersisted = (NBTCompound)Forgadata.get((Object)"PlayerPersisted");
        final int out = PlayerPersisted.getInt(NBTkey);
        return out;
    }

    public static void SetInt(final Player p, final String NBTkey, final int NBTvalue) {
        final NBTCompound Forgadata = NBTManager.getInstance().readForgeData((Entity)p);
        final NBTCompound PlayerPersisted = (NBTCompound)Forgadata.get((Object)"PlayerPersisted");
        PlayerPersisted.put(NBTkey, (Object)NBTvalue);
        Forgadata.put("PlayerPersisted", (Object)PlayerPersisted);
        NBTManager.getInstance().writeForgeData((Entity)p, Forgadata);
    }
}
