package com.stackhawk.vuln.soap.bean;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Course", propOrder = {
        "id",
        "username",
        "password",
        "name",
        "description"
})
@Entity
public class User {
    @GeneratedValue
    private int id;
    private String username;
    private String password;
    private String name;
    private String description;


    public User(int id, String username, String password, String name, String description) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.description = description;
    }

    public User() { }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Course [id=%s, username=%s, password=%s]", id, username, password);
    }

}