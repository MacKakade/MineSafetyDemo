package com.goldengekko.generator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyGreenDaoGenerator {

    private static Entity citations,events,forms;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "com.dmi.minesafety.demo.greendao");
        createDatabase(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void createDatabase(Schema schema) {

        createCitations(schema);
        createEvents(schema);
        createForms(schema);
    }


    private static void createCitations(Schema schema) {
        citations = schema.addEntity("Citation");
        citations.setTableName("Citations");
        citations.addIdProperty();

    }

    private static void createEvents(Schema schema) {
        events = schema.addEntity("Event");
        events.setTableName("Events");
        events.addIdProperty();

    }

    private static void createForms(Schema schema) {
        forms = schema.addEntity("Form");
        forms.setTableName("Forms");
        forms.addIdProperty();

    }
}
