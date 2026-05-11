package org.gym;

import org.gym.data.GymRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GymRepositoryTest {

    private Visitor createVisitor(int id, String name) {

        Membership m = new Membership(
                id,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                true
        );

        return new Visitor(id, name, m);
    }

    @Test
    public void exportVisitors_shouldCreateFile() throws Exception {

        String path = "test_data/visitors.txt";
        GymRepository repo = new GymRepository(path);

        List<Visitor> visitors = List.of(
                createVisitor(2, "Charlie"),
                createVisitor(1, "Kirk")
        );

        repo.exportVisitors(visitors, null, false);

        File file = new File(path);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void importVisitors_shouldReturnList() throws Exception {

        String path = "test_data/visitors.txt";
        GymRepository repo = new GymRepository(path);

        List<Visitor> visitors = List.of(
                createVisitor(1, "John"),
                createVisitor(2, "Pork")
        );

        repo.exportVisitors(visitors, null, false);

        List<Visitor> imported = repo.importVisitors();

        assertEquals(2, imported.size());
    }

    @Test
    public void export_shouldSortByName() throws Exception {

        String path = "test_data/visitors.txt";
        GymRepository repo = new GymRepository(path);

        List<Visitor> visitors = List.of(
                createVisitor(1, "Zorro"),
                createVisitor(2, "Anna"),
                createVisitor(3, "Mike")
        );

        repo.exportVisitors(visitors, Comparator.comparing(Visitor::getName), true);

        List<Visitor> imported = repo.importVisitors();

        assertEquals("Anna", imported.getFirst().getName());
    }

    @Test
    public void export_emptyList_shouldStillCreateFile() throws Exception {

        String path = "test_data/visitors.txt";
        GymRepository repo = new GymRepository(path);

        repo.exportVisitors(List.of(), null, false);

        File file = new File(path);

        assertTrue(file.exists());
    }

    @Test
    public void import_nonExistingFile_shouldReturnEmptyList() throws Exception {

        String path = "test_data/not_existing.txt";
        GymRepository repo = new GymRepository(path);

        List<Visitor> result = repo.importVisitors();

        assertTrue(result.isEmpty());
    }
}