package com.jimenezlav.expenses.web.rest;

import com.jimenezlav.expenses.ExpensesApp;

import com.jimenezlav.expenses.domain.FinancialEntity;
import com.jimenezlav.expenses.repository.FinancialEntityRepository;
import com.jimenezlav.expenses.service.FinancialEntityService;
import com.jimenezlav.expenses.service.dto.FinancialEntityDTO;
import com.jimenezlav.expenses.service.mapper.FinancialEntityMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FinancialEntityResource REST controller.
 *
 * @see FinancialEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpensesApp.class)
public class FinancialEntityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private FinancialEntityRepository financialEntityRepository;

    @Autowired
    private FinancialEntityMapper financialEntityMapper;

    @Autowired
    private FinancialEntityService financialEntityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinancialEntityMockMvc;

    private FinancialEntity financialEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FinancialEntityResource financialEntityResource = new FinancialEntityResource(financialEntityService);
        this.restFinancialEntityMockMvc = MockMvcBuilders.standaloneSetup(financialEntityResource)
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
    public static FinancialEntity createEntity(EntityManager em) {
        FinancialEntity financialEntity = new FinancialEntity()
            .name(DEFAULT_NAME)
            .imageUrl(DEFAULT_IMAGE_URL);
        return financialEntity;
    }

    @Before
    public void initTest() {
        financialEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinancialEntity() throws Exception {
        int databaseSizeBeforeCreate = financialEntityRepository.findAll().size();

        // Create the FinancialEntity
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(financialEntity);
        restFinancialEntityMockMvc.perform(post("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialEntity in the database
        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialEntity testFinancialEntity = financialEntityList.get(financialEntityList.size() - 1);
        assertThat(testFinancialEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFinancialEntity.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createFinancialEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financialEntityRepository.findAll().size();

        // Create the FinancialEntity with an existing ID
        financialEntity.setId(1L);
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(financialEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialEntityMockMvc.perform(post("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialEntityRepository.findAll().size();
        // set the field null
        financialEntity.setName(null);

        // Create the FinancialEntity, which fails.
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(financialEntity);

        restFinancialEntityMockMvc.perform(post("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isBadRequest());

        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialEntityRepository.findAll().size();
        // set the field null
        financialEntity.setImageUrl(null);

        // Create the FinancialEntity, which fails.
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(financialEntity);

        restFinancialEntityMockMvc.perform(post("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isBadRequest());

        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFinancialEntities() throws Exception {
        // Initialize the database
        financialEntityRepository.saveAndFlush(financialEntity);

        // Get all the financialEntityList
        restFinancialEntityMockMvc.perform(get("/api/financial-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getFinancialEntity() throws Exception {
        // Initialize the database
        financialEntityRepository.saveAndFlush(financialEntity);

        // Get the financialEntity
        restFinancialEntityMockMvc.perform(get("/api/financial-entities/{id}", financialEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financialEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinancialEntity() throws Exception {
        // Get the financialEntity
        restFinancialEntityMockMvc.perform(get("/api/financial-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinancialEntity() throws Exception {
        // Initialize the database
        financialEntityRepository.saveAndFlush(financialEntity);
        int databaseSizeBeforeUpdate = financialEntityRepository.findAll().size();

        // Update the financialEntity
        FinancialEntity updatedFinancialEntity = financialEntityRepository.findOne(financialEntity.getId());
        updatedFinancialEntity
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL);
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(updatedFinancialEntity);

        restFinancialEntityMockMvc.perform(put("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isOk());

        // Validate the FinancialEntity in the database
        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeUpdate);
        FinancialEntity testFinancialEntity = financialEntityList.get(financialEntityList.size() - 1);
        assertThat(testFinancialEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFinancialEntity.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingFinancialEntity() throws Exception {
        int databaseSizeBeforeUpdate = financialEntityRepository.findAll().size();

        // Create the FinancialEntity
        FinancialEntityDTO financialEntityDTO = financialEntityMapper.toDto(financialEntity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinancialEntityMockMvc.perform(put("/api/financial-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialEntity in the database
        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinancialEntity() throws Exception {
        // Initialize the database
        financialEntityRepository.saveAndFlush(financialEntity);
        int databaseSizeBeforeDelete = financialEntityRepository.findAll().size();

        // Get the financialEntity
        restFinancialEntityMockMvc.perform(delete("/api/financial-entities/{id}", financialEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinancialEntity> financialEntityList = financialEntityRepository.findAll();
        assertThat(financialEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialEntity.class);
        FinancialEntity financialEntity1 = new FinancialEntity();
        financialEntity1.setId(1L);
        FinancialEntity financialEntity2 = new FinancialEntity();
        financialEntity2.setId(financialEntity1.getId());
        assertThat(financialEntity1).isEqualTo(financialEntity2);
        financialEntity2.setId(2L);
        assertThat(financialEntity1).isNotEqualTo(financialEntity2);
        financialEntity1.setId(null);
        assertThat(financialEntity1).isNotEqualTo(financialEntity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialEntityDTO.class);
        FinancialEntityDTO financialEntityDTO1 = new FinancialEntityDTO();
        financialEntityDTO1.setId(1L);
        FinancialEntityDTO financialEntityDTO2 = new FinancialEntityDTO();
        assertThat(financialEntityDTO1).isNotEqualTo(financialEntityDTO2);
        financialEntityDTO2.setId(financialEntityDTO1.getId());
        assertThat(financialEntityDTO1).isEqualTo(financialEntityDTO2);
        financialEntityDTO2.setId(2L);
        assertThat(financialEntityDTO1).isNotEqualTo(financialEntityDTO2);
        financialEntityDTO1.setId(null);
        assertThat(financialEntityDTO1).isNotEqualTo(financialEntityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financialEntityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financialEntityMapper.fromId(null)).isNull();
    }
}
