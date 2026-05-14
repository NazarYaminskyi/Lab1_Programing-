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

    public void setActive(boolean _active) {

        if (!_active && endDate.isAfter(LocalDate.now())) {
            System.out.println("Membership was manually deactivated.");
        }

        this.active = _active;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setEndDate(LocalDate date) {

        if (date.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }

        this.endDate = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
