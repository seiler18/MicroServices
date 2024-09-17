package cl.jesusseiler.store.shopping.controller;

import cl.jesusseiler.store.shopping.entity.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import cl.jesusseiler.store.shopping.service.InvoiceService;
import jakarta.validation.Valid;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceRest {

    @Autowired
    InvoiceService invoiceService;

    // -------------------Retrieve All Invoices--------------------------------------------
    @GetMapping
    public ResponseEntity<List<Invoice>> listAllInvoices() {
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        if (invoices.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(invoices);
    }

    // -------------------Retrieve Single Invoice------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") long id) {
        log.info("Fetching Invoice with id {}", id);
        Invoice invoice  = invoiceService.getInvoice(id);
        if (null == invoice) {
            log.error("Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(invoice);
    }

    // -------------------Create a Invoice-------------------------------------------
    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) throws MethodArgumentNotValidException {
        log.info("Creating Invoice : {}", invoice);
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result); // Lanza la excepción para ser manejada por el
                                                                     // GlobalExceptionHandler
        }

        Invoice invoiceDB = invoiceService.createInvoice (invoice);
        log.info("Invoice created: {}", invoiceDB);

        return  ResponseEntity.status( HttpStatus.CREATED).body(invoiceDB);
    }

    // ------------------- Update a Invoice ------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable("id") long id, @RequestBody Invoice invoice) {
        log.info("Updating Invoice with id {}", id);

        invoice.setId(id);
        Invoice currentInvoice=invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            log.error("Unable to update. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(currentInvoice);
    }

    // ------------------- Delete a Invoice-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);

        Invoice invoice = invoiceService.getInvoice(id);
        if (invoice == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

}
