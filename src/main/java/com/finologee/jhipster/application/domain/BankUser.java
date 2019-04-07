package com.finologee.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BankUser.
 */
@Entity
@Table(name = "bank_user")
public class BankUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JsonIgnoreProperties("users")
    private BankAccount bankAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public BankUser bankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
        return this;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getUsername() {
        return username;
    }

    public BankUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public BankUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public BankUser address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public BankUser bankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankUser bankUser = (BankUser) o;
        if (bankUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankUser{" +
            "id=" + getId() +
            ", bankAccountId=" + getBankAccountId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
