package com.sharkz.annotation_compiler;

import com.google.auto.service.AutoService;
import com.sharkz.annotation.Path;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/17  20:44
 * 描    述 注解处理器 --> 拿到标记了的Activity 生成代码的
 * 1.AbstractProcessor 注解处理器的父类 系统提供的
 * 2.注册处理器注册给虚拟机
 *
 * @AutoService(Processor.class) // 注册了注解处理器
 * 修订历史：
 * ================================================
 */
@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {


    /**
     *
     */
    private String mModuleName;

    /**
     *
     */
    private RouterLogger mRouterLogger;


    // =============================================================================================


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mRouterLogger = new RouterLogger(processingEnv.getMessager());

        // javaCompileOptions {
        //            annotationProcessorOptions {
        //                arguments = [moduleName: project.getName()]
        //            }
        // }
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && !options.isEmpty()) {
            mModuleName = options.get(RouterConstants.OPTION_MODULE_NAME);
            mRouterLogger.setOpenLog("true".equals(options.get(RouterConstants.OPTION_OPEN_LOG)));
        }
    }

    /**
     * 声明这个注解处理器需要识别的注解有哪些
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Path.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的Java版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 支持最新版
        return processingEnv.getSourceVersion();
    }

    /**
     * 核心方法
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 获取到当前模块中用到了BindPath注解的Activity的类对象（类节点）
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Path.class);
        if (elements == null || elements.isEmpty()) {
            return true;
        }

        // > Task :module1:javaPreCompileDebug
        mRouterLogger.info(String.format("> %s : %s :  begin", RouteProcessor.class.getSimpleName(), mModuleName));

        Map<String, ClassName> map = new HashMap<>(16);
        // 遍历整个模块中用到了bindPath注解的节点
        for (Element element : elements) {
            if (!element.getKind().isClass()) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            if (!validateClass(typeElement)) {
                continue;
            }
            // 获取到Activity上面的Path的注解
            Path annotation = typeElement.getAnnotation(Path.class);
            // 获取到注解里面的值 中间容器map 的Activity 所对应的key
            String[] paths = annotation.value();
            // 获取到包名加类名
            ClassName className = ClassName.get(typeElement);
            mRouterLogger.info(String.format("> %s : %s :  found routed target: %s", RouteProcessor.class.getSimpleName(), mModuleName,
                    typeElement.getQualifiedName()));
            for (String path : paths) {
                if (map.containsKey(path)) {
                    throw new RuntimeException(String.format("> %s : %s : repeated : %s[%s, %s]", RouteProcessor.class.getSimpleName(), mModuleName,
                            path, typeElement.getQualifiedName(), map.get(path)));
                }
                // TODO 存起来
                map.put(path, className);
            }
        }
        if (map.isEmpty()) {
            return true;
        }
        if (mModuleName == null) {
            throw new RuntimeException(String.format("> %s : %s : mModuleName == null", RouteProcessor.class.getSimpleName(), mModuleName,
                    RouterConstants.OPTION_MODULE_NAME));
        }
        String validModuleName = mModuleName.replace(".", "_").replace("-", "_");


        // javaPoet  start
        MethodSpec.Builder methodPutActivity = MethodSpec.methodBuilder(RouterConstants.METHOD_PUT_ACTIVITY)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);

        // not exist
        ClassName ARouter = ClassName.get("com.example.lib_core", "ARouter");

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            ClassName className = map.get(path);
            // $S  string  $T type.class
            // auto import
            methodPutActivity.addStatement("$T.getInstance().putActivity($S, $T.class)", ARouter, path, className);
        }

        TypeElement interfaceType = processingEnv.getElementUtils().getTypeElement(RouterConstants.IROUTE_FULL_NAME);
        String className = capitalize(validModuleName) + RouterConstants.IROUTE;
        TypeSpec type = TypeSpec.classBuilder(className)
                .addSuperinterface(ClassName.get(interfaceType))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodPutActivity.build())
                .addJavadoc(RouterConstants.CLASS_JAVA_DOC)
                .build();
        try {
            mRouterLogger.info(String.format("> %s : %s :  routed create: %s.%s", RouteProcessor.class.getSimpleName(), mModuleName,
                    RouterConstants.APT_PACKAGE_NAME, className));
            JavaFile.builder(RouterConstants.APT_PACKAGE_NAME, type).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRouterLogger.info(String.format("> %s : %s :  end", RouteProcessor.class.getSimpleName(), mModuleName));

        return true;
    }


    // =============================================================================================


    private String capitalize(CharSequence self) {
        return self.length() == 0 ? "" :
                "" + Character.toUpperCase(self.charAt(0)) + self.subSequence(1, self.length());
    }

    private boolean validateClass(TypeElement typeElement) {
        // not activity/fragment
        if (!isSubType(typeElement, RouterConstants.ACTIVITY_FULL_NAME) && !isSubType(typeElement, RouterConstants.FRAGMENT_X_FULL_NAME)) {
            mRouterLogger.error(typeElement, String.format("> %s : %s :  %s is activity/fragment", RouteProcessor.class.getSimpleName(), mModuleName,
                    typeElement.getSimpleName().toString()));
            return false;
        }
        //  abstract class
        Set<Modifier> modifiers = typeElement.getModifiers();
        if (modifiers.contains(Modifier.ABSTRACT)) {
            mRouterLogger.error(typeElement, String.format("> %s : %s :  %s is abstract", RouteProcessor.class.getSimpleName(), mModuleName,
                    (typeElement).getQualifiedName()));
            return false;
        }
        return true;
    }

    private boolean isSubType(Element typeElement, String type) {
        return processingEnv.getTypeUtils().isSubtype(typeElement.asType(),
                processingEnv.getElementUtils().getTypeElement(type).asType());
    }


}
