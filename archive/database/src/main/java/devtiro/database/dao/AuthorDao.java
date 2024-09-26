package devtiro.database.dao;

import devtiro.database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long authorId);

    List<Author> findAll();

    void update(long authorId, Author author);

    void delete(long authorId);
}
