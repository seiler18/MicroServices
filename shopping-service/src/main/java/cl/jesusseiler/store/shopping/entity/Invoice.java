package cl.jesusseiler.store.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.jesusseiler.store.shopping.model.Customer;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_invoices")
public class Invoice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;



    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    private String state;

    @Transient
    private Customer customer;

    public Invoice(){
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

}
