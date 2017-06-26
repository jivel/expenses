package com.jimenezlav.expenses.web.rest;

import com.jimenezlav.expenses.ExpensesApp;

import com.jimenezlav.expenses.domain.Spending;
import com.jimenezlav.expenses.repository.SpendingRepository;
import com.jimenezlav.expenses.service.SpendingService;
import com.jimenezlav.expenses.service.dto.SpendingDTO;
import com.jimenezlav.expenses.service.mapper.SpendingMapper;
import com.jimenezlav.expenses.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.jimenezlav.expenses.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpendingResource REST controller.
 *
 * @see SpendingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpensesApp.class)
public class SpendingResourceIntTest {

    private static final String DEFAULT_CONCEPT = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPT = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final ZonedDateTime DEFAULT_EXPENSE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPENSE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private SpendingMapper spendingMapper;

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpendingMockMvc;

    private Spending spending;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpendingResource spendingResource = new SpendingResource(spendingService);
        this.restSpendingMockMvc = MockMvcBuilders.standaloneSetup(spendingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spending createEntity(EntityManager em) {
        Spending spending = new Spending()
            .concept(DEFAULT_CONCEPT)
            .amount(DEFAULT_AMOUNT)
            .expenseDate(DEFAULT_EXPENSE_DATE);
        return spending;
    }

    @Before
    public void initTest() {
        spending = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpending() throws Exception {
        int databaseSizeBeforeCreate = spendingRepository.findAll().size();

        // Create the Spending
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);
        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isCreated());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeCreate + 1);
        Spending testSpending = spendingList.get(spendingList.size() - 1);
        assertThat(testSpending.getConcept()).isEqualTo(DEFAULT_CONCEPT);
        assertThat(testSpending.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSpending.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
    }

    @Test
    @Transactional
    public void createSpendingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spendingRepository.findAll().size();

        // Create the Spending with an existing ID
        spending.setId(1L);
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkConceptIsRequired() throws Exception {
        int databaseSizeBeforeTest = spendingRepository.findAll().size();
        // set the field null
        spending.setConcept(null);

        // Create the Spending, which fails.
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = spendingRepository.findAll().size();
        // set the field null
        spending.setAmount(null);

        // Create the Spending, which fails.
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpenseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = spendingRepository.findAll().size();
        // set the field null
        spending.setExpenseDate(null);

        // Create the Spending, which fails.
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpendings() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        // Get all the spendingList
        restSpendingMockMvc.perform(get("/api/spendings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spending.getId().intValue())))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(sameInstant(DEFAULT_EXPENSE_DATE))));
    }

    @Test
    @Transactional
    public void getSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        // Get the spending
        restSpendingMockMvc.perform(get("/api/spendings/{id}", spending.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spending.getId().intValue()))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.expenseDate").value(sameInstant(DEFAULT_EXPENSE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSpending() throws Exception {
        // Get the spending
        restSpendingMockMvc.perform(get("/api/spendings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);
        int databaseSizeBeforeUpdate = spendingRepository.findAll().size();

        // Update the spending
        Spending updatedSpending = spendingRepository.findOne(spending.getId());
        updatedSpending
            .concept(UPDATED_CONCEPT)
            .amount(UPDATED_AMOUNT)
            .expenseDate(UPDATED_EXPENSE_DATE);
        SpendingDTO spendingDTO = spendingMapper.toDto(updatedSpending);

        restSpendingMockMvc.perform(put("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isOk());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeUpdate);
        Spending testSpending = spendingList.get(spendingList.size() - 1);
        assertThat(testSpending.getConcept()).isEqualTo(UPDATED_CONCEPT);
        assertThat(testSpending.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSpending.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpending() throws Exception {
        int databaseSizeBeforeUpdate = spendingRepository.findAll().size();

        // Create the Spending
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpendingMockMvc.perform(put("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isCreated());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);
        int databaseSizeBeforeDelete = spendingRepository.findAll().size();

        // Get the spending
        restSpendingMockMvc.perform(delete("/api/spendings/{id}", spending.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spending.class);
        Spending spending1 = new Spending();
        spending1.setId(1L);
        Spending spending2 = new Spending();
        spending2.setId(spending1.getId());
        assertThat(spending1).isEqualTo(spending2);
        spending2.setId(2L);
        assertThat(spending1).isNotEqualTo(spending2);
        spending1.setId(null);
        assertThat(spending1).isNotEqualTo(spending2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpendingDTO.class);
        SpendingDTO spendingDTO1 = new SpendingDTO();
        spendingDTO1.setId(1L);
        SpendingDTO spendingDTO2 = new SpendingDTO();
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
        spendingDTO2.setId(spendingDTO1.getId());
        assertThat(spendingDTO1).isEqualTo(spendingDTO2);
        spendingDTO2.setId(2L);
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
        spendingDTO1.setId(null);
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(spendingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(spendingMapper.fromId(null)).isNull();
    }
}
