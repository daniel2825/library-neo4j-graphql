package com.neo4j.example.graph.db.services;


import com.neo4j.example.graph.db.config.DataBaseConfig;
import com.neo4j.example.graph.db.repository.IBookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServicesTest {
    private BookServices bookServices;
    @Mock
    private IBookRepository bookRepository;
    @Mock
    private DataBaseConfig dataBaseConfig;
    
    @BeforeAll
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.bookServices = new BookServices(bookRepository, dataBaseConfig);
    }
    
    @Test
    public void findBook() {
        bookServices.getByName("book-1");
    }
    
    
}
