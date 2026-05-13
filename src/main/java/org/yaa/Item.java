package org.yaa;

public abstract class Item {
    protected String itemId;
    protected ItemStatus itemStatus;
    public static int nextId = 1;

    public enum ItemStatus{
        BORROWED, IN_STORE, LOST
    }
}
