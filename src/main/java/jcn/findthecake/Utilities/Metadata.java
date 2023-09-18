package jcn.findthecake.Utilities;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Metadata implements MetadataValue {

    private Object value;
    private final Plugin plugin;

    public Metadata(Plugin plugin, Object value) {
        this.plugin = plugin;
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public void invalidate() {
        // Мы не будем что-то делать при инвалидации метаданных
    }

    @Override
    public Plugin getOwningPlugin() {
        return plugin;
    }

    // Другие методы, если необходимо
}
