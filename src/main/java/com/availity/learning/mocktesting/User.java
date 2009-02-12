package com.availity.learning.mocktesting;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Entity
@Table(name = "app_user")
public class User implements Serializable {
  private static final long serialVersionUID = 3832626162173359411L;

  private Long id;
  private String username;
  private String password;
  private String passwordHint;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Integer version;
  private boolean carePrescribeAgent;

  public User() {
  }

  /**
   * Create a new instance and set the username.
   *
   * @param username login name for user.
   */
  public User(final String username) {
    this.username = username;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  @Column(nullable = false, length = 50, unique = true)
  public String getUsername() {
    return username;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  @Column(name = "password_hint")
  public String getPasswordHint() {
    return passwordHint;
  }

  @Column(name = "first_name", nullable = false, length = 50)
  public String getFirstName() {
    return firstName;
  }

  @Column(name = "last_name", nullable = false, length = 50)
  public String getLastName() {
    return lastName;
  }

  @Column(nullable = false, unique = true)
  public String getEmail() {
    return email;
  }

  @Column(name = "phone_number")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Transient
  public String getFullName() {
    return firstName + ' ' + lastName;
  }

  @Transient
  public boolean isCarePrescribeAgent() {
    return carePrescribeAgent;
  }

  @Version
  public Integer getVersion() {
    return version;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordHint(String passwordHint) {
    this.passwordHint = passwordHint;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public void setCarePrescribeAgent(boolean carePrescribeAgent) {
    this.carePrescribeAgent = carePrescribeAgent;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }

    final User user = (User) o;

    return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

  }

  public int hashCode() {
    return (username != null ? username.hashCode() : 0);
  }


}

