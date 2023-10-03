@file:Suppress("UnstableApiUsage")

package com.moodi.issue

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.moodi.lint.NonNullAssertionDetector

val NON_NULL_ASSERTION_ISSUE = Issue.create(
    "NonNullAssertion",
    "Non-null assertion (!!) used",
    "Don't use non-null assertions (!!). Design your code better to avoid needing it in the first place. As a last resort, replace it with checkNotNull() function.",
    Implementation(NonNullAssertionDetector::class.java, Scope.JAVA_FILE_SCOPE),
    "https://android.jlelse.eu/how-to-remove-all-from-your-kotlin-code-87dc2c9767fb",
    Category.CORRECTNESS,
    5,
    Severity.ERROR
)