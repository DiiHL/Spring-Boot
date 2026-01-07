package br.com.diih.service;

import br.com.diih.data.dto.v1.BookDTO;
import br.com.diih.exceptions.RequiredObjectNullException;
import br.com.diih.exceptions.ResourceNotFoundException;
import br.com.diih.model.Book;
import br.com.diih.repository.BookRepository;
import br.com.diih.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    @Mock
    BookRepository repository;
    @InjectMocks
    BookServices bookServices;

    @BeforeEach
    void setUp() {
        input = new MockBook();
    }

    @Test
    @DisplayName("Should return a Find All Books")
    void findByAll() {
        List<Book> list = input.mockEtityList();
        when(repository.findAll()).thenReturn(list);

        List<BookDTO> books = bookServices.findByAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        BookDTO book1 = books.get(1);
        assertNotNull(book1.getLinks());
        assertEquals(1, book1.getId());
        assertEquals("Author1", book1.getAuthor());
        assertEquals("LaunchDate1", book1.getLaunchDate());
        assertEquals(1, book1.getPrice());
        assertEquals("Title1", book1.getTitle());
    }

    @Test
    @DisplayName("should FindById 1, and return Book")
    void findByIdSuccess() {
        Book entity = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookDTO result = bookServices.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Author1", result.getAuthor());
        assertEquals("LaunchDate1", result.getLaunchDate());
        assertEquals(1, result.getPrice());
        assertEquals("Title1", result.getTitle());
    }

    @Test
    @DisplayName("should not FindById and return Exception")
    void findByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            bookServices.findById(1L);
        });
        String msg = "No Record found for this ID";
        String message = runtimeException.getMessage();
        assertTrue(message.contains(msg));
    }

    @Test
    void create() {
        Book entity = input.mockEntity(1);
        when(repository.save(entity)).thenReturn(entity);

        BookDTO dto = input.mockDTO(1);
        BookDTO bookDTO = bookServices.create(dto);

        assertNotNull(bookDTO);
        assertNotNull(bookDTO.getId());
        assertNotNull(bookDTO.getLinks());
    }

    @Test
    void createNull() {
        RequiredObjectNullException exception = assertThrows(RequiredObjectNullException.class, () -> {
            bookServices.create(null);
        });
        String msg = "It is not allowed to persist a null object!";
        String message = exception.getMessage();
        assertTrue(message.contains(msg));
    }

    @Test
    void update() {
        Book entity = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        BookDTO dto = input.mockDTO(1);

        BookDTO update = bookServices.update(dto);

        assertNotNull(update);
        assertNotNull(update.getId());
        assertNotNull(update.getLinks());

        assertEquals("Author1",update.getAuthor());
        assertEquals("LaunchDate1",update.getLaunchDate());
        assertEquals("Title1",update.getTitle());
    }

    @Test
    @DisplayName("Should delete Book when exists")
    void deleteSuccess() {
        Book entity = input.mockEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        bookServices.delete(1L);
        verify(repository, Mockito.times(1)).findById(1L);
        verify(repository, Mockito.times(1)).delete(any(Book.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when Book does not exist")
    void deleteError() {
        Book entity = input.mockEntity();
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookServices.delete(1L);
        });
        String msg = "No Record found for this ID";
        String message = exception.getMessage();
        assertTrue(message.contains(msg));
    }
}