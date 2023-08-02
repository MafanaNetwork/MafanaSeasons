package me.TahaCheji.data;

public class Season implements SeasonEvents {
    private final String Name;
    private final String prefix;
    private final int priority;

    public Season(String name, String prefix, int priority) {
        Name = name;
        this.prefix = prefix;
        this.priority = priority;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return Name;
    }
}
