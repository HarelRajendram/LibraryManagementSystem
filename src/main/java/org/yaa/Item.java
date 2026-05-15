package org.yaa;

import lombok.Getter;
import lombok.Setter;

public abstract class Item {

    @Getter
    protected String itemId;

    @Getter @Setter
    protected ItemStatus itemStatus;

    public static int nextId = 1;

    public Item() {
        this.itemId = String.format("%04d", nextId++);
        this.itemStatus = ItemStatus.IN_STORE;
    }
    public Item(String itemId) {
        this.itemId = itemId;
        this.itemStatus = ItemStatus.IN_STORE;
    }

    public enum ItemStatus {
        BORROWED, IN_STORE, LOST
    }
}
