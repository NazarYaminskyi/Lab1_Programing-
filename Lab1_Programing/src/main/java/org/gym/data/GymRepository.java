package org.gym.data;

import org.gym.Visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GymRepository {

    private final String filePath;

    public GymRepository(String filePath) {
        this.filePath = filePath;
    }


    private List<Visitor> sortVisitors(
            List<Visitor> visitors,
            Comparator<Visitor> comparator
    ) {
        List<Visitor> sorted = new ArrayList<>(visitors);
        sorted.sort(comparator);
        return sorted;
    }

    public void exportVisitors(
            List<Visitor> visitors,
            Comparator<Visitor> comparator,
            boolean isSorted
    ) throws IOException {
        List<Visitor> sortedVisitors = visitors;
        if(isSorted) {
            sortedVisitors = sortVisitors(visitors, comparator);
        }

        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Visitor v : sortedVisitors) {
                writer.write(v.getId() + "," + v.getName());
                writer.newLine();
            }
        }
    }

    public List<Visitor> importVisitors() throws IOException {

        List<Visitor> visitors = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            return visitors;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                Visitor v = new Visitor(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        null
                );

                visitors.add(v);
            }
        }

        return visitors;
    }
}