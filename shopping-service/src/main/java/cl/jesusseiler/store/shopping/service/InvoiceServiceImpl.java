package cl.jesusseiler.store.shopping.service;

import cl.jesusseiler.store.shopping.client.CustomerClient;
import cl.jesusseiler.store.shopping.client.ProductClient;
import cl.jesusseiler.store.shopping.entity.Invoice;
import cl.jesusseiler.store.shopping.entity.InvoiceItem;
import cl.jesusseiler.store.shopping.model.Customer;
import cl.jesusseiler.store.shopping.model.Product;
import cl.jesusseiler.store.shopping.repository.InvoiceItemsRepository;
import cl.jesusseiler.store.shopping.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;
    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices) {
            // Cargar el cliente
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);

            // Cargar los productos
            List<InvoiceItem> updatedItems = invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(updatedItems);
        }
        return invoices;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB != null) {
            return invoiceDB; // Retorna la factura existente
        }

        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice); // Guarda la nueva factura

        // Actualiza el stock de productos
        invoiceDB.getItems().forEach(invoiceItem -> {
            productClient.updateStock(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        // Cargar el cliente
        Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
        log.info("Customer fetched: {}", customer); // Log del cliente

        invoiceDB.setCustomer(customer);

        // Cargar los productos
        List<InvoiceItem> updatedItems = invoiceDB.getItems().stream().map(invoiceItem -> {
            Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
            log.info("Product fetched: {}", product); // Log del producto
            invoiceItem.setProduct(product);
            return invoiceItem;
        }).collect(Collectors.toList());
        invoiceDB.setItems(updatedItems);

        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());

        // Actualizar productos
        List<InvoiceItem> updatedItems = invoice.getItems().stream().map(invoiceItem -> {
            Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
            invoiceItem.setProduct(product);
            return invoiceItem;
        }).collect(Collectors.toList());
        invoiceDB.setItems(updatedItems);

        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (null != invoice) {
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> listItem = invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                if (product == null) {
                    log.error("Product with id {} not found", invoiceItem.getProductId());
                }
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(listItem);
        }
        return invoice;
    }

}
