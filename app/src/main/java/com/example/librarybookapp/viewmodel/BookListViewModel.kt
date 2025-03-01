package com.example.librarybookapp.viewmodel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.math.roundToInt

//Single viewmodel drilled down into the app for using across the entire app
//Defined variables
class BookListViewModel(database: AppDatabase): ViewModel() {
    private val bookDao: BookDAO = database.bookDao()

    private val _success = mutableStateOf(false)
    val success: State<Boolean> = _success

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val book: MutableList<Book> = mutableListOf()

    private val _bookInfo = mutableStateOf<Book?>(null)
    val bookInfo: State<Book?> = _bookInfo

    private val _bookToDisplay = MutableStateFlow<Book?>(null)
    val bookToDisplay: StateFlow<Book?> = _bookToDisplay.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _isEmailEmpty = mutableStateOf(true)
    val isEmailEmpty: State<Boolean> = _isEmailEmpty

    private val _isEmailValid = mutableStateOf(false)
    val isEmailValid: State<Boolean> = _isEmailValid

    private val _isNameValid = mutableStateOf(false)

    private val _isNameEmpty = mutableStateOf(true)
    val isNameEmpty: State<Boolean> = _isNameEmpty

    private val _isSubmitted = mutableStateOf(false)
    val isSubmitted: State<Boolean> = _isSubmitted

    var isChecked: Boolean = false

    //Date validation logic
    fun isValidDate(dateString: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(dateString, formatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
    //Email validation logic
    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex) && email.trim().isNotEmpty()
    }
    //Name validation logic
    private fun isValidName(name: String): Boolean {
        val nameRegex = "^[A-Za-z\\s'-]{2,}$".toRegex()
        return name.matches(nameRegex) && name.trim().isNotEmpty()
    }
    //String formatting logic used for name and book string inputs
    fun formatString(input: String): String {
        return input.trim().lowercase()
            .split("(?<=[\\s'-.])|(?=[\\s'-.])".toRegex()) // Splits but keeps punctuation
            .joinToString("") { word ->
                if (word.isNotEmpty() && word[0].isLetter()) {
                    word.replaceFirstChar { it.uppercase() } // Capitalize first letter of each word
                } else word // Keep punctuation and spaces unchanged
            }
    }
    //Creating the custom list with the checkbox logic
    fun setCustomListById(id: Int){
        val bookToAdd = bookDao.getBookById(id)
        if (bookToAdd != null)
        {
            if (isChecked)
            {
                if (!book.contains(bookToAdd))
                {
                    book.add(bookToAdd)
                }
            }
            else
            {
                    book.remove(bookToAdd)
            }
        }
    }
    //Getter for the custom list access
    private fun getCustomList(): List<Book> {
        return book
    }
    //Calculating the read progress of the book
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
    //Calculating the percentage of the book read progress
    fun calculateProgressPercentage(book: Book): String {
        val progress = calculateProgress(book)
        val percentage = (progress * 100).roundToInt()
        return "$percentage%"
    }
    //Setter for the book info on the bookInfo display page
    fun setBookInfo(book: Book) {
        _bookInfo.value = book
    }
    //Database access logic for retrieving all the books
    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks()
    }
    //Database access logic for adding a book
    private fun addBook(book: Book) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }
    //Database access logic for getting a book by title and author which
    // restricts the user from adding the same book twice
    fun getBookByTitle(book: Book, onBookFound: (Book?) -> Unit) {
        viewModelScope.launch {
            bookDao.getBookByTitle(book.bookTitle, book.bookAuthor)
                .flowOn(Dispatchers.IO).collect(){books: List<Book?> ->
                    if (books.isNotEmpty())
                    {
                        _showDialog.value = true
                        _bookToDisplay.value = books.first()
                        onBookFound(books.first())
                    }
                    else
                    {
                        onBookFound(null)
                        addBook(book)
                    }
                }
        }
    }
    //Clearing the book info display list
    fun clearBookList()
    {
        _bookToDisplay.value = null

    }
    //Clearing the existing book dialog
    fun clearShowDialog()
    {
        _showDialog.value = false
    }
    //Clearing credentials to reset validation
    fun resetCredentials()
    {
        _isEmailEmpty.value = true
        _isNameEmpty.value = true
        _isEmailValid.value = false
        _isNameValid.value = false
        _isSubmitted.value = false
    }
    //Database access logic for deleting a book
    suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }
    //Database access logic for updating a book (Called inside the getBookByTitle function)
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
    //Email sending logic with email formatting
    fun sendEmail(emailAddress: String, name: String) {
        _isSubmitted.value = true
        _isEmailEmpty.value = emailAddress.trim().isEmpty()
        _isNameEmpty.value = name.trim().isEmpty()
        _isEmailValid.value = !isEmailValid(emailAddress)
        _isNameValid.value = !isValidName(name)
        if (_isEmailEmpty.value || _isNameEmpty.value || _isEmailValid.value || _isNameValid.value)
        {
            return
        }
        viewModelScope.launch {
            _success.value = true
            _isLoading.value = true
            val bookList: List<Book> = getCustomList().ifEmpty {
                bookDao.getAllBooks()
            }
            val emailBody = StringBuilder()
            emailBody.append("Dear ${formatString(name)},\n\n")
            emailBody.append("Here is your requested Book List:\n")
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
                sendWithEmailService(emailAddress, emailBody.toString(), name)
            }
            book.clear()
            isChecked = false
        }
    }
    //Email sending logic with MailJet API
    private fun sendWithEmailService(emailAddress: String,
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
                .put(Emailv31.Message.SUBJECT, "Book List").put(Emailv31.Message.TEXTPART, emailBody)))

        try {
            val response: MailjetResponse = client.post(request)
            Log.d("MailJet", "Email sent successfully: ${response.status}")
            _success.value = false
            _isLoading.value = false
            _email.value = emailAddress
        }catch (e: Exception)
        {
            Log.e("MailJet", "Error sending email: ${e.message}")
            e.printStackTrace()
        }
    }
}