package com.example.librarybookapp.viewmodel

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarybookapp.BuildConfig
import com.example.librarybookapp.model.AppDatabase
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.model.BookDAO
import com.mailjet.client.ClientOptions
import com.mailjet.client.MailjetClient
import com.mailjet.client.MailjetRequest
import com.mailjet.client.MailjetResponse
import com.mailjet.client.resource.Emailv31
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.math.roundToInt


class BookListViewModel(database: AppDatabase): ViewModel() {
    private val bookDao: BookDAO = database.bookDao()

    private val _success = mutableStateOf(false)
    val success: State<Boolean> = _success

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _bookInfo = mutableStateOf<Book?>(null)
    val bookInfo: State<Book?> = _bookInfo

    fun isValidDate(dateString: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(dateString, formatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }

    fun calculateProgress(book: Book): Float {
        val currentPage = book.progress
        val minValue = 0
        val maxValue = book.pages
        val clampedValue = currentPage.coerceIn(minValue, maxValue)

        return if (maxValue - minValue == 0)
        {
            1f
        }
        else
        {
            (clampedValue - minValue).toFloat() / (maxValue - minValue).toFloat()
        }
    }

    fun calculateProgressPercentage(book: Book): String {
        val progress = calculateProgress(book)
        val percentage = (progress * 100).roundToInt()
        return "$percentage%"
    }

    fun setBookInfo(book: Book) {
        _bookInfo.value = book
    }

    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
    fun sendEmail(emailAddress: String, name: String) {
        viewModelScope.launch {
            _success.value = true
            val bookList: List<Book> = bookDao.getAllBooks()
            val emailSubject = "Book List"
            val emailBody = StringBuilder()
            emailBody.append("Dear $name,\n\n")
            emailBody.append("Book List:")
            emailBody.append("\n")
            for (book in bookList) {
                emailBody.append("Title: ${book.bookTitle}\n")
                emailBody.append("Author: ${book.bookAuthor}\n")
                emailBody.append("Genre: ${book.bookGenre}\n")
                emailBody.append("Date Published: ${book.datePublished}\n")
                emailBody.append("Pages: ${book.pages}\n")
                emailBody.append("Date Added: ${book.dateAdded}\n")
                emailBody.append("Pages Complete: ${book.progress}\n")
                emailBody.append("------------------------\n")
            }
            withContext(Dispatchers.IO)
            {
                sendWithEmailService(emailAddress, emailSubject, emailBody.toString(), name)
            }
        }
    }

    private fun sendWithEmailService(emailAddress: String, emailSubject: String,
                                     emailBody: String, name: String)
    {
        val apiKey = BuildConfig.MAILJET_API_KEY
        val secretKey = BuildConfig.MAILJET_SECRET_API_KEY
        val mailJetEmail = BuildConfig.MAILJET_EMAIL

        val clientOptions = ClientOptions.builder()
            .apiKey(apiKey)
            .apiSecretKey(secretKey).build()

        val client = MailjetClient(clientOptions)

        val request = MailjetRequest(Emailv31.resource)
            .property(Emailv31.MESSAGES, JSONArray().put(JSONObject().put(
                Emailv31.Message.FROM,JSONObject().put("Email", mailJetEmail)
                    .put("Name","LibraryBookApp")).put(Emailv31.Message.TO,JSONArray()
                .put(JSONObject().put("Email", emailAddress).put("Name",name)))
                .put(Emailv31.Message.SUBJECT, emailSubject).put(Emailv31.Message.TEXTPART, emailBody)))

        try {
            val response: MailjetResponse = client.post(request)
            Log.d("MailJet", "Email sent successfully: ${response.status}")
            _success.value = false
            _email.value = emailAddress
        }catch (e: Exception)
        {
            Log.e("MailJet", "Error sending email: ${e.message}")
            e.printStackTrace()
        }
    }
}