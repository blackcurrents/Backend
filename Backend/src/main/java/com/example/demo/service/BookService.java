package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;

    /**
     * 查询所有图书（带剩余数量）
     */
    public List<Book> getAllBooks() {
        return bookMapper.selectAllWithRemain();
    }

    /**
     * 分页查询图书（带剩余数量）
     */
    public IPage<Book> getBooksByPage(int pageNum, int pageSize) {
        Page<Book> page = new Page<>(pageNum, pageSize);
        return bookMapper.selectPageWithRemain(page);
    }

    /**
     * 查询可借图书
     */
    public List<Book> getAvailableBooks() {
        return bookMapper.selectAvailableBooks();
    }

    /**
     * 根据分类查询可借图书（新增）
     */
    public List<Book> getAvailableBooksByCategory(String category) {
        List<Book> books = bookMapper.selectByCategory(category);
        return books.stream()
                .filter(book -> book.getRemainCount() != null && book.getRemainCount() > 0)
                .collect(Collectors.toList());
    }

    /**
     * 根据ID查询图书（带剩余数量）
     */
    public Book getBookById(Long id) {
        return bookMapper.selectByIdWithRemain(id);
    }

    /**
     * 根据分类查询图书
     */
    public List<Book> getBooksByCategory(String category) {
        return bookMapper.selectByCategory(category);
    }

    /**
     * 搜索图书
     */
    public List<Book> searchBooks(String keyword) {
        return bookMapper.searchBooks(keyword);
    }

    /**
     * 搜索可借图书（新增）
     */
    public List<Book> searchAvailableBooks(String keyword) {
        List<Book> books = bookMapper.searchBooks(keyword);
        return books.stream()
                .filter(book -> book.getRemainCount() != null && book.getRemainCount() > 0)
                .collect(Collectors.toList());
    }

    /**
     * 新增图书
     */
    public boolean addBook(Book book) {
        return bookMapper.insert(book) > 0;
    }

    /**
     * 更新图书
     */
    public boolean updateBook(Book book) {
        return bookMapper.updateById(book) > 0;
    }

    /**
     * 删除图书（逻辑删除）
     */
    public boolean deleteBook(Long id) {
        return bookMapper.deleteById(id) > 0;
    }

    /**
     * 增加图书库存
     */
    public boolean increaseTotalCount(Long id) {
        return bookMapper.increaseTotalCount(id) > 0;
    }

    /**
     * 减少图书库存
     */
    public boolean decreaseTotalCount(Long id) {
        return bookMapper.decreaseTotalCount(id) > 0;
    }
}