package com.ironhack.edgeservice.model;

import javax.persistence.*;
import java.util.Objects;
@Entity //le cambie el nombre en la bd a sales_rep
public class SalesRep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public SalesRep() {
    }

    public SalesRep(String name) {
        this.name = name;
    }


    //getters and setters needed
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Strings to Print
    @Override
    public String toString() {
        return "=== SALES REP " + id + " ===\n" +
                "- name: " + name + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesRep salesRep = (SalesRep) o;
        return name.equals(salesRep.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
