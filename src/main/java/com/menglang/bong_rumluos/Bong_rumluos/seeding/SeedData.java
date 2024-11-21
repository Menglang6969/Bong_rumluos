package com.menglang.bong_rumluos.Bong_rumluos.seeding;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.category.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeedData {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);
    private final CategoryService categoryService;

    @PostConstruct
    private void seedData(){
        seedCategory();
    }

    private void seedCategory(){
        log.info("seeding category . . . . . . . . . .");
        CategoryDTO cat1=new CategoryDTO("Smart Phone","Smart Phone","#eee",null);
        CategoryDTO cat2=new CategoryDTO("Moto","Moto","Blue",null);
        CategoryDTO cat3=new CategoryDTO("Samsung","Smart Phone Model Samsung","#fff",1L);
        CategoryDTO cat4=new CategoryDTO("I-Phone","Smart Phone Model I-Phone","#e55edd",1L);
        CategoryDTO cat5=new CategoryDTO("Honda","Honda Moto","#e55edd",2L);
        List<CategoryDTO> categoryLists=List.of(cat1,cat2,cat3,cat4,cat5);
        for (CategoryDTO c :categoryLists){
            try {
                categoryService.create(c);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
