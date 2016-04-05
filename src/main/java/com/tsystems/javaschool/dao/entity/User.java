package com.tsystems.javaschool.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 19.02.2016
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", length = 15, unique = true, nullable = false)
    private String userName;

    @Column(name = "user_pass", length = 255, nullable = false)
    private String userPass;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole userRole;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        if (!userPass.equals(user.userPass)) return false;
        return userRole.equals(user.userRole);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + userPass.hashCode();
        result = 31 * result + userRole.hashCode();
        return result;
    }
}
