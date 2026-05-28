package org.example.kt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kt.exception.BookNotFoundException;
import org.example.kt.model.entity.Book;
import org.example.kt.repository.BookRepository;
import org.example.kt.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        log.debug("Get all books");

        List<Book> books = bookRepository.findAll();

        log.info("Lấy tất cả sách thành công");

        return books;
    }

    @Override
    public Book getBookById(Long id) {
        log.debug("Lấy sách theo id: {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Không tìm thấy sách với id {}", id);
                    return new BookNotFoundException("Không tìm thấy sách");
                });
    }

    @Override
    public Book createBook(Book book) {
        log.debug("Thêm sách: {}", book);

        Book saved = bookRepository.save(book);

        log.info("Thêm sách thành công");

        return saved;
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Book existing = getBookById(id);

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setCategory(book.getCategory());
        existing.setQuantity(book.getQuantity());

        log.info("Cập nhật sách thành công");

        return bookRepository.save(existing);
    }

    @Override
    public Book patchBook(Long id, Map<String, Object> updates) {

        Book existing = getBookById(id);

        if (updates.containsKey("title")) {
            existing.setTitle((String) updates.get("title"));
        }

        if (updates.containsKey("author")) {
            existing.setAuthor((String) updates.get("author"));
        }

        if (updates.containsKey("category")) {
            existing.setCategory((String) updates.get("category"));
        }

        if (updates.containsKey("quantity")) {
            existing.setQuantity((Integer) updates.get("quantity"));
        }

        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(Long id) {

        Book existing = getBookById(id);

        bookRepository.delete(existing);

        log.info("Xóa sách thành công");
    }
}
