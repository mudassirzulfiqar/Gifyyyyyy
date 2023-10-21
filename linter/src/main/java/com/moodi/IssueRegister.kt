package com.moodi

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.moodi.issue.NOT_NULL_ASSERTION_ISSUE

class IssueRegister : IssueRegistry() {

    override val minApi: Int
        get() = 6
    override val api: Int
        get() = CURRENT_API
    override val issues: List<Issue>
        get() = listOf(NOT_NULL_ASSERTION_ISSUE)
}