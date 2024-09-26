package devtiro.database.repositories;

import devtiro.database.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> findByAgeLessThan(int age);

    @Query("select a from Author a where a.age > ?1")
    Iterable<Author> findByAgeGreaterThan(int age);
}
