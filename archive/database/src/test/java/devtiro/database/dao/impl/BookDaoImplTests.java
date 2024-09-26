package devtiro.database.dao.impl;

import devtiro.database.TestDataUtils;
import devtiro.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testCreateBookGeneratesCorrectSql() {

        Book book = TestDataUtils.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title , author_id) VALUES (?, ?, ?)"),
                eq("123-4-1234-1234-0"), eq("War and Peace"), eq(1L));
    }

    @Test
    public void testFindOneBookGeneratesCorrectSql() {
        underTest.findOne("123-4-1234-1234-0");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("123-4-1234-1234-0"));
    }

    @Test
    public void testFindManyGeneratesCorrectSql() {
        underTest.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testUpdateBookGeneratesCorrectSql() {
        Book book = TestDataUtils.createTestBookA();

        underTest.update(book.getIsbn(), book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq("123-4-1234-1234-0"), eq("War and Peace"), eq(1L), eq(book.getIsbn()));
    }

    @Test
    public void testDeleteGeneratesCorrectSql() {
        underTest.delete("123-4-1234-1234-0");
        verify(jdbcTemplate).update(
                eq("DELETE FROM books WHERE isbn = ?"),
                eq("123-4-1234-1234-0"));
    }
}
