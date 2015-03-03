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

    public static List<Mine> MINES = new ArrayList<Mine>();



    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP
            = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("Header", "Citations"));
        addItem(new DummyItem("1", "Citation#: 877141"));
        addItem(new DummyItem("2", "Citation#: 877142"));
        addItem(new DummyItem("3", "Citation#: 877143"));
        addItem(new DummyItem("4", "Citation#: 877144"));
        addItem(new DummyItem("5", "Citation#: 877145"));
        addItem(new DummyItem("6", "Citation#: 877146"));
        addItem(new DummyItem("7", "Citation#: 877147"));
        addItem(new DummyItem("8", "Citation#: 877148"));
        addItem(new DummyItem("9", "Citation#: 877149"));
        addItem(new DummyItem("10", "Citation#: 877150"));
        addItem(new DummyItem("Header", "Events"));
        addItem(new DummyItem("11", "Event#: 834141"));
        addItem(new DummyItem("12", "Event#: 835142"));
        addItem(new DummyItem("13", "Event#: 836143"));
        addItem(new DummyItem("14", "Event#: 837144"));
        addItem(new DummyItem("15", "Event#: 838145"));
        addItem(new DummyItem("16", "Event#: 839146"));
        addItem(new DummyItem("17", "Event#: 836147"));
        addItem(new DummyItem("18", "Event#: 837148"));
        addItem(new DummyItem("19", "Event#: 838149"));
        addItem(new DummyItem("20", "Event#: 839150"));


    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static void addMine(Mine item) {
        MINES.add(item);
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


    public static class Mine {

        public String id;

        public String name;

        public String operatorName;

        public String city;

        public String state;

        public double lat;

        public double longg;


        public Mine(String id, String name,String operatorName, String city, String state,double lat,double longg) {
            this.id = id;
            this.name = name;
            this.operatorName = operatorName;
            this.city = city;
            this.state = state;
            this.lat = lat;
            this.longg = longg;
        }


    }
}
