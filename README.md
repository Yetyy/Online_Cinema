# Cinema Aggregator Project

## Описание

Этот проект представляет собой pet-проект, посвященный созданию сайта агрегатора фильмов/онлайн-кинотеатра. Основные функциональные возможности сайта включают регистрацию пользователей,пользовательские отзывы, каталог фильмов, поиск по базе данных фильмов, отображение информации о найденных фильмах и динамическое создание страниц фильмов на основе данных, полученных из API.
## Установка

Run git clone:
```bash
git clone https://github.com/Yetyy/Online_Cinema.git
```

Go to the directory:
```bash
cd Online_Cinema/cinema/
```

And run docker-compose build:
```bash
docker-compose build
```

And then:
```bash
docker-compose up
```

## Использование

Приложение будет доступно по этой [ссылке](http://localhost:8080/)
## Стек технологий

- **Spring Boot**: Создание автономных приложений с минимальной конфигурацией.
- **Spring Data JPA**: Работа с базами данных через Java Persistence API (JPA).
- **Spring Security**: Аутентификация и авторизация пользователей.
- **Thymeleaf**: Шаблонизатор для динамического создания HTML-страниц.
- **MySQL**: Реляционная база данных для хранения информации о фильмах, пользователях и отзывах.
- **Caffeine Cache**: Кэширование данных для повышения производительности.
- **Maven**: Управление зависимостями и сборка проекта.
- **RESTful API**(YouTube Data API,Kinopoisk.dev): Взаимодействие с внешним API для получения информации о фильмах и трейлерах. 
- **Spring Web**: Создание веб-контроллеров и обработка HTTP-запросов.
- **Bootstrap**: Фреймворк для стилизации и создания адаптивного интерфейса.

<details>
  <summary>Изображения сайта</summary>

  ![Регистрация](https://github.com/Yetyy/Online_Cinema/tree/main/images/Register.png)
  
  ![Вход](https://github.com/Yetyy/Online_Cinema/tree/main/images/Login.png)
  
  ![Основная страница сайта](https://github.com/Yetyy/Online_Cinema/tree/main/images/Main_page.png)
  
  ![Подробная информация о фильме](https://github.com/Yetyy/Online_Cinema/tree/main/images/Film_details_1.png)
  
  ![Блок трейлера и отзывов на фильм](https://github.com/Yetyy/Online_Cinema/tree/main/images/Film_details_2.png)
  
  ![Страница поиска](https://github.com/Yetyy/Online_Cinema/tree/main/images/Search.png)
  

</details>
