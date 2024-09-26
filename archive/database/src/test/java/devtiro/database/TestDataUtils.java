package devtiro.database;

import devtiro.database.domain.Author;
import devtiro.database.domain.Book;

public final class TestDataUtils {


    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Jesse A Casey")
                .age(24)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("123-4-1234-1234-0")
                .title("War and Peace")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("123-5-1234-1234-0")
                .title("Crime and Punishment")
                .authorId(2L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("123-6-1234-1234-0")
                .title("The Nose")
                .authorId(3L)
                .build();
    }
}
