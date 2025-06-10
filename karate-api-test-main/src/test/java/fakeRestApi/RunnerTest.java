package fakeRestApi;

import com.intuit.karate.Runner;

//public class RunnerTest {
//
//    public static void main(String[] args){
//        Runner.path("src/test/java/fakeRestApi")
//                .outputCucumberJson(true)
//                .parallel(1);
//    }
//}


public class RunnerTest {

    public static void main(String[] args){
        Runner.path("classpath:fakeRestApi") // Ruta basada en el classpath
                .outputCucumberJson(true)     // Habilita reporte JSON para Karate
                .parallel(1);                 // Ejecución secuencial (1 hilo)
    }
}
