package org.gym;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Visitor {

    private int id;
    private String name;
    private Membership membership;
    private List<LocalDateTime> visitHistory;

    public Visitor(int id, String name, Membership membership) {
        this.id = id;
        this.name = name;
        this.membership = membership;
        this.visitHistory = new ArrayList<>();
    }

    public boolean canVisitGym() {
        return membership != null && membership.isValid();
    }

    public void addVisit(LocalDateTime time) {
        visitHistory.add(time);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Membership getMembership() {
        return membership;
    }

    public List<LocalDateTime> getVisitHistory() {
        return visitHistory;
    }

    public void setName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (name.length() > 100) {
            throw new IllegalArgumentException("Name is too long");
        }

        this.name = name.trim();
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public boolean visitGym() {

        if (!this.canVisitGym()) return false;

        this.addVisit(LocalDateTime.now());
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitor)) return false;
        Visitor visitor = (Visitor) o;
        return id == visitor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
