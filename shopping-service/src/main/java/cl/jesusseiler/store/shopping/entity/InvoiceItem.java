package cl.jesusseiler.store.shopping.entity;


import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.jesusseiler.store.shopping.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;



@Entity
@Data
@Table(name = "tbl_invoice_items")
@JsonIgnoreProperties({"invoice"})
public class InvoiceItem  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "El stock debe ser mayor que cero")
    private Double quantity;
    @Positive(message = "El precio debe ser mayor que cero")
    private Double  price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private Double subTotal;

    @Transient
    private Product product;


    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }
    public InvoiceItem(){
        this.quantity=(double) 0;
        this.price=(double) 0;

    }
}
