package com.book.service


import com.jooq.h2.spring.BookData
import com.jooq.h2.spring.BookDataProto

interface BookService {


    fun findAll(): BookDataProto.BookList

    fun findAllJson():List<BookData>



    fun findBookById(bookId:Int): BookDataProto.Book


    fun findBookByIdJson(bookId:Int):BookData


    //@Transactional
    fun create(id:Int, authorId:Int,title:String)


}