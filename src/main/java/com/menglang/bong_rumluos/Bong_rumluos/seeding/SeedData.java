package com.menglang.bong_rumluos.Bong_rumluos.seeding;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductRequest;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.category.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SeedData {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);
    private final CategoryRepository categoryRepository;
   private final ProductMapper productMapper;
   private  final CategoryMapper categoryMapper;
   private final ProductRepository productRepository;
   private final CustomerMapper customerMapper;
   private final CustomerRepository customerRepository;

    @PostConstruct
    private void seedData() {
        seedCategory();
        seedProduct();
        seedCustomer();
    }

    private void seedCustomer(){
        log.info("seeding customer . . . . . . . . . .");
        CustomerRequest c1=new CustomerRequest("Menglang","012 223 322","Kandal","Web Developer","https://localhost:9090/file/angkor_wat.jpg",List.of("https://localhost:9090/file/angkor_wat.jpg"));
        CustomerRequest c2=new CustomerRequest("JongLong","012 223 321","Kandal","Operation Officer","https://localhost:9090/file/angkor_wat.jpg",List.of("https://localhost:9090/file/angkor_wat.jpg"));
        CustomerRequest c3=new CustomerRequest("MengSorng","012 223 329","Kandal","Sale Outdoor","https://localhost:9090/file/angkor_wat.jpg",List.of("https://localhost:9090/file/angkor_wat.jpg"));
        List<Customer> customers=Stream.of(c1,c2,c3).map(this.customerMapper::toEntity).toList();
        try{
            customerRepository.saveAll(customers);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    private void seedCategory() {
        log.info("seeding category . . . . . . . . . .");
        CategoryDTO cat1 = new CategoryDTO("Phone", "Smart Phone", "#eee", null);
        CategoryDTO cat2 = new CategoryDTO("Moto", "Moto", "Blue", null);
        CategoryDTO cat3 = new CategoryDTO("Car", "Tesla", "#fff", null);
        CategoryDTO cat4 = new CategoryDTO("TV", "Smart TV", "#e55edd", null);
        CategoryDTO cat5 = new CategoryDTO("Tablet", "I-Pad", "#e55edd", null);
        List<Category> categoryLists = Stream.of(cat1, cat2, cat3, cat4, cat5).map(p->this.categoryMapper.toEntity(p, categoryRepository)).toList();
        try{
            categoryRepository.saveAll(categoryLists);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    private void seedProduct(){
        log.info("seeding products . . . . . . . . . .");
        ProductRequest p1=new ProductRequest("I-Phone 10 Pro Max","color Gold",1l,"#fafaee","","IMEI0000001","IMEI0000002",null,null);
        ProductRequest p2=new ProductRequest("I-Phone 11 Pro Max","color Gold",1l,"#fafaee","","IMEI0000003","IMEI0000004",null,null);
        ProductRequest p3=new ProductRequest("Honda Dream 2025","Black",2l,"#fafaee","","1BL-5522","02400012555",null,null);
        ProductRequest p4=new ProductRequest("SCOPY 2025","White",2l,"#fafaee","","1FL-2255","012554455",null,null);
        ProductRequest p5=new ProductRequest("Cyber Truck 2024","Black",3l,"#fafaee","","1FF-MENGLANG","012225544",null,null);
        ProductRequest p6=new ProductRequest("Samsung TV","Online TV",4l,"#fafaee","","TV-MENGLANG","000011112",null,null);
        List<Product> products= Stream.of(p1,p2,p3,p4,p5,p6).map(this.productMapper::toEntity).toList();
        try{
            productRepository.saveAll(products);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
