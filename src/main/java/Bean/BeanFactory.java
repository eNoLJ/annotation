package Bean;

import annotation.Component;
import annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {

    public final Map<String, Object> beanFactory = new HashMap<>();

    public BeanFactory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init();
    }

    private void init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> componentClassList = findAnnotationForComponent();
        List<Class<?>> targetClassList;

        for (Class<?> componentClass : componentClassList) {
            targetClassList = findClassByAnnotation();

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

    private List<Class<?>> findClassByAnnotation() {
        Reflections reflections = new Reflections("controller");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        return new ArrayList<>(classes);
    }
}
