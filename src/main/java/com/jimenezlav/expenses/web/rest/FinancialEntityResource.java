package com.jimenezlav.expenses.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jimenezlav.expenses.service.FinancialEntityService;
import com.jimenezlav.expenses.web.rest.util.HeaderUtil;
import com.jimenezlav.expenses.web.rest.util.PaginationUtil;
import com.jimenezlav.expenses.service.dto.FinancialEntityDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FinancialEntity.
 */
@RestController
@RequestMapping("/api")
public class FinancialEntityResource {

    private final Logger log = LoggerFactory.getLogger(FinancialEntityResource.class);

    private static final String ENTITY_NAME = "financialEntity";

    private final FinancialEntityService financialEntityService;

    public FinancialEntityResource(FinancialEntityService financialEntityService) {
        this.financialEntityService = financialEntityService;
    }

    /**
     * POST  /financial-entities : Create a new financialEntity.
     *
     * @param financialEntityDTO the financialEntityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialEntityDTO, or with status 400 (Bad Request) if the financialEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-entities")
    @Timed
    public ResponseEntity<FinancialEntityDTO> createFinancialEntity(@Valid @RequestBody FinancialEntityDTO financialEntityDTO) throws URISyntaxException {
        log.debug("REST request to save FinancialEntity : {}", financialEntityDTO);
        if (financialEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new financialEntity cannot already have an ID")).body(null);
        }
        FinancialEntityDTO result = financialEntityService.save(financialEntityDTO);
        return ResponseEntity.created(new URI("/api/financial-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-entities : Updates an existing financialEntity.
     *
     * @param financialEntityDTO the financialEntityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialEntityDTO,
     * or with status 400 (Bad Request) if the financialEntityDTO is not valid,
     * or with status 500 (Internal Server Error) if the financialEntityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-entities")
    @Timed
    public ResponseEntity<FinancialEntityDTO> updateFinancialEntity(@Valid @RequestBody FinancialEntityDTO financialEntityDTO) throws URISyntaxException {
        log.debug("REST request to update FinancialEntity : {}", financialEntityDTO);
        if (financialEntityDTO.getId() == null) {
            return createFinancialEntity(financialEntityDTO);
        }
        FinancialEntityDTO result = financialEntityService.save(financialEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /financial-entities : get all the financialEntities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of financialEntities in body
     */
    @GetMapping("/financial-entities")
    @Timed
    public ResponseEntity<List<FinancialEntityDTO>> getAllFinancialEntities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FinancialEntities");
        Page<FinancialEntityDTO> page = financialEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/financial-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /financial-entities/:id : get the "id" financialEntity.
     *
     * @param id the id of the financialEntityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financialEntityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/financial-entities/{id}")
    @Timed
    public ResponseEntity<FinancialEntityDTO> getFinancialEntity(@PathVariable Long id) {
        log.debug("REST request to get FinancialEntity : {}", id);
        FinancialEntityDTO financialEntityDTO = financialEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(financialEntityDTO));
    }

    /**
     * DELETE  /financial-entities/:id : delete the "id" financialEntity.
     *
     * @param id the id of the financialEntityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/financial-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinancialEntity(@PathVariable Long id) {
        log.debug("REST request to delete FinancialEntity : {}", id);
        financialEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
