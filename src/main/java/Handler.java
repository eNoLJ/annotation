import annotation.Controller;
import annotation.GetMapping;
import annotation.PostMapping;
import controller.UserController;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.Set;

// 입력값 예시
// GET /signIn.html
// GET /question.html

public class Handler {

    private static Class<?> targetClass;

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Scanner sc = new Scanner(System.in);
        String request;

        while (!(request = sc.nextLine()).equals("exit")) {
            String[] splitRequest = request.split(" ");
            String method = splitRequest[0];
            String url = splitRequest[1];
            Method controllerMethod = findControllerMethod(method, url);

            if (controllerMethod != null) {
                controllerMethod.invoke(targetClass.getConstructor().newInstance());
            }

            sc = new Scanner(System.in);
        }
    }

    private static Method findControllerMethod(String method, String url) throws ClassNotFoundException {
        Reflections reflections = new Reflections("controller");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> clazz : classes) {
            Method controllerMethod = findMethodByClass(clazz, method, url);
            if (controllerMethod != null) {
                targetClass = Class.forName(clazz.getName());
                return controllerMethod;
            }
        }

        return null;
    }

    private static Method findMethodByClass(Class<?> controller, String method, String url) {
        Method[] methods = controller.getMethods();

        for (Method controllerMethod : methods) {
            if (controllerMethod.isAnnotationPresent(GetMapping.class) && method.equals("GET")) {
                GetMapping getMapping = controllerMethod.getDeclaredAnnotation(GetMapping.class);
                if (getMapping.value().equals(url)) {
                    return controllerMethod;
                }
            }
            if (controllerMethod.isAnnotationPresent(PostMapping.class) && method.equals("POST")) {
                PostMapping postMapping = controllerMethod.getDeclaredAnnotation(PostMapping.class);
                if (postMapping.value().equals(url)) {
                    return controllerMethod;
                }
            }
        }

        return null;
    }
}
