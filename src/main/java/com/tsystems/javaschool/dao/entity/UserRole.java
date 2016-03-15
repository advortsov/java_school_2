package com.tsystems.javaschool.dao.entity;

import javax.persistence.*;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 19.02.2016
 */

@Entity
@IdClass(UserRoleId.class)
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "user_name", length = 15, nullable = false) // changed on not unique 3 march
    private String userName;

    @Id
    @Column(name = "role_name", length = 15, nullable = false)
    private String userRole;

    public UserRole() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

        if (!userName.equals(userRole1.userName)) return false;
        return userRole.equals(userRole1.userRole);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + userRole.hashCode();
        return result;
    }
}
