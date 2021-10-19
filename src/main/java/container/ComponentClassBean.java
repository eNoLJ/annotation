package container;

import annotation.Component;
import annotation.Controller;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ComponentClassBean {

    public final Map<String, Object> beanFactory = new HashMap<>();

    public ComponentClassBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init();
    }

    public Object getBean(String beanName) {
        return beanFactory.get(beanName);
    }

    private void init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> componentClassList = findAnnotationForComponent();
        List<Class<?>> targetClassList;

        for (Class<?> componentClass : componentClassList) {
            System.out.println(componentClass);
            targetClassList = findClassByAnnotation(componentClass);

            for (Class<?> clazz : targetClassList) {
                Object newClass = clazz.getConstructor().newInstance();
                beanFactory.put(clazz.getName(), newClass);
            }
        }
    }

    private List<Class<?>> findAnnotationForComponent() {
        Reflections reflections = new Reflections("annotation");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);
        return new ArrayList<>(classes);
    }

    private List<Class<?>> findClassByAnnotation(Class<?> clazz) {
        Reflections reflections = new Reflections("controller");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) clazz);
        return new ArrayList<>(classes);
    }
}
