package nuris.epam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.04.2017.
 */
public class Basket implements Serializable{

    public List<BookInfo> books = new ArrayList<>();

    public void add(BookInfo book) {
        books.add(book);
    }

    public void delete(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.remove(i);
                return;
            }
        }
    }
    public void deleteAll(){
        books.removeAll(books);
    }

    public List<BookInfo> getBooks() {
        return books;
    }
}
