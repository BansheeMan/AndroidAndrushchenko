package com.example.a14_dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a14_dialogs.databinding.ActivityLevel1Binding
import com.example.a14_dialogs.entities.AvailableVolumeValues
import kotlin.properties.Delegates.notNull

class DialogsLevel1Activity : AppCompatActivity() {

    lateinit var binding: ActivityLevel1Binding
    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel1Binding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.showDefaultAlertDialogButton.setOnClickListener(listenerAlertDialogDefault)
        binding.showSingleChoiceAlertDialogButton.setOnClickListener(listenerSingleChoiceAlertDialog)
        binding.showSingleChoiceWithConfirmationAlertDialogButton.setOnClickListener(
            listenerSingleChoiceWithConfirmationAlertDialog
        )
        binding.showMultipleChoiceAlertDialogButton.setOnClickListener(
            listenerMultipleChoiceAlertDialog
        )
        binding.showMultipleChoiceWithConfirmationAlertDialogButton.setOnClickListener(
            listenerMultipleChoiceWithConfirmationAlertDialog
        )

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED
        updateUi()
    }

    // ---------

    private val listenerAlertDialogDefault = View.OnClickListener {
        val listener = DialogInterface.OnClickListener { dialogInterface, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> showToast(R.string.action_yes)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.action_no)
                DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.action_ignore)
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setCancelable(true) //не дает отменить окно если FALSE
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.default_alert_title)
            .setMessage(R.string.default_alert_message)
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_no, listener)
            .setNeutralButton(R.string.action_ignore, listener)
            .setOnCancelListener {  //отрабатывает при отмене
                showToast(R.string.dialog_cancelled)
            }
            .setOnDismissListener {  //отрабатывает в любом случае
                Log.d(TAG, "Dialog dismissed")
            }
            .create()
        dialog.show()
    }

    // ---------

    private val listenerSingleChoiceAlertDialog = View.OnClickListener {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex) { dialog, which ->
                volume = volumeItems.values[which]
                updateUi()
                dialog.dismiss()
            }.create()
        dialog.show()
    }

    // ---------

    private val listenerSingleChoiceWithConfirmationAlertDialog = View.OnClickListener {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm) { d, _ ->
                val index = (d as AlertDialog).listView.checkedItemPosition
                volume = volumeItems.values[index]
                updateUi()
            }.create()
        dialog.show()
    }

    // ---------

    private val listenerMultipleChoiceAlertDialog = View.OnClickListener {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.color_setup)
            .setMultiChoiceItems(colorItems, checkboxes) { _, which, isCheked ->
                colorComponents[which] = if (isCheked) 255 else 0
                this.color = Color.rgb(
                    colorComponents[0],
                    colorComponents[1],
                    colorComponents[2]
                )
                updateUi()
            }
            .setPositiveButton(R.string.action_close, null)
            .create()
        dialog.show()
    }

    // ---------

    private val listenerMultipleChoiceWithConfirmationAlertDialog = View.OnClickListener {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        var color: Int = this.color
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.color_setup)
            .setMultiChoiceItems(colorItems, checkboxes) { _, which, isChecked ->
                colorComponents[which] = if (isChecked) 255 else 0
                color = Color.rgb(
                    colorComponents[0],
                    colorComponents[1],
                    colorComponents[2]
                )
            }
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                this.color = color
                updateUi()
            }
            .create()
        dialog.show()
    }

    // ---------

    private fun updateUi() {
        binding.currentColorTextView.text = getString(R.string.current_volume, volume)
        binding.colorView.setBackgroundColor(color)
    }

    private fun showToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(messageRes: String) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        @JvmStatic
        private val TAG = DialogsLevel1Activity::class.java.simpleName

        @JvmStatic
        private val KEY_VOLUME = "KEY_VOLUME"

        @JvmStatic
        private val KEY_COLOR = "KEY_COLOR"
    }
}