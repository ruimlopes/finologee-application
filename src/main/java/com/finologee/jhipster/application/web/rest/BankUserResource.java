package com.finologee.jhipster.application.web.rest;
import com.finologee.jhipster.application.domain.BankUser;
import com.finologee.jhipster.application.repository.BankUserRepository;
import com.finologee.jhipster.application.web.rest.errors.BadRequestAlertException;
import com.finologee.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BankUser.
 */
@RestController
@RequestMapping("/api")
public class BankUserResource {

    private final Logger log = LoggerFactory.getLogger(BankUserResource.class);

    private static final String ENTITY_NAME = "finologeeBankUser";

    private final BankUserRepository bankUserRepository;

    public BankUserResource(BankUserRepository bankUserRepository) {
        this.bankUserRepository = bankUserRepository;
    }

    /**
     * POST  /bank-users : Create a new bankUser.
     *
     * @param bankUser the bankUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankUser, or with status 400 (Bad Request) if the bankUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bank-users")
    public ResponseEntity<BankUser> createBankUser(@RequestBody BankUser bankUser) throws URISyntaxException {
        log.debug("REST request to save BankUser : {}", bankUser);
        if (bankUser.getId() != null) {
            throw new BadRequestAlertException("A new bankUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankUser result = bankUserRepository.save(bankUser);
        return ResponseEntity.created(new URI("/api/bank-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bank-users : Updates an existing bankUser.
     *
     * @param bankUser the bankUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bankUser,
     * or with status 400 (Bad Request) if the bankUser is not valid,
     * or with status 500 (Internal Server Error) if the bankUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bank-users")
    public ResponseEntity<BankUser> updateBankUser(@RequestBody BankUser bankUser) throws URISyntaxException {
        log.debug("REST request to update BankUser : {}", bankUser);
        if (bankUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankUser result = bankUserRepository.save(bankUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bankUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bank-users : get all the bankUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bankUsers in body
     */
    @GetMapping("/bank-users")
    public List<BankUser> getAllBankUsers() {
        log.debug("REST request to get all BankUsers");
        return bankUserRepository.findAll();
    }

    /**
     * GET  /bank-users/:id : get the "id" bankUser.
     *
     * @param id the id of the bankUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bankUser, or with status 404 (Not Found)
     */
    @GetMapping("/bank-users/{id}")
    public ResponseEntity<BankUser> getBankUser(@PathVariable Long id) {
        log.debug("REST request to get BankUser : {}", id);
        Optional<BankUser> bankUser = bankUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankUser);
    }

    /**
     * DELETE  /bank-users/:id : delete the "id" bankUser.
     *
     * @param id the id of the bankUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bank-users/{id}")
    public ResponseEntity<Void> deleteBankUser(@PathVariable Long id) {
        log.debug("REST request to delete BankUser : {}", id);
        bankUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
