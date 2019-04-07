package com.finologee.jhipster.application.web.rest;

import com.finologee.jhipster.application.FinologeeApp;

import com.finologee.jhipster.application.domain.BankUser;
import com.finologee.jhipster.application.repository.BankUserRepository;
import com.finologee.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.finologee.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BankUserResource REST controller.
 *
 * @see BankUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinologeeApp.class)
public class BankUserResourceIntTest {

    private static final Long DEFAULT_BANK_ACCOUNT_ID = 1L;
    private static final Long UPDATED_BANK_ACCOUNT_ID = 2L;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private BankUserRepository bankUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBankUserMockMvc;

    private BankUser bankUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankUserResource bankUserResource = new BankUserResource(bankUserRepository);
        this.restBankUserMockMvc = MockMvcBuilders.standaloneSetup(bankUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankUser createEntity(EntityManager em) {
        BankUser bankUser = new BankUser()
            .bankAccountId(DEFAULT_BANK_ACCOUNT_ID)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .address(DEFAULT_ADDRESS);
        return bankUser;
    }

    @Before
    public void initTest() {
        bankUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankUser() throws Exception {
        int databaseSizeBeforeCreate = bankUserRepository.findAll().size();

        // Create the BankUser
        restBankUserMockMvc.perform(post("/api/bank-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankUser)))
            .andExpect(status().isCreated());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeCreate + 1);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getBankAccountId()).isEqualTo(DEFAULT_BANK_ACCOUNT_ID);
        assertThat(testBankUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBankUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testBankUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createBankUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankUserRepository.findAll().size();

        // Create the BankUser with an existing ID
        bankUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankUserMockMvc.perform(post("/api/bank-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankUser)))
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBankUsers() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        // Get all the bankUserList
        restBankUserMockMvc.perform(get("/api/bank-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankAccountId").value(hasItem(DEFAULT_BANK_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        // Get the bankUser
        restBankUserMockMvc.perform(get("/api/bank-users/{id}", bankUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankUser.getId().intValue()))
            .andExpect(jsonPath("$.bankAccountId").value(DEFAULT_BANK_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankUser() throws Exception {
        // Get the bankUser
        restBankUserMockMvc.perform(get("/api/bank-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();

        // Update the bankUser
        BankUser updatedBankUser = bankUserRepository.findById(bankUser.getId()).get();
        // Disconnect from session so that the updates on updatedBankUser are not directly saved in db
        em.detach(updatedBankUser);
        updatedBankUser
            .bankAccountId(UPDATED_BANK_ACCOUNT_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .address(UPDATED_ADDRESS);

        restBankUserMockMvc.perform(put("/api/bank-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankUser)))
            .andExpect(status().isOk());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getBankAccountId()).isEqualTo(UPDATED_BANK_ACCOUNT_ID);
        assertThat(testBankUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBankUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testBankUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();

        // Create the BankUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankUserMockMvc.perform(put("/api/bank-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankUser)))
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeDelete = bankUserRepository.findAll().size();

        // Delete the bankUser
        restBankUserMockMvc.perform(delete("/api/bank-users/{id}", bankUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankUser.class);
        BankUser bankUser1 = new BankUser();
        bankUser1.setId(1L);
        BankUser bankUser2 = new BankUser();
        bankUser2.setId(bankUser1.getId());
        assertThat(bankUser1).isEqualTo(bankUser2);
        bankUser2.setId(2L);
        assertThat(bankUser1).isNotEqualTo(bankUser2);
        bankUser1.setId(null);
        assertThat(bankUser1).isNotEqualTo(bankUser2);
    }
}
