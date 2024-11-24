package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.database.CreatureBuddyDatabase;

import java.util.Objects;

@Entity(tableName = CreatureBuddyDatabase.USER_TABLE)
public class User{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private String password;

    private boolean isAdmin;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}