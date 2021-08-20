package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 아이템_저장() throws Exception {
        //given
        Item album = new Album();
        album.setName("album");

        Item book = new Book();
        book.setName("book");

        Item movie = new Movie();
        movie.setName("movie");

        //when
        itemService.saveItem(album);
        itemService.saveItem(book);
        itemService.saveItem(movie);

        //then
        assertEquals(album, itemRepository.findOne(album.getId()));
        assertEquals(book, itemRepository.findOne(book.getId()));
        assertEquals(movie, itemRepository.findOne(movie.getId()));
    }
}