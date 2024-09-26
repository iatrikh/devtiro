package devtiro.database.repositories;

import devtiro.database.TestDataUtils;
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
public class BookRepositoryIntegrationTest {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testBookCreatedAndFound() {

        Author author = TestDataUtils.createTestAuthorA();

        Book book = TestDataUtils.createTestBookA(author);

        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testMultipleBooksCreatedAndFound() {

        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        Book bookA = TestDataUtils.createTestBookA(authorA);
        Book bookB = TestDataUtils.createTestBookB(authorB);
        Book bookC = TestDataUtils.createTestBookC(authorC);
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        List<Book> result = (List<Book>) underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testBookCanBeUpdated() {
        Author authorA = TestDataUtils.createTestAuthorA();
        Book bookA = TestDataUtils.createTestBookA(authorA);
        underTest.save(bookA);

        bookA.setTitle("Anna Karenina");
        underTest.save(bookA);

        Optional<Book> result = underTest.findById(bookA.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
        assertThat(result.get().getTitle()).isEqualTo("Anna Karenina");
    }

    @Test
    public void testBookCanBeDeleted() {
        Author authorA = TestDataUtils.createTestAuthorA();
        Book bookA = TestDataUtils.createTestBookA(authorA);
        underTest.save(bookA);

        underTest.deleteById(bookA.getIsbn());

        Optional<Book> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}