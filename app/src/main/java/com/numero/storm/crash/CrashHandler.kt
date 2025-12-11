package com.numero.storm.crash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

/**
 * Global uncaught exception handler that captures crash information
 * and launches the CrashHandlerActivity to display it to the user.
 *
 * This handler intercepts all uncaught exceptions in the application,
 * formats the crash details, and provides a user-friendly crash report screen.
 */
class CrashHandler private constructor(
    private val applicationContext: Context
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    companion object {
        private var instance: CrashHandler? = null

        const val EXTRA_CRASH_INFO = "com.numero.storm.CRASH_INFO"

        /**
         * Initialize the crash handler. Should be called in Application.onCreate()
         */
        fun initialize(context: Context) {
            if (instance == null) {
                instance = CrashHandler(context.applicationContext)
                Thread.setDefaultUncaughtExceptionHandler(instance)
            }
        }
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val crashInfo = buildCrashReport(thread, throwable)
            launchCrashActivity(crashInfo)
        } catch (e: Exception) {
            // If we fail to show our crash screen, fall back to default handler
            defaultHandler?.uncaughtException(thread, throwable)
        } finally {
            // Kill the process
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }

    private fun buildCrashReport(thread: Thread, throwable: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        val stackTrace = stringWriter.toString()

        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date())

        return buildString {
            appendLine("═══════════════════════════════════════")
            appendLine("NUMERO CRASH REPORT")
            appendLine("═══════════════════════════════════════")
            appendLine()
            appendLine("Time: $timestamp")
            appendLine("Thread: ${thread.name} (ID: ${thread.id})")
            appendLine()
            appendLine("───────────────────────────────────────")
            appendLine("DEVICE INFORMATION")
            appendLine("───────────────────────────────────────")
            appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
            appendLine("Brand: ${Build.BRAND}")
            appendLine("Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
            appendLine("Product: ${Build.PRODUCT}")
            appendLine("Hardware: ${Build.HARDWARE}")
            appendLine()
            appendLine("───────────────────────────────────────")
            appendLine("APP INFORMATION")
            appendLine("───────────────────────────────────────")
            try {
                val packageInfo = applicationContext.packageManager
                    .getPackageInfo(applicationContext.packageName, 0)
                appendLine("Package: ${applicationContext.packageName}")
                appendLine("Version: ${packageInfo.versionName}")
                @Suppress("DEPRECATION")
                appendLine("Version Code: ${packageInfo.versionCode}")
            } catch (e: Exception) {
                appendLine("Package info unavailable")
            }
            appendLine()
            appendLine("───────────────────────────────────────")
            appendLine("EXCEPTION")
            appendLine("───────────────────────────────────────")
            appendLine("Type: ${throwable.javaClass.name}")
            appendLine("Message: ${throwable.message ?: "No message"}")
            appendLine()
            appendLine("───────────────────────────────────────")
            appendLine("STACK TRACE")
            appendLine("───────────────────────────────────────")
            append(stackTrace)
            appendLine()
            appendLine("═══════════════════════════════════════")
            appendLine("END OF CRASH REPORT")
            appendLine("═══════════════════════════════════════")
        }
    }

    private fun launchCrashActivity(crashInfo: String) {
        val intent = Intent(applicationContext, CrashHandlerActivity::class.java).apply {
            putExtra(EXTRA_CRASH_INFO, crashInfo)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        applicationContext.startActivity(intent)
    }
}
