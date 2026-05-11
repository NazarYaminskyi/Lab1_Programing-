package org.gym;

import java.time.LocalDate;
import java.util.Objects;

public class Membership {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public Membership(int id, LocalDate startDate, LocalDate endDate, boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public boolean isValid() {
        return active && endDate.isAfter(LocalDate.now());
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {

        if (!active && endDate.isAfter(LocalDate.now())) {
            System.out.println("Membership was manually deactivated.");
        }

        this.active = active;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }

        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership)) return false;
        Membership that = (Membership) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
