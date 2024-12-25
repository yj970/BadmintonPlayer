package com.yj.badmintonplayer.ui.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about size
</pre> *
 */
class SizeUtils private constructor() {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////
    interface onGetSizeListener {
        fun onGetSize(view: View?)
    }

    companion object {
        /**
         * Value of dp to value of px.
         *
         * @param dpValue The value of dp.
         * @return value of px
         */
        fun dp2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * Value of px to value of dp.
         *
         * @param pxValue The value of px.
         * @return value of dp
         */
        fun px2dp(pxValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * Value of sp to value of px.
         *
         * @param spValue The value of sp.
         * @return value of px
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * Value of px to value of sp.
         *
         * @param pxValue The value of px.
         * @return value of sp
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * Converts an unpacked complex data value holding a dimension to its final floating
         * point value. The two parameters <var>unit</var> and <var>value</var>
         * are as in [TypedValue.TYPE_DIMENSION].
         *
         * @param value The value to apply the unit to.
         * @param unit  The unit to convert from.
         * @return The complex floating point value multiplied by the appropriate
         * metrics depending on its unit.
         */
        fun applyDimension(value: Float, unit: Int): Float {
            val metrics = Resources.getSystem().displayMetrics
            when (unit) {
                TypedValue.COMPLEX_UNIT_PX -> return value
                TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
                TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
                TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
                TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
                TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
            }
            return 0f
        }

        /**
         * Force get the size of view.
         *
         * e.g.
         * <pre>
         * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
         * Override
         * public void onGetSize(final View view) {
         * view.getWidth();
         * }
         * });
        </pre> *
         *
         * @param view     The view.
         * @param listener The get size listener.
         */
        fun forceGetViewSize(view: View, listener: onGetSizeListener?) {
            view.post { listener?.onGetSize(view) }
        }

    }
}