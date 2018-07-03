package com.book

import com.book.service.BookService
import com.jooq.h2.spring.BookData
import com.jooq.h2.spring.BookDataProto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController {


    @Autowired
    lateinit var service: BookService


    @GetMapping("")
    fun sayHello(): String {
        return "hello"
    }

    @GetMapping("/book")
    fun findAll(): BookDataProto.BookList = service.findAll()


    @GetMapping("/book/{bookId}")
    fun findById(@PathVariable bookId:Int) : BookDataProto.Book
            = service.findBookById(bookId)




    @GetMapping("/book.json")
    fun findAllJson(): List<BookData> = service.findAllJson()


    @GetMapping("/book/{bookId}.json")
    fun findByIdJson(@PathVariable bookId:Int) : BookData
            = service.findBookByIdJson(bookId)



}