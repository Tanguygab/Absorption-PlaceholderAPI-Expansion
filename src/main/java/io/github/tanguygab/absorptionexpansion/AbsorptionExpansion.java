package io.github.tanguygab.absorptionexpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class AbsorptionExpansion extends PlaceholderExpansion {

    private Method getAbsorption;
    private Method getHandle;

    public AbsorptionExpansion() {
        String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> entityPlayerClass = Class.forName("net.minecraft.server."+ver+".EntityPlayer");
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit."+ver+".entity.CraftPlayer");
            getAbsorption = entityPlayerClass.getMethod("getAbsorptionHearts");
            getHandle = craftPlayerClass.getMethod("getHandle");
        } catch (Exception ignored) {}
    }

    @Override
    public String getIdentifier() {
        return "absorption";
    }

    @Override
    public String getAuthor() {
        return "Tanguygab";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public List<String> getPlaceholders() {
        return Arrays.asList("%absorption_absorption%","%absorption_health%");
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) return "";
        if (params.equalsIgnoreCase("absorption")) return getAbsorption(p)+"";
        if (params.equalsIgnoreCase("health")) return (p.getHealth()+getAbsorption(p))+"";
        return null;
    }

    public float getAbsorption(Player p) {
        try {return (float) getAbsorption.invoke(getHandle.invoke(p));}
        catch (Exception e) {return 0;}
    }
}
