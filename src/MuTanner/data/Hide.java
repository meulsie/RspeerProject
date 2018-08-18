package MuTanner.data;

public enum Hide {
    SOFT("Cowhide", 124),
    HARD("Cowhide", 125),
    GREEN("Green dragonhide", 128),
    BLUE("Blue dragonhide",129),
    RED("Red dragonhide",130),
    BLACK("Black dragonhide",131);

    private final String name;
    private final int hideComponent;

    Hide(final String name, final int hideComponent){
        this.name = name;
        this.hideComponent = hideComponent;
    }

    public String getName() { return name; }

    public int getHideComponent() { return hideComponent; }
}
