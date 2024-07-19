package com.assignment.name_project.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Popup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "popup_id")
    private List<MenuItem> menuitem;

    // Getters and setters
    public List<MenuItem> getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(List<MenuItem> menuitem) {
        this.menuitem = menuitem;
    }
}
