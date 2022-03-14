package com.example.coineone

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
//this class defines a custom circular progress bar with round edge
class CircularProgressBar : View{

    private var mViewWidth = 0
    private var mViewHeight = 0
    private val mStartAngle = 180f
    private var studyTimePercent = 0
    private var classTimePercent = 0
    private var freeTimePercent = 0

    private var classTimeSweepAngle = 0f
    private var studyTimeSweepAngle = 0f
    private var freeTimeSweepAngle = 0f


    private val mMaxSweepAngle = 360f

    private val mStrokeWidth = 40

    private val mAnimationDuration = 1000

    private val mMaxProgress = 100

    private val mRoundedCorners = true
    private var classTimePaint: Paint? = null
    private var studyTimePaint: Paint? = null
    private var freeTimePaint: Paint? = null
    private var totalText_Paint: Paint? = null




    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        initMeasurments()

        drawOutlineArc(canvas)
    }

    private fun drawOutlineArc(canvas: Canvas) {
        classTimePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        studyTimePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        freeTimePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        totalText_Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val diameter = Math.min(mViewWidth, mViewHeight)
        val pad = (mStrokeWidth / 2.0).toFloat()
        val outerOval = RectF(pad, pad, diameter - pad, diameter - pad)

        classTimePaint?.color = Color.rgb(37, 150, 190)
        classTimePaint?.strokeWidth = mStrokeWidth.toFloat()
        classTimePaint?.isAntiAlias = false
        classTimePaint?.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        classTimePaint?.style = Paint.Style.STROKE

        canvas.drawArc(outerOval, mStartAngle, classTimeSweepAngle, false, classTimePaint!!)

        studyTimePaint?.color = Color.rgb(255, 158, 87)
        studyTimePaint?.strokeWidth = mStrokeWidth.toFloat()
        studyTimePaint?.isAntiAlias = false
        studyTimePaint?.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        studyTimePaint?.style = Paint.Style.STROKE

        canvas.drawArc(outerOval, mStartAngle + classTimeSweepAngle, studyTimeSweepAngle, false, studyTimePaint!!)

        freeTimePaint?.color = Color.rgb(97, 241, 123)
        freeTimePaint?.strokeWidth = mStrokeWidth.toFloat()
        freeTimePaint?.isAntiAlias = false
        freeTimePaint?.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        freeTimePaint?.style = Paint.Style.STROKE

        canvas.drawArc(outerOval, mStartAngle + classTimeSweepAngle + studyTimeSweepAngle, freeTimeSweepAngle, false, freeTimePaint!!)

    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }


    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }


    fun setProgress() {
        val animator_class =ValueAnimator.ofFloat(classTimeSweepAngle, calcSweepAngleFromProgress(classTimePercent))
        animator_class.interpolator = DecelerateInterpolator()
        animator_class.duration = mAnimationDuration.toLong()
        animator_class.addUpdateListener { valueAnimator ->
            classTimeSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator_class.start()
        val animator_study = ValueAnimator.ofFloat(studyTimeSweepAngle, calcSweepAngleFromProgress(studyTimePercent))
        animator_study.interpolator = DecelerateInterpolator()
        animator_study.duration = mAnimationDuration.toLong()
        animator_study.addUpdateListener { valueAnimator ->
            studyTimeSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator_study.start()
        val animator_free = ValueAnimator.ofFloat(freeTimeSweepAngle, calcSweepAngleFromProgress(freeTimePercent))
        animator_free.interpolator = DecelerateInterpolator()
        animator_free.duration = mAnimationDuration.toLong()
        animator_free.addUpdateListener { valueAnimator ->
            freeTimeSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator_free.start()
    }
    fun setProgressValue(studytime: Int, classtime: Int, freetime: Int) {
        val totaltime = (studytime + classtime + freetime).toFloat()
        if (totaltime == 0f) {
            studyTimePercent = 0
            classTimePercent = 0
            freeTimePercent = 0
        } else {
            studyTimePercent = (studytime / totaltime * 100).toInt()
            classTimePercent = (classtime / totaltime * 100).toInt()
            freeTimePercent = (freetime / totaltime * 100).toInt()
        }
        setProgress()
    }
}