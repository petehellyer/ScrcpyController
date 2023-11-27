package com.codertainment.scrcpy.controller.util

import org.jetbrains.plugins.terminal.TerminalView
import com.intellij.openapi.wm.ToolWindowManager
import org.jetbrains.plugins.terminal.TerminalToolWindowFactory
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import java.io.IOException
import org.jetbrains.plugins.terminal.LocalTerminalDirectRunner


class TerminalUtils(private val project: Project) {

    private var terminalWidget: ShellTerminalWidget? = null
    private var window: ToolWindow? = null

    init {
        val terminalView = TerminalView.getInstance(project)
        window = ToolWindowManager.getInstance(project).getToolWindow(TerminalToolWindowFactory.TOOL_WINDOW_ID)
        terminalWidget = terminalView.createLocalShellWidget(project.projectFilePath, "scrcpy")
    }

    fun run(command: String) {
        try {
            terminalWidget?.executeCommand(command)
            //window?.hide(null)
        } catch (e: IOException) {
            LOG.warn("Cannot run command:$command", e)
        }
    }

    fun close() {
        terminalWidget?.close()
    }

    fun isAvailable(): Boolean {
        val window = ToolWindowManager.getInstance(project).getToolWindow(TerminalToolWindowFactory.TOOL_WINDOW_ID)
        return window != null && window.isAvailable
    }

    companion object {
        private val LOG = Logger.getInstance(
            LocalTerminalDirectRunner::class.java
        )
    }
}