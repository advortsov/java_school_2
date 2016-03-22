package com.tsystems.javaschool.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 19.02.2016
 */

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "user_name", length = 15, unique = true, nullable = false)
    private String userName;

    @Column(name = "user_pass", length = 255, nullable = false)
    private String userPass;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user_name) {
        this.userName = user_name;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
