package com.color.card.entity;

import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/1
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class DataListEntity {
    private List<Bloodglucoses> bloodglucoses;

    private Event event;

    private List<Urines> urines;


    public List<Bloodglucoses> getBloodglucoses() {
        return bloodglucoses;
    }

    public void setBloodglucoses(List<Bloodglucoses> bloodglucoses) {
        this.bloodglucoses = bloodglucoses;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Urines> getUrines() {
        return urines;
    }

    public void setUrines(List<Urines> urines) {
        this.urines = urines;
    }

    public class Urines {
        private int eventId;
        private int id;
        private TakeTime takeTime;
        private int uid;
        private String value;
        private String valueLevel;


        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TakeTime getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(TakeTime takeTime) {
            this.takeTime = takeTime;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValueLevel() {
            return valueLevel;
        }

        public void setValueLevel(String valueLevel) {
            this.valueLevel = valueLevel;
        }
    }


    public class Event {
        private int bloodGlucose;
        private CreatedTime createdTime;
        private double diffValue;
        private int id;
        private int uid;
        private int urine;
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getBloodGlucose() {
            return bloodGlucose;
        }

        public void setBloodGlucose(int bloodGlucose) {
            this.bloodGlucose = bloodGlucose;
        }

        public CreatedTime getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(CreatedTime createdTime) {
            this.createdTime = createdTime;
        }

        public double getDiffValue() {
            return diffValue;
        }

        public void setDiffValue(double diffValue) {
            this.diffValue = diffValue;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUrine() {
            return urine;
        }

        public void setUrine(int urine) {
            this.urine = urine;
        }

        public class CreatedTime extends TakeTime {

        }
    }

    public class Bloodglucoses {
        private int eventId;
        private int id;
        private int uid;
        private String value;
        private String valueLevel;
        private TakeTime takeTime;

        public TakeTime getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(TakeTime takeTime) {
            this.takeTime = takeTime;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValueLevel() {
            return valueLevel;
        }

        public void setValueLevel(String valueLevel) {
            this.valueLevel = valueLevel;
        }
    }


}
