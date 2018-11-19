package com.newsmanager.web.model;

import java.util.Objects;

public final class Label extends AbstractModel {

    private final String name;

    public Label(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Label label = (Label) o;
        return Objects.equals(name, label.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Label{" +
                "id='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
