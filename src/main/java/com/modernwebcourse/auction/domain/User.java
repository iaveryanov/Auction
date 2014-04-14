package com.modernwebcourse.auction.domain;

public class User {

    public int id;
    public String name;
    public String email;
    public boolean getOverbidNotifications;

    public User() {
    }

    public User(int id, String name, String email, boolean getOverbidNotifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.getOverbidNotifications = getOverbidNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", getOverbidNotifications=" + getOverbidNotifications +
                '}';
    }
}
