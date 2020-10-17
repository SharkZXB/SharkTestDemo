package com.sharkz.annotation_compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/17  20:44
 * 描    述 打印日志的
 * 修订历史：
 * ================================================
 */
public class RouterLogger {

    /**
     *
     */
    private Messager messager;

    private boolean openLog = true;

    public RouterLogger(Messager messager) {
        this.messager = messager;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }

    public void info(CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, info);
    }

    public void info(Element element, CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, info, element);
    }

    public void warn(CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, info);
    }

    public void warn(Element element, CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, info, element);
    }

    public void error(CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, info);
    }

    public void error(Element element, CharSequence info) {
        if (!openLog) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, info, element);
    }

}
