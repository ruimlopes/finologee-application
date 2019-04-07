package com.finologee.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.finologee.jhipster.application.domain.enumeration.BankAccountStatus;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BankAccountStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment giverAccount;

    @OneToMany(mappedBy = "bankAccount")
    private Set<BankUser> users = new HashSet<>();
    @OneToMany(mappedBy = "bankAccount")
    private Set<Balance> balances = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public BankAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BankAccountStatus getStatus() {
        return status;
    }

    public BankAccount status(BankAccountStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BankAccountStatus status) {
        this.status = status;
    }

    public Payment getGiverAccount() {
        return giverAccount;
    }

    public BankAccount giverAccount(Payment payment) {
        this.giverAccount = payment;
        return this;
    }

    public void setGiverAccount(Payment payment) {
        this.giverAccount = payment;
    }

    public Set<BankUser> getUsers() {
        return users;
    }

    public BankAccount users(Set<BankUser> bankUsers) {
        this.users = bankUsers;
        return this;
    }

    public BankAccount addUser(BankUser bankUser) {
        this.users.add(bankUser);
        bankUser.setBankAccount(this);
        return this;
    }

    public BankAccount removeUser(BankUser bankUser) {
        this.users.remove(bankUser);
        bankUser.setBankAccount(null);
        return this;
    }

    public void setUsers(Set<BankUser> bankUsers) {
        this.users = bankUsers;
    }

    public Set<Balance> getBalances() {
        return balances;
    }

    public BankAccount balances(Set<Balance> balances) {
        this.balances = balances;
        return this;
    }

    public BankAccount addBalance(Balance balance) {
        this.balances.add(balance);
        balance.setBankAccount(this);
        return this;
    }

    public BankAccount removeBalance(Balance balance) {
        this.balances.remove(balance);
        balance.setBankAccount(null);
        return this;
    }

    public void setBalances(Set<Balance> balances) {
        this.balances = balances;
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
        BankAccount bankAccount = (BankAccount) o;
        if (bankAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
