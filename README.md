# Коллекция аспектов aspects-library
Здесь собранна коллекция аспектов AspectJ, основанных на аннотациях. Для подключения к проекту Spring необходимо просто подключить класс как Bean, а так же подключить все необходимые для AOP библиотеки: org.aspectj:aspectjrt и org.aspectj:aspectjweaver.

## Аспекты
| Название класса | Описание |
| ---------| -------- |
| [ValidateMongo](https://github.com/JavaGrinko/aspects-library/blob/master/src/main/java/javagrinko/aspects/ValidateMongo.java) | В проект Spring Data MongoDB добавляет функцию валидации полей документов перед добавлением в коллекцию. Используется валидатор javax.validation.Validator и стандарт JSR-349 Bean Validation. |
