package com.example.graphqlserver;

import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @BatchMapping
    public Mono<Map<Book, Author>> author(List<Book> books) {
       var authorIds = books.stream().map(Book::authorId).collect(Collectors.toSet());
       var authorMap =  Author.getByIds(authorIds).stream().collect(Collectors.toMap(Author::id, Function.identity()));

     var x =   books.stream().collect(Collectors.toMap(b -> b, b -> authorMap.get(b.authorId())));

     System.out.println("asdfsfdsf ");
     return Mono.just(x);
    }
    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    @QueryMapping
    public List<Book> books() {
        return Book.findAll();
    }

//    @SchemaMapping
//    public Author author(Book book) {
//        return Author.getById(book.authorId());
//    }
}