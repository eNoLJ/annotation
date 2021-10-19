import container.ComponentClassBean;
import container.ControllerMethodBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

// 입력값 예시
// GET /signIn.html
// GET /question.html
public class Handler {

    private static Class<?> targetClass;

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Scanner sc = new Scanner(System.in);
        String request;

        ComponentClassBean componentClassBean = new ComponentClassBean();
        ControllerMethodBean controllerMethodBean = new ControllerMethodBean();

        while (!(request = sc.nextLine()).equals("exit")) {
            String[] splitRequest = request.split(" ");
            String method = splitRequest[0];
            String url = splitRequest[1];
            Method controllerMethod = controllerMethodBean.findControllerMethod(url, method);

            if (controllerMethod != null) {
                Object targetClass = componentClassBean.getBean(controllerMethod.getDeclaringClass().getName());
                controllerMethod.invoke(targetClass);
            }

            sc = new Scanner(System.in);
        }
    }
}
