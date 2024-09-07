## Задание

Сделать [REST](https://habr.com/ru/articles/38730/) сервис с использованием [JDBC](https://javarush.com/groups/posts/2172-jdbc-ili-s-chego-vsje-nachinaetsja) и [Servlet](https://javarush.com/en/groups/posts/en.2529.part-5-servlets-java-servlet-api-writing-a-simple-web-application).\
Запустить приложение в Tomcat, можно в [докере](https://www.jetbrains.com/help/idea/deploying-a-web-app-into-an-app-server-container.html).\
Использовать пше Java 21.\
Записать видео демонстрацию и отправить в ЛС в скайпе.\
Функционал любой на выбор, минимум CRUD сервис с несколькими видами entity и отдельными сервлетами, сервисами и репозиториями для разных entity.\
Запрещено использовать Spring, Hibernate, Lombok.\
Можно использовать Hikari CP, Mapstruckt.\
Параметры подключения к БД должны быть вынесены в файл.\
Должны быть реализованы связи [ManayToOne](https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne) ([OneToMany](https://en.wikipedia.org/wiki/One-to-many_(data_model)) и [ManyToMany](https://en.wikipedia.org/wiki/Many-to-many_(data_model),)).\
Связи должны быть отражены в коде (должны быть соответствующие коллекции внутри энтити). Две коллекции для ManyToMany и одна для OneToMany (как в хибернейт, только без аннотаций).\
Связи должны быть двусторонние.\
Не нужно использовать отдельный класс маппинга many to many, только прямая связь.\
Сервлет должен возвращать DTO, не возвращаем Entity, принимать также DTO.\
Должна быть правильная [архитектура](https://habr.com/ru/articles/269589/).\
Должны соблюдаться принципы чистого кода.\
Должен быть сервисный слой.\
Должны соблюдаться принципы ООП, Solid.\
Должны соблюдаться KISS, DRY, YAGNI.\
Должны быть unit тесты, использовать Mockito и Junit.\
Для проверки работы репозитория (DAO) с БД использовать [testcontainers](https://testcontainers.com/).\
Покрытие тестами должно быть больше 80% (строк и методов).\
Должны быть протестированы все слои приложения.\
Слой сервлетов, сервисный слой тестировать с помощью Mockito (независимо от БД и нижележащих слоев).\
Тесты должны запускаться везде, никаких подключений к локальной БД в тестах, только к базе в контейнере testcontainers.\
БД на выбор Postgres, MySQL.\
Ставим плагин [SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint) и исправляем его замечания уровня major и выше.
