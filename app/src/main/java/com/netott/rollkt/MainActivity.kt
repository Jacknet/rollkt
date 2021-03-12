package com.netott.rollkt

// Android global environment libs
import android.content.Context
// OS build declarations
import android.os.Build
// OS bundle
import android.os.Bundle
// Vibration and effect components
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.VibrationEffect.createOneShot
import android.os.Vibrator
// Viewport
import android.view.View
// Widgets
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
// AndroidX app compatibility
import androidx.appcompat.app.AppCompatActivity
// Java util libs for Random()
import java.util.*

// Main app activity
class MainActivity : AppCompatActivity() {
    // On-create function for app elements, override default
    override fun onCreate(savedInstanceState: Bundle?) {
        // On-create with saved instance state
        super.onCreate(savedInstanceState)

        // Set content view to project activity_main layout
        setContentView(R.layout.activity_main)

        // Get roll button from view
        val rollButton = findViewById<View>(R.id.rollButton) as Button

        // Get result text from view
        val resultsTextView = findViewById<View>(R.id.resultsTextView) as TextView

        // Get seek bar from view
        val seekBar = findViewById<View>(R.id.seekBar) as SeekBar

        // Get vibration service
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Set the on-click event for the roll button
        rollButton.setOnClickListener {
            // If seek bar is greater than 0
            if (seekBar.progress > 0) {
                // Get a random integer that tops
                val rand = (Random().nextInt(seekBar.progress) + 1).toString()
                resultsTextView.text = rand
            } else {
                // If device API level is 29 (Android 10)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Display an otter instead if seek bar reports 0 or lower
                    resultsTextView.text = String(Character.toChars(0x1F9A6))
                } else {
                    // Display a sailboat for earlier API levels, including target 19 (KitKat)
                    resultsTextView.text = String(Character.toChars(0x26F5))
                }
            }

            // If phone supports vibration
            if (vibrator.hasVibrator()) {
                // If device API level is 26 (Oreo) or larger
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Use new vibrate function, one shot default amplitude. Vibrate for 100ms.
                    vibrator.vibrate(createOneShot(100, DEFAULT_AMPLITUDE))
                } else {
                    // Otherwise use older vibrate method that can work from API 19 (KitKat).
                    // Vibrate for 100ms.
                    vibrator.vibrate(100)
                }
            }
        }
    }
}