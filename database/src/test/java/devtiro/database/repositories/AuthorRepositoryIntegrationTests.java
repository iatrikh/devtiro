package devtiro.database.repositories;

import devtiro.database.TestDataUtils;
import devtiro.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCreatedAndFound() {
        Author author = TestDataUtils.createTestAuthorA();

        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testMultipleAuthorsCreatedAndFound() {

        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        List<Author> result = (List<Author>) underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testAuthorCanBeUpdated() {
        Author authorA = TestDataUtils.createTestAuthorA();
        underTest.save(authorA);

        authorA.setName("Updated");
        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
        assertThat(result.get().getName()).isEqualTo("Updated");
    }

    @Test
    public void testAuthorCanBeDeleted() {
        Author authorA = TestDataUtils.createTestAuthorA();
        underTest.save(authorA);

        underTest.deleteById(1L);

        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindAuthorsWithAgeLessThan() {
        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> result = underTest.findByAgeLessThan(50);
        assertThat(result).containsExactly(authorB, authorC);
    }

    @Test
    public void testFindAuthorsWithAgeGreaterThan() {
        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> result = underTest.findByAgeGreaterThan(50);
        assertThat(result).containsExactly(authorA);
    }
}
