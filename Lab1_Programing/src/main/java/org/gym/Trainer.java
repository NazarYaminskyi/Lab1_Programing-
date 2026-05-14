package org.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trainer {

    private int id;
    private String name;
    private String specialization;
    private List<Visitor> clients;

    public Trainer(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.clients = new ArrayList<>();
    }

    public boolean addClient(Visitor visitor) {
        if (clients.contains(visitor)) {
            return false;
        }
        clients.add(visitor);
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<Visitor> getClients() {
        return clients;
    }

    public void setName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Trainer name cannot be empty");
        }

        if (name.length() > 200) {
            throw new IllegalArgumentException("Trainer name is too long");
        }

        this.name = name.trim();
    }

    public void setSpecialization(String _specialization) {
        
        if (_specialization == null || _specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }

        if (_specialization.length() > 50) {
            throw new IllegalArgumentException("Specialization is too long");
        }

        this.specialization = _specialization;
    }

    public boolean deleteClientById(int _id) {

        for (Visitor visitor : clients) {

            if (visitor.getId() == _id) {
                clients.remove(visitor);
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
