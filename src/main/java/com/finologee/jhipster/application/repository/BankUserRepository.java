package com.finologee.jhipster.application.repository;

import com.finologee.jhipster.application.domain.BankUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {

}
