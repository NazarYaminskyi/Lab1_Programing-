package org.gym;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Gym {

    private String name;
    private List<Visitor> visitors;
    private List<Trainer> trainers;

    public Gym(String name) {
        this.name = name;
        this.visitors = new ArrayList<>();
        this.trainers = new ArrayList<>();
    }

    // CREATE
    public void addVisitor(Visitor visitor) {
        visitors.add(visitor);
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    // READ
    public Visitor getVisitorById(int id) {
        for (Visitor v : visitors) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public List<Visitor> getVisitors() {
        return new ArrayList<>(visitors);
    }

    public Trainer getTrainerById(int id) {
        for (Trainer t : trainers) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // DELETE
    public boolean removeVisitor(int id) {
        Visitor v = getVisitorById(id);
        if (v != null) {
            visitors.remove(v);
            return true;
        }
        return false;
    }

    public boolean removeTrainer(int id) {
        Trainer t = getTrainerById(id);
        if (t != null) {
            trainers.remove(t);
            return true;
        }
        return false;
    }

    public void setName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Gym name cannot be empty");
        }

        if (name.length() > 200) {
            throw new IllegalArgumentException("Gym name is too long");
        }

        this.name = name.trim();
    }

    public String getName() {

        if (name == null || name.isBlank()) {
            return "Unnamed Gym";
        }

        return name;
    }

    // BUSINESS LOGIC
    public boolean visitGym(int visitorId) {
        Visitor v = getVisitorById(visitorId);

        if (v == null) return false;
        if (!v.canVisitGym()) return false;

        v.addVisit(LocalDateTime.now());
        return true;
    }

    public boolean assignTrainer(int visitorId, int trainerId) {
        Visitor v = getVisitorById(visitorId);
        Trainer t = getTrainerById(trainerId);

        if (v == null || t == null) return false;

        return t.addClient(v);
    }

    public List<LocalDateTime> getVisitHistory(int visitorId) {
        Visitor v = getVisitorById(visitorId);
        if (v == null) return new ArrayList<>();
        return v.getVisitHistory();
    }
}
