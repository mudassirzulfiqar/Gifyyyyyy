@file:Suppress("UnstableApiUsage")

package com.moodi.issue

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.moodi.lint.NonNullAssertionDetector

val NOT_NULL_ASSERTION_ISSUE = Issue.create(
    "NotNullAssertionIssue",
    "Not-null assertion operator(!!) is used",
    "Avoid employing not-null assertions (!!) ,use elvis operator (?:) instead.",
    Implementation(NonNullAssertionDetector::class.java, Scope.JAVA_FILE_SCOPE),
    "https://kotlinlang.org/docs/null-safety.html#the-operator",
    Category.CORRECTNESS,
    5,
    Severity.ERROR
)