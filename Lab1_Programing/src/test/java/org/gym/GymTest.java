package org.gym;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GymTest {

    // Membership tests

    @Test
    public void validMembership_shouldReturnTrue() {

        Membership m = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        assertTrue(m.isValid());
    }

    @Test
    public void expiredMembership_shouldReturnFalse() {

        Membership m = new Membership(
                2,
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(1),
                true
        );

        assertFalse(m.isValid());
    }

    @Test
    public void inactiveMembership_shouldReturnFalse() {

        Membership m = new Membership(
                3,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                false
        );

        assertFalse(m.isValid());
    }

    @Test
    public void endDateBeforeStartDate_shouldThrowException() {

        Membership membership = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> membership.setEndDate(
                        LocalDate.now().minusDays(5)
                )
        );
    }

    // Visitor tests

    @Test
    public void visitorWithValidMembership_canVisit() {

        Membership m = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        Visitor v = new Visitor(1, "Nazar", m);

        assertTrue(v.canVisitGym());
    }

    @Test
    public void visitorWithoutMembership_cannotVisit() {

        Visitor v = new Visitor(2, "Ivan", null);

        assertFalse(v.canVisitGym());
    }

    @Test
    public void visitHistory_shouldIncreaseAfterVisit() {

        Membership m = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        Visitor v = new Visitor(1, "Nazar", m);

        v.addVisit(LocalDateTime.now());

        assertEquals(1, v.getVisitHistory().size());
    }

    // Trainer tests

    @Test
    public void trainer_addClient_shouldReturnTrue() {

        Trainer t = new Trainer(
                1,
                "Max",
                "Fitness"
        );

        Membership m = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        Visitor v = new Visitor(
                1,
                "Nazar",
                m
        );

        assertTrue(t.addClient(v));
    }

    @Test
    public void trainer_addSameClientTwice_shouldReturnFalse() {

        Trainer t = new Trainer(
                1,
                "Andriy",
                "Fitness"
        );

        Membership m = new Membership(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        Visitor v = new Visitor(
                1,
                "Nazar",
                m
        );

        t.addClient(v);

        assertFalse(t.addClient(v));
    }

    // Gym tests

    @Test
    public void gym_visit_validVisitor_shouldReturnTrue() {

        Visitor v = new Visitor(
                1,
                "Nazar",
                new Membership(
                        1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        true
                )
        );

        assertTrue(v.visitGym());
    }

    // Visitor validation tests

    @Test
    public void setEmptyName_shouldThrowException() {

        Visitor v = new Visitor(1, "Nazar", null);

        assertThrows(
                IllegalArgumentException.class,
                () -> v.setName("")
        );
    }

    @Test
    public void setTooLongName_shouldThrowException() {

        Visitor v = new Visitor(1, "Nazar", null);

        String longName = "a".repeat(101);

        assertThrows(
                IllegalArgumentException.class,
                () -> v.setName(longName)
        );
    }
}