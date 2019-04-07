package com.finologee.jhipster.application.web.rest;
import com.finologee.jhipster.application.domain.Balance;
import com.finologee.jhipster.application.repository.BalanceRepository;
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
 * REST controller for managing Balance.
 */
@RestController
@RequestMapping("/api")
public class BalanceResource {

    private final Logger log = LoggerFactory.getLogger(BalanceResource.class);

    private static final String ENTITY_NAME = "finologeeBalance";

    private final BalanceRepository balanceRepository;

    public BalanceResource(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    /**
     * POST  /balances : Create a new balance.
     *
     * @param balance the balance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balance, or with status 400 (Bad Request) if the balance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balances")
    public ResponseEntity<Balance> createBalance(@RequestBody Balance balance) throws URISyntaxException {
        log.debug("REST request to save Balance : {}", balance);
        if (balance.getId() != null) {
            throw new BadRequestAlertException("A new balance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Balance result = balanceRepository.save(balance);
        return ResponseEntity.created(new URI("/api/balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balances : Updates an existing balance.
     *
     * @param balance the balance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balance,
     * or with status 400 (Bad Request) if the balance is not valid,
     * or with status 500 (Internal Server Error) if the balance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balances")
    public ResponseEntity<Balance> updateBalance(@RequestBody Balance balance) throws URISyntaxException {
        log.debug("REST request to update Balance : {}", balance);
        if (balance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Balance result = balanceRepository.save(balance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balances : get all the balances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of balances in body
     */
    @GetMapping("/balances")
    public List<Balance> getAllBalances() {
        log.debug("REST request to get all Balances");
        return balanceRepository.findAll();
    }

    /**
     * GET  /balances/:id : get the "id" balance.
     *
     * @param id the id of the balance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balance, or with status 404 (Not Found)
     */
    @GetMapping("/balances/{id}")
    public ResponseEntity<Balance> getBalance(@PathVariable Long id) {
        log.debug("REST request to get Balance : {}", id);
        Optional<Balance> balance = balanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(balance);
    }

    /**
     * DELETE  /balances/:id : delete the "id" balance.
     *
     * @param id the id of the balance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balances/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        log.debug("REST request to delete Balance : {}", id);
        balanceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
