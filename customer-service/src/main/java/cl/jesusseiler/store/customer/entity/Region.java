package cl.jesusseiler.store.customer.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_regions")
public class Region implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;



}
