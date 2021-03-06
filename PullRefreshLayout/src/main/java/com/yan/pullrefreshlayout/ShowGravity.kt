package com.yan.pullrefreshlayout


import android.view.ViewGroup

import androidx.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * refresh show helper
 *
 * @author yanxianwei
 * @date 2017/7/7
 */
class ShowGravity internal constructor(private val prl: PullRefreshLayout) {

    /**
     * show gravity
     * - use by pullRefreshLayout to set show gravity
     */
    var headerShowGravity = FOLLOW
    var footerShowGravity = FOLLOW

    /**
     * @ShowState
     */
    @IntDef(
        FOLLOW,
        FOLLOW_PLACEHOLDER,
        FOLLOW_CENTER,
        PLACEHOLDER,
        PLACEHOLDER_FOLLOW,
        PLACEHOLDER_CENTER,
        CENTER,
        CENTER_FOLLOW
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class ShowState

    fun dellHeaderMoving(moveDistance: Int) {
        if (prl.headerView != null && moveDistance >= 0) {
            when (headerShowGravity) {
                FOLLOW -> prl.headerView?.translationY = moveDistance.toFloat()
                FOLLOW_PLACEHOLDER -> prl.headerView?.translationY =
                    (if (moveDistance <= prl.refreshTriggerDistance)
                        moveDistance
                    else
                        prl.refreshTriggerDistance).toFloat()
                FOLLOW_CENTER -> prl.headerView?.translationY =
                    (if (moveDistance <= prl.refreshTriggerDistance)
                        moveDistance
                    else
                        prl.refreshTriggerDistance + (moveDistance - prl.refreshTriggerDistance) / 2).toFloat()
                PLACEHOLDER_CENTER -> prl.headerView?.translationY =
                    (if (moveDistance <= prl.refreshTriggerDistance)
                        0
                    else
                        (moveDistance - prl.refreshTriggerDistance) / 2).toFloat()
                PLACEHOLDER_FOLLOW -> prl.headerView?.translationY =
                    (if (moveDistance <= prl.refreshTriggerDistance)
                        0
                    else
                        moveDistance - prl.refreshTriggerDistance).toFloat()
                CENTER -> prl.headerView?.translationY = (moveDistance / 2).toFloat()
                CENTER_FOLLOW -> prl.headerView?.translationY =
                    (if (moveDistance <= prl.refreshTriggerDistance)
                        moveDistance / 2
                    else
                        moveDistance - prl.refreshTriggerDistance / 2).toFloat()
            }
        }
    }

    fun dellFooterMoving(moveDistance: Int) {
        if (prl.footerView != null && moveDistance <= 0) {
            when (footerShowGravity) {
                FOLLOW -> prl.footerView?.translationY = moveDistance.toFloat()
                FOLLOW_PLACEHOLDER -> prl.footerView?.translationY =
                    (if (moveDistance >= -prl.loadTriggerDistance) moveDistance else -prl.loadTriggerDistance).toFloat()
                FOLLOW_CENTER -> prl.footerView?.translationY =
                    (if (moveDistance <= -prl.loadTriggerDistance)
                        -prl.loadTriggerDistance + (prl.loadTriggerDistance + moveDistance) / 2
                    else
                        moveDistance).toFloat()
                PLACEHOLDER_CENTER -> prl.footerView?.translationY =
                    (if (moveDistance <= -prl.loadTriggerDistance)
                        (moveDistance + prl.loadTriggerDistance) / 2
                    else
                        0).toFloat()
                PLACEHOLDER_FOLLOW -> prl.footerView?.translationY =
                    (if (moveDistance <= -prl.loadTriggerDistance)
                        moveDistance + prl.loadTriggerDistance
                    else
                        0).toFloat()
                CENTER -> prl.footerView?.translationY = (moveDistance / 2).toFloat()
                CENTER_FOLLOW -> prl.footerView?.translationY =
                    (if (moveDistance <= -prl.loadTriggerDistance)
                        moveDistance + prl.loadTriggerDistance / 2
                    else
                        moveDistance / 2).toFloat()
            }
        }
    }

    fun layout(left: Int, top: Int, right: Int, bottom: Int) {
        if (prl.headerView != null) {
            val paddingLeft = prl.paddingLeft
            val paddingTop = prl.paddingTop
            val lp = prl.headerView?.layoutParams as ViewGroup.MarginLayoutParams
            when (headerShowGravity) {
                FOLLOW, FOLLOW_PLACEHOLDER, FOLLOW_CENTER ->
                    prl.headerView?.let {
                        it.layout(
                            paddingLeft + lp.leftMargin,
                            top + lp.topMargin + paddingTop - it.measuredHeight,
                            paddingLeft + lp.leftMargin + it.measuredWidth,
                            top + lp.topMargin + paddingTop
                        )
                    }
                PLACEHOLDER, PLACEHOLDER_CENTER, PLACEHOLDER_FOLLOW ->
                    prl.headerView?.let {
                        it.layout(
                            paddingLeft + lp.leftMargin, top + paddingTop + lp.topMargin,
                            paddingLeft + lp.leftMargin + it.measuredWidth,
                            top + paddingTop + lp.topMargin + it.measuredHeight
                        )
                    }
                CENTER, CENTER_FOLLOW ->
                    prl.headerView?.let {
                        it.layout(
                            paddingLeft + lp.leftMargin,
                            top + paddingTop - it.measuredHeight / 2,
                            paddingLeft + lp.leftMargin + it.measuredWidth,
                            top + paddingTop + it.measuredHeight / 2
                        )
                    }
            }
        }
        if (prl.footerView != null) {
            val paddingLeft = prl.paddingLeft
            val paddingBottom = prl.paddingBottom
            val lp = prl.footerView?.layoutParams as ViewGroup.MarginLayoutParams
            when (footerShowGravity) {
                FOLLOW, FOLLOW_PLACEHOLDER, FOLLOW_CENTER ->
                    prl.footerView?.let {
                        it.layout(
                            lp.leftMargin + paddingLeft, bottom - lp.topMargin - paddingBottom,
                            lp.leftMargin + paddingLeft + it.measuredWidth,
                            bottom - lp.topMargin - paddingBottom + it.measuredHeight
                        )
                    }
                PLACEHOLDER, PLACEHOLDER_CENTER, PLACEHOLDER_FOLLOW -> prl.footerView?.let {
                    it.layout(
                        lp.leftMargin + paddingLeft,
                        bottom - lp.bottomMargin - paddingBottom - it.measuredHeight,
                        lp.leftMargin + paddingLeft + it.measuredWidth,
                        bottom - lp.bottomMargin - paddingBottom
                    )
                }
                CENTER, CENTER_FOLLOW ->
                    prl.footerView?.let {
                        it.layout(
                            lp.leftMargin + paddingLeft,
                            bottom - paddingBottom - it.measuredHeight / 2,
                            lp.leftMargin + paddingLeft + it.measuredWidth,
                            bottom - paddingBottom + it.measuredHeight / 2
                        )
                    }
            }
        }
    }

    companion object {
        const val FOLLOW = 0
        const val FOLLOW_PLACEHOLDER = 1
        const val FOLLOW_CENTER = 2
        const val PLACEHOLDER = 3
        const val PLACEHOLDER_FOLLOW = 4
        const val PLACEHOLDER_CENTER = 5
        const val CENTER = 6
        const val CENTER_FOLLOW = 7
    }
}
