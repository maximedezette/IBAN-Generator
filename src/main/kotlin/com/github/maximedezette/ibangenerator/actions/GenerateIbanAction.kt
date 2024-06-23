package com.github.maximedezette.ibangenerator.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import org.jetbrains.plugins.template.FrenchIban


internal class GenerateIbanAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document
        val caretModel = editor.caretModel

        val iban = FrenchIban.create()

        WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(editor.caretModel.offset, iban.getValue())
            caretModel.moveToOffset(caretModel.offset + iban.getValue().length)
        }
    }
}