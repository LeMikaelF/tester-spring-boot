package com.mikaelfrancoeur.testerspringboot.aspect;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.INFO;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableAspectJAutoProxy
@Import({
        MyAspectTest.AnnotationOnType.class,
        MyAspectTest.AnnotationOnMethod.class })
class MyAspectTest implements WithAssertions {

    @MockBean
    private MyAspect myAspect;

    @Autowired
    private AnnotationOnType annotationOnType;

    @Autowired
    private AnnotationOnMethod annotationOnMethod;

    private final RuntimeException exception = new RuntimeException();

    @ParameterizedTest(name = "{0}")
    @MethodSource("methodsToTest")
    void test(String description, Function<TestClassHolder, Consumer<Runnable>> method, boolean shouldBeAdvised) {
        Throwable caughtThrowable = catchThrowable(() -> method.apply(new TestClassHolder(annotationOnMethod, annotationOnType)).accept(throwException()));

        if (shouldBeAdvised) {
            verify(myAspect).afterThrowing(any(), same(exception));
        } else {
            verifyNoInteractions(myAspect);
        }

        assertThat(caughtThrowable).isSameAs(exception);
    }

    private Runnable throwException() {
        return () -> {
            throw exception;
        };
    }

    record TestClassHolder(AnnotationOnMethod annotationOnMethod, AnnotationOnType annotationOnType) {
    }

    static Stream<Arguments> methodsToTest() {
        return Stream.of(
                Arguments.of("annotation on type, package-private", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnType::packagePrivate, false),
                Arguments.of("annotation on type, public", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnType::publicMethod, false),
                Arguments.of("annotation on type, package-private, name starts with save", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnType::savePackagePrivate, false),
                Arguments.of("annotation on type, public, name starts with save", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnType::savePublic, true),
                Arguments.of("annotation on method, package-private", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnMethod::packagePrivate, false),
                Arguments.of("annotation on method, public", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnMethod::publicMethod, false),
                Arguments.of("annotation on method, package-private, name starts with save", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnMethod::savePackagePrivate, false),
                Arguments.of("annotation on method, public, name starts with save", (Function<TestClassHolder, Consumer<Runnable>>)
                        holder -> holder.annotationOnMethod::savePublic, true)
        );
    }

    @LoggedException(level = DEBUG)
    static class AnnotationOnType {
        void packagePrivate(Runnable runnable) {
            runnable.run();
        }

        public void publicMethod(Runnable runnable) {
            runnable.run();
        }

        void savePackagePrivate(Runnable runnable) {
            runnable.run();
        }

        public void savePublic(Runnable runnable) {
            runnable.run();
        }
    }

    static class AnnotationOnMethod {
        @LoggedException(level = INFO)
        void packagePrivate(Runnable runnable) {
            runnable.run();
        }

        @LoggedException(level = INFO)
        public void publicMethod(Runnable runnable) {
            runnable.run();
        }

        @LoggedException(level = INFO)
        void savePackagePrivate(Runnable runnable) {
            runnable.run();
        }

        @LoggedException(level = INFO)
        public void savePublic(Runnable runnable) {
            runnable.run();
        }
    }
}
