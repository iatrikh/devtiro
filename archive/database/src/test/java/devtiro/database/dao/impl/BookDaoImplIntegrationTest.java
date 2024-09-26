package devtiro.database.dao.impl;

import devtiro.database.TestDataUtils;
import devtiro.database.dao.AuthorDao;
import devtiro.database.domain.Author;
import devtiro.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTest {

    private BookDaoImpl underTest;
    private AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCreatedAndFound() {

        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtils.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testMultipleBooksCreatedAndFound() {

        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();
        authorDao.create(authorA);
        authorDao.create(authorB);
        authorDao.create(authorC);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        Book bookB = TestDataUtils.createTestBookB();
        bookB.setAuthorId(authorB.getId());
        Book bookC = TestDataUtils.createTestBookC();
        bookC.setAuthorId(authorC.getId());
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        List<Book> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testBookCanBeUpdated() {
        Author authorA = TestDataUtils.createTestAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        bookA.setTitle("Anna Karenina");
        underTest.update(bookA.getIsbn(), bookA);

        Optional<Book> result = underTest.findOne(bookA.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
        assertThat(result.get().getTitle()).isEqualTo("Anna Karenina");
    }

    @Test
    public void testBookCanBeDeleted() {
        Author authorA = TestDataUtils.createTestAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        underTest.delete(bookA.getIsbn());

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}