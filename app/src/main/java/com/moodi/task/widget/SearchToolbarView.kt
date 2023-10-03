package com.moodi.task.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.moodi.task.R

/**
 * This class is used to create custom search toolbar.
 * This class is responsible for handling search toolbar events. The reason to
 * create a custom view is to handle events in one place. rather then polluting
 * the activity or fragment code.
 *
 * This class is being used [HomeActivity]
 *
 * clearSearchText() method is used to clear the search text.
 * addClearButtonListener() method is used to add clear button click listener.
 * addBackButtonListener() method is used to add back button click listener.
 * addFocusListener() method is used to add focus listener.
 * addSearchTextChangeListener() method is used to add search text change listener.
 * addEmptyListener() method is used to add empty listener.
 *
 * INPUT_LIMIT is used to set the limit of search text.
 *
 */
class SearchToolbarView : ConstraintLayout {

    private var backButtonClickListener: (() -> Unit)? = null
    private var clearButtonClickListener: (() -> Unit)? = null
    private var focusListener: ((Boolean) -> Unit)? = null
    private var searchSuggestionTextListener: ((String) -> Unit)? = null
    private var emptyListener: (() -> Unit)? = null

    private var _backButton: Boolean? = false
    private var _clearButton: Boolean? = false

    private lateinit var backImageButton: ImageButton
    private lateinit var clearImageButton: ImageButton
    private lateinit var searchEditText: EditText

    private var backButton: Boolean?
        get() = _backButton
        set(value) {
            _backButton = value
            refresh()
        }
    private var clearButton: Boolean?
        get() = _clearButton
        set(value) {
            _clearButton = value
            refresh()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        inflate(context, R.layout.search_toolbar_layout, this)

        backImageButton = findViewById(R.id.back_image_button)
        searchEditText = findViewById(R.id.search_edit_textview)
        clearImageButton = findViewById(R.id.clear_image_button)

        searchEditText.clearFocus()
        searchEditText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            backButton = hasFocus
            focusListener?.invoke(hasFocus)
        }

        backImageButton.setOnClickListener {
            hideKeyboard()
            searchEditText.clearFocus()
            searchEditText.setText("")
            backButtonClickListener?.invoke()
            clearButton = false

        }
        clearImageButton.setOnClickListener {
            searchEditText.setText("")
            clearButton = false
            clearButtonClickListener?.invoke()
        }

        searchEditText.addTextChangedListener {
            clearButton = searchEditText.text.toString().isNotEmpty()
            if (searchEditText.text.toString().length >= INPUT_LIMIT) {
                searchSuggestionTextListener?.invoke(searchEditText.text.toString())
            } else if (searchEditText.text.toString().isEmpty()) {
                emptyListener?.invoke()
            }
        }

        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SearchToolbarView, defStyle, 0
        )

        _backButton = a.getBoolean(R.styleable.SearchToolbarView_back_button, false)

        a.recycle()

        refresh()
    }

    /**
     * This method is used to hide the keyboard.
     */
    private fun hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     *
     */
    private fun refresh() {

        if (backButton!!) {
            backImageButton.visibility = VISIBLE
        } else {
            backImageButton.visibility = GONE
        }

        if (clearButton!!) {
            clearImageButton.visibility = VISIBLE
        } else {
            clearImageButton.visibility = GONE
        }
    }

    /**
     * This method is used to clear the search text.
     */
    fun clearSearchText() {
        searchEditText.setText("")
        searchEditText.clearFocus()
    }

    /**
     * This method is used to add clear button click listener.
     */
    fun addClearButtonListener(body: () -> Unit) {
        this.clearButtonClickListener = body
    }

    /**
     * This method is used to add back button click listener.
     */
    fun addBackButtonListener(body: () -> Unit) {
        this.backButtonClickListener = body
    }

    /**
     * This method is used to add focus listener.
     */
    fun addFocusListener(body: (Boolean) -> Unit) {
        this.focusListener = body
    }

    /**
     * This method is used to add search text change listener.
     */
    fun addSearchTextChangeListener(body: (String) -> Unit) {
        this.searchSuggestionTextListener = body
    }

    /**
     * This method is used to add empty listener.
     * This listener is used to clear the search text when the search text is empty.
     * As this is requirement that if user goes back to search view, it should be clear when it is empty.
     * Usually the approach is to show recent searches when the search text is empty.
     *
     */
    fun addEmptyListener(body: () -> Unit) {
        this.emptyListener = body
    }

    companion object {
        /**
         * This is used to set the limit of search text.
         */
        const val INPUT_LIMIT = 2
    }
}