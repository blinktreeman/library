# Library application
## Задание

1. Подключить OpenAPI 3 и swagger к проекту с библиоткой
2. Описать все контроллеры, эндпоинты и возвращаемые тела с помощью аннотаций OpenAPI 3
3. В качестве результата, необходимо прислать скриншот(ы) страницы swagger (с ручками)

## Решение

Подключаем зависимость OpenAPI
`pom.xml`
```xml
<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

