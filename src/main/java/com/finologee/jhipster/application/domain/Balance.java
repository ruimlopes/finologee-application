package com.finologee.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.finologee.jhipster.application.domain.enumeration.BalanceType;

/**
 * A Balance.
 */
@Entity
@Table(name = "balance")
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ammount")
    private String ammount;

    @Column(name = "currency")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private BalanceType type;

    @ManyToOne
    @JsonIgnoreProperties("balances")
    private BankAccount bankAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmmount() {
        return ammount;
    }

    public Balance ammount(String ammount) {
        this.ammount = ammount;
        return this;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getCurrency() {
        return currency;
    }

    public Balance currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BalanceType getType() {
        return type;
    }

    public Balance type(BalanceType type) {
        this.type = type;
        return this;
    }

    public void setType(BalanceType type) {
        this.type = type;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Balance bankAccount(BankAccount bankAccount) {
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
        Balance balance = (Balance) o;
        if (balance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Balance{" +
            "id=" + getId() +
            ", ammount='" + getAmmount() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
