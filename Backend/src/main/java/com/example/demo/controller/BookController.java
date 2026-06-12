package com.example.demo.controller;

import com.example.demo.annotation.RequireRole;
import com.example.demo.common.Result;
import com.example.demo.context.UserContext;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 图书控制器
 */
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookService bookService;

    /**
     * 查询所有图书
     * 管理员：返回所有图书（包括剩余数量为0的）
     * 普通用户：只返回可借图书（剩余数量 > 0）
     */
    @GetMapping
    public ResponseEntity<Result<List<Book>>> getAllBooks() {
        boolean isAdmin = UserContext.isAdmin();
        List<Book> books;

        if (isAdmin) {
            books = bookService.getAllBooks();
        } else {
            books = bookService.getAvailableBooks();
        }

        return ResponseEntity.ok(Result.success(books));
    }

    /**
     * 分页查询图书
     */
    @GetMapping("/page")
    public ResponseEntity<Result<com.baomidou.mybatisplus.core.metadata.IPage<Book>>> getBooksByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<Book> page = bookService.getBooksByPage(pageNum, pageSize);
        return ResponseEntity.ok(Result.success(page));
    }

    /**
     * 查询可借图书（所有用户都可访问）
     */
    @GetMapping("/available")
    public ResponseEntity<Result<List<Book>>> getAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(Result.success(books));
    }

    /**
     * 根据ID查询图书
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Result.error(404, "图书不存在"));
        }

        // 普通用户只能查看可借图书
        if (!UserContext.isAdmin()) {
            Integer remainCount = book.getRemainCount();
            if (remainCount == null || remainCount <= 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Result.error(403, "图书已借完，无法查看详情"));
            }
        }

        return ResponseEntity.ok(Result.success(book));
    }

    /**
     * 根据分类查询图书
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Result<List<Book>>> getBooksByCategory(@PathVariable String category) {
        boolean isAdmin = UserContext.isAdmin();
        List<Book> books;

        if (isAdmin) {
            books = bookService.getBooksByCategory(category);
        } else {
            books = bookService.getAvailableBooksByCategory(category);
        }

        return ResponseEntity.ok(Result.success(books));
    }

    /**
     * 搜索图书
     */
    @GetMapping("/search")
    public ResponseEntity<Result<List<Book>>> searchBooks(@RequestParam String keyword) {
        boolean isAdmin = UserContext.isAdmin();
        List<Book> books;

        if (isAdmin) {
            books = bookService.searchBooks(keyword);
        } else {
            books = bookService.searchAvailableBooks(keyword);
        }

        return ResponseEntity.ok(Result.success(books));
    }

    /**
     * 新增图书（仅管理员）
     */
    @PostMapping
    @RequireRole(1)
    public ResponseEntity<Result<String>> addBook(@RequestBody Book book) {
        boolean success = bookService.addBook(book);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Result.success("添加成功", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("添加失败"));
    }

    /**
     * 更新图书（仅管理员）
     */
    @PutMapping("/{id}")
    @RequireRole(1)
    public ResponseEntity<Result<String>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        boolean success = bookService.updateBook(book);
        if (success) {
            return ResponseEntity.ok(Result.success("更新成功", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("更新失败"));
    }

    /**
     * 删除图书（仅管理员）
     */
    @DeleteMapping("/{id}")
    @RequireRole(1)
    public ResponseEntity<Result<String>> deleteBook(@PathVariable Long id) {
        boolean success = bookService.deleteBook(id);
        if (success) {
            return ResponseEntity.ok(Result.success("删除成功", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("删除失败"));
    }
}