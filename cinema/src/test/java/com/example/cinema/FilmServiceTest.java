package com.example.cinema;

import com.example.cinema.model.Film;
import com.example.cinema.service.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

@SpringBootTest
public class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testGetPopularFilmsCache() {
        // Первый вызов должен заполнить кэш
        List<Film> firstCall = filmService.getPopularFilms();
        // Второй вызов должен извлекать данные из кэша
        List<Film> secondCall = filmService.getPopularFilms();

        // Проверка, что оба вызова возвращают одинаковые данные
        Assertions.assertEquals(firstCall, secondCall);

        // Проверка наличия данных в кэше
        Cache cache = cacheManager.getCache("popularFilms");
        Assertions.assertNotNull(cache.get(""));

        // Можно также проверить статистику кэша, если это поддерживается
    }
}
