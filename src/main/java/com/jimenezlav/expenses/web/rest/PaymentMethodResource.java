package com.jimenezlav.expenses.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jimenezlav.expenses.service.PaymentMethodService;
import com.jimenezlav.expenses.web.rest.util.HeaderUtil;
import com.jimenezlav.expenses.web.rest.util.PaginationUtil;
import com.jimenezlav.expenses.service.dto.PaymentMethodDTO;
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
 * REST controller for managing PaymentMethod.
 */
@RestController
@RequestMapping("/api")
public class PaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodResource.class);

    private static final String ENTITY_NAME = "paymentMethod";

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodResource(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    /**
     * POST  /payment-methods : Create a new paymentMethod.
     *
     * @param paymentMethodDTO the paymentMethodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentMethodDTO, or with status 400 (Bad Request) if the paymentMethod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-methods")
    @Timed
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentMethod : {}", paymentMethodDTO);
        if (paymentMethodDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paymentMethod cannot already have an ID")).body(null);
        }
        PaymentMethodDTO result = paymentMethodService.save(paymentMethodDTO);
        return ResponseEntity.created(new URI("/api/payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-methods : Updates an existing paymentMethod.
     *
     * @param paymentMethodDTO the paymentMethodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentMethodDTO,
     * or with status 400 (Bad Request) if the paymentMethodDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentMethodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-methods")
    @Timed
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentMethod : {}", paymentMethodDTO);
        if (paymentMethodDTO.getId() == null) {
            return createPaymentMethod(paymentMethodDTO);
        }
        PaymentMethodDTO result = paymentMethodService.save(paymentMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-methods : get all the paymentMethods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentMethods in body
     */
    @GetMapping("/payment-methods")
    @Timed
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PaymentMethods");
        Page<PaymentMethodDTO> page = paymentMethodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-methods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-methods/:id : get the "id" paymentMethod.
     *
     * @param id the id of the paymentMethodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentMethodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-methods/{id}")
    @Timed
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get PaymentMethod : {}", id);
        PaymentMethodDTO paymentMethodDTO = paymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentMethodDTO));
    }

    /**
     * DELETE  /payment-methods/:id : delete the "id" paymentMethod.
     *
     * @param id the id of the paymentMethodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-methods/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMethod : {}", id);
        paymentMethodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
