import java.lang.annotation.*;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        Entity entity = new Entity();
        Class clss = entity.getClass();
//        clss=Class.forName("Entity");Entity.class.getName();
        int number = entity.getNumber();
        String name = "";
        System.out.println(number + " " + name);
        try {
            Field field = clss.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(name = (String) field.get(entity));
            field.set(entity, "this is reflection");
            System.out.println(name = (String) field.get(entity));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(number + " " + name);


        Method[] methods = clss.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(Modifier.toString(method.getModifiers()) + " " + method.getName());
        }
//        Method method = clss.getDeclaredMethod("getNumber");
//        method.setAccessible(true);
////        method.invoke(entity); вызов объекта
//        System.out.println("Имя метода :" + method.getName());
//        System.out.println("Модификатор :" + Modifier.toString(method.getModifiers()));

        Field field = clss.getDeclaredField("number");
        field.setAccessible(true);
        field.setInt(entity, 2);
        System.out.println("num: " + " " + field.get(entity));

        Constructor[] constructors = clss.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println("Constr : " + " " + constructor.getName());
        }

        Annotation[] annotations = clss.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Secured) {
                Secured myAnnot = (Secured) annotation;
                System.out.println("name: " + myAnnot.str() + " " + "value: " + myAnnot.val());
            }
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @interface Secured {
        String str();
        int val();
    }

    @Secured(str = "My annot", val = 1)
    public static class Entity {
        private int number;
        private String name = "Awesome";

//        public Entity(int number, String name) {
//            this.number = number;
//            this.name = name;
//        }
        @Secured(str = "My annot", val = 2)
        private int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setName(String name) {
            this.name = name;
        }

        private void printData() {
            System.out.println(number + name);
        }
    }
}