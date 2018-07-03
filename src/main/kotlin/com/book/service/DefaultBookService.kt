package com.book.service


import com.jooq.h2.spring.BookData
import com.jooq.h2.spring.BookDataProto
import org.jooq.DSLContext
import org.jooq.example.db.h2.tables.Book.BOOK
import org.jooq.example.db.h2.tables.records.BookRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class DefaultBookService: BookService {


    @Autowired
    internal var dsl: DSLContext? = null

    //@Transactional
    override fun create(id: Int, authorId: Int, title: String) {
        dsl!!.insertInto<BookRecord>(BOOK).set(BOOK.ID, id).set(BOOK.AUTHOR_ID, authorId).set(BOOK.TITLE, title).execute()
    }

    override fun findAllJson(): List<BookData> {
        return getCollectBooks()
    }

    override fun findAll(): BookDataProto.BookList {
        return convertToBookListProto(getCollectBooks())

    }


    override fun findBookByIdJson(bookId: Int): BookData {
        return getBookData(bookId)
    }


    override fun findBookById(bookId: Int): BookDataProto.Book {
        return convertToBookProto(getBookData(bookId))
    }

    private fun getBookData(bookId: Int): BookData {
        return dsl!!.select(*BOOK.fields()).from(BOOK).where(BOOK.ID.eq(bookId)).fetchInto(BookData::class.java)
                .stream()
                .findFirst()
                .get()
    }


    private fun convertToBookProto(bookData: BookData): BookDataProto.Book {
        print(" bookdata $bookData" )
        return extractBookData(bookData)
    }

    private fun extractBookData(bookData: BookData): BookDataProto.Book {
        return BookDataProto.Book.newBuilder()
                .setAuthorId(bookData.authorId)
                .setPublished(if(bookData.publishedIn!=null) bookData.publishedIn else 9999)
                .setBookTitle(bookData.title)
                .build()
    }

    private fun getCollectBooks(): List<BookData> {
        return dsl!!
                .select(*BOOK.fields())
                .from(BOOK)
                .fetchInto(BookData::class.java)
                .stream()
                .collect(Collectors.toList())
    }

    private fun convertToBookListProto(bookDataList: List<BookData>): BookDataProto.BookList {
        val bookProtoList = BookDataProto.BookList.newBuilder().build()
        bookDataList.forEach { bookData ->
            bookProtoList.toBuilder().addBook(
                    extractBookData(bookData)
            )
        }
        return bookProtoList
    }

}
