package com.tsystems.javaschool.dao.entity;

import javax.persistence.*;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 19.02.2016
 */

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name", length = 15, nullable = false, unique = true)
    private String userRole;

    public UserRole() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole1 = (UserRole) o;

        if (id != userRole1.id) return false;
        return userRole.equals(userRole1.userRole);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + userRole.hashCode();
        return result;
    }
}
