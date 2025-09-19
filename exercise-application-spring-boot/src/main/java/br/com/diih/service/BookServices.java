package br.com.diih.service;

import br.com.diih.controllers.BookController;
import br.com.diih.data.dto.v1.BookDTO;
import br.com.diih.exceptions.RequiredObjectNullException;
import br.com.diih.exceptions.ResourceNotFoundException;
import br.com.diih.model.Book;
import br.com.diih.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.diih.mapper.ObjectMapper.parseListObjects;
import static br.com.diih.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final static Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository bookRepository;

    public List<BookDTO> findByAll() {
        List<BookDTO> bookDTOS = parseListObjects(bookRepository.findAll(), BookDTO.class);
        bookDTOS.forEach(this::addHateoasLink);
        return bookDTOS;
    }

    public BookDTO findById(Long id) {
        BookDTO dto = parseObject(bookRepository.findById(id).orElseThrow(() -> new RuntimeException("No Record found for this ID")), BookDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public BookDTO create(BookDTO book) {
        if (book == null) throw new RequiredObjectNullException();

        Book entity = parseObject(book, Book.class);
        BookDTO bookDTO = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLink(bookDTO);
        return bookDTO;
    }

    public BookDTO update (BookDTO book){
        if (book == null) throw new RequiredObjectNullException();

        Book entity = bookRepository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setAuthor(book.getAuthor());

        BookDTO dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public void delete(Long id) {
        Book entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        bookRepository.delete(entity);
    }


    public void addHateoasLink(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findByAll()).withRel("Books List").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("Create Book").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("Update Book").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("Update Book").withType("PUT"));
    }
}
