package com.moodi.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.moodi.issue.NOT_NULL_ASSERTION_ISSUE
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UPostfixExpression

class NonNullAssertionDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UPostfixExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitPostfixExpression(node: UPostfixExpression) {
                if (node.asRenderString().contains("!!")) {
                    context.report(
                        NOT_NULL_ASSERTION_ISSUE,
                        node,
                        context.getLocation(node),
                        "This code includes non-null assertion (!!)"
                    )
                }
            }
        }
    }
}
