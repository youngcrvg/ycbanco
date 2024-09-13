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
    public static int getLevelV(final Player p) {
        final int str = GetInt(p, "jrmcStrI");
        final int dex = GetInt(p, "jrmcDexI");
        final int con = GetInt(p, "jrmcCnsI");
        final int wil = GetInt(p, "jrmcWilI");
        final int spi = GetInt(p, "jrmcCncI");
        final int mnd = GetInt(p, "jrmcIntI");
        return (int)((str + dex + con + wil + spi + mnd) / 5.0 - 11.0);
    }
}
