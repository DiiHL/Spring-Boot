package br.com.diih.unitetests.mapper.mocks;

import br.com.diih.data.dto.v1.BookDTO;
import br.com.diih.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEtityList() {
        List<Book> mockList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            mockList.add(mockEntity(i));
        }
        return mockList;
    }

    public List<BookDTO> mockDTOList(){
        List<BookDTO> mockList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            mockList.add(mockDTO(1));
        }
        return mockList;
    }

    public Book mockEntity(Integer num) {
        Book book = new Book();
        book.setId(num.longValue());
        book.setAuthor("Author" + num);
        book.setLaunchDate("LaunchDate" + num);
        book.setPrice(num.doubleValue());
        book.setTitle("Title" + num);
        return book;
    }

    public BookDTO mockDTO(Integer num) {
        BookDTO book = new BookDTO();
        book.setId(num.longValue());
        book.setAuthor("Author" + num);
        book.setLaunchDate("LaunchDate" + num);
        book.setPrice(num.doubleValue());
        book.setTitle("Title" + num);
        return book;
    }
}
