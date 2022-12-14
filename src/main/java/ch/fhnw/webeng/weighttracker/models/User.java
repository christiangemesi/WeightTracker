package ch.fhnw.webeng.weighttracker.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="User")
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    @Transient
    private List<WeightEntry> weightEntries = new ArrayList<>();

    public User() {}

    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public List<WeightEntry> getWeightEntries() {
        return weightEntries;
    }

    public void setWeightEntries(List<WeightEntry> weightEntrySet) {
        this.weightEntries = weightEntrySet;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", email='" + username + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var that = (User) other;
        return Objects.equals(id, that.id)
            && Objects.equals(username, that.username)
            && Objects.equals(password, that.password)
            && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
