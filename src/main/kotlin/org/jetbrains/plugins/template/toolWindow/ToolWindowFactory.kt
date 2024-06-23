package org.jetbrains.plugins.template.toolWindow

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import org.jetbrains.plugins.template.FrenchIban
import org.jetbrains.plugins.template.Bundle
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.JButton


class ToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {
        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(Bundle.message("iban", "?"))
            var ibanValue = "";

            add(label)
            add(JButton(Bundle.message("generateIban")).apply {
                addActionListener {
                    ibanValue = FrenchIban.create().getValue()
                    label.text = Bundle.message("iban", ibanValue)
                }
            })
            add(JButton(Bundle.message("copy")).apply {
                addActionListener {
                    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                    val selection = StringSelection(ibanValue)
                    clipboard.setContents(selection, selection)
                    Notifications.Bus.notify(
                            Notification(
                                    "IBAN Generator",
                                    Bundle.message("notificationTitle"),
                                    Bundle.message("ibanCopied"),
                                    NotificationType.INFORMATION
                            )
                    )
                }
            })
        }
    }
}
