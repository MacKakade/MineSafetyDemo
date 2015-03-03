package com.dmi.minesafety.demo.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards. <p> TODO: Replace all uses of this class before
 * publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP
            = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("Header", "Citations"));
        addItem(new DummyItem("1", "877141"));
        addItem(new DummyItem("2", "877142"));
        addItem(new DummyItem("3", "877143"));
        addItem(new DummyItem("4", "877144"));
        addItem(new DummyItem("5", "877145"));
        addItem(new DummyItem("6", "877146"));
        addItem(new DummyItem("Header", "Events"));
        addItem(new DummyItem("1", "834141"));
        addItem(new DummyItem("2", "835142"));
        addItem(new DummyItem("3", "836143"));
        addItem(new DummyItem("4", "837144"));
        addItem(new DummyItem("5", "838145"));
        addItem(new DummyItem("6", "839146"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {

        public String id;

        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
