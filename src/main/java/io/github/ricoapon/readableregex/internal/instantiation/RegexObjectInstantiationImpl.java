package io.github.ricoapon.readableregex.internal.instantiation;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.github.ricoapon.readableregex.ReadableRegexPattern;
import io.github.ricoapon.readableregex.RegexObjectInstantiation;
import io.github.ricoapon.readableregex.RegexObjectInstantiationException;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RegexObjectInstantiation}.
 * @param <T> The type of the object to instantiate.
 */
public class RegexObjectInstantiationImpl<T> implements RegexObjectInstantiation {
    /**
     * See {@link RegexObjectInstantiation#instantiateObject(ReadableRegexPattern, String, Class)}.
     * @param pattern The pattern.
     * @param data    The data.
     * @param clazz   The class of the object to instantiate.
     * @return Instance of {@link T}.
     */
    public T constructObject(ReadableRegexPattern pattern, String data, Class<T> clazz) {
        Constructor<?> constructor = determineConstructorForInjection(clazz);
        List<ParameterInfo> parameterInfoList = determineParameterNamesAndTypes(constructor);
        checkThatPatternHaveANamedGroupForEachParameter(pattern, clazz, parameterInfoList);
        Matcher matcher = createExactMatcher(pattern, data);
        Object[] constructorArgs = createConstructorArgs(parameterInfoList, matcher);
        return createNewInstance(constructor, constructorArgs);
    }

    /**
     * Finds the constructor we should use for instantiation. We have the following criteria:
     * <ul>
     *     <li>If there is only one constructor, use that one.</li>
     *     <li>If there is more than one constructor, use the constructor that is annotated with {@link Inject}</li>.
     *     <li>If there are multiple constructors annotated with {@link Inject}, throw an exception.</li>
     * </ul>
     * @param clazz The class of the object to instantiate.
     * @return The constructor.
     */
    private Constructor<?> determineConstructorForInjection(Class<T> clazz) {
        if (clazz.getConstructors().length == 1) {
            return clazz.getConstructors()[0];
        }

        List<Constructor<?>> validConstructors = Arrays.stream(clazz.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());

        if (validConstructors.size() > 1) {
            throw new RegexObjectInstantiationException("The class " + clazz.getName() + " has more than one constructor annotated " +
                    "with @Inject. This is not possible. Fix your code by making sure at most one constructor is annotated with @Inject.");
        }

        return validConstructors.get(0);
    }

    /**
     * Creates a list containing the needed information about the constructor parameters. This contains:
     * <ul>
     *     <li>The name of the parameter.</li>
     *     <li>The type of the parameter.</li>
     * </ul>
     * @param constructor The constructor.
     * @return List with {@link ParameterInfo}.
     */
    private List<ParameterInfo> determineParameterNamesAndTypes(Constructor<?> constructor) {
        Paranamer paranamer = new BytecodeReadingParanamer();

        String[] parameterNames = paranamer.lookupParameterNames(constructor);
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        List<ParameterInfo> parameterInfos = new ArrayList<>();
        for (int i = 0; i < constructor.getParameters().length; i++) {
            parameterInfos.add(new ParameterInfo(parameterNames[i], parameterTypes[i]));
        }
        return parameterInfos;
    }

    /**
     * Checks that there exists a parameter name for which there does not exist a group in the pattern.
     * @param pattern           The pattern.
     * @param clazz             The class of the object to instantiate.
     * @param parameterInfoList The information about the parameters of the constructor.
     * @throws RegexObjectInstantiationException If the check failed.
     */
    private void checkThatPatternHaveANamedGroupForEachParameter(ReadableRegexPattern pattern, Class<T> clazz, List<ParameterInfo> parameterInfoList)
            throws RegexObjectInstantiationException {
        for (ParameterInfo parameterInfo : parameterInfoList) {
            if (!pattern.groups().contains(parameterInfo.getName())) {
                throw new RegexObjectInstantiationException("The constructor of the class " + clazz.getName() + " has a parameter with the name '" +
                        parameterInfo.getName() + "'. But this name does not occur in the given pattern. You can fix this by " +
                        "changing the pattern to add a group with this name.");
            }
        }
    }

    /**
     * Creates a {@link Matcher} based on the given pattern and data.
     * @param pattern The pattern.
     * @param data    The data.
     * @return {@link Matcher} for which {@link Matcher#matches()} is already called and returns {@code true}.
     * @throws RegexObjectInstantiationException If there is no exact match.
     */
    private Matcher createExactMatcher(ReadableRegexPattern pattern, String data) throws RegexObjectInstantiationException {
        Matcher matcher = pattern.matches(data);
        if (!matcher.matches()) {
            throw new RegexObjectInstantiationException("The given pattern does not match the given string. Make sure to write your pattern " +
                    "in such a way that you match the COMPLETE string.");
        }
        return matcher;
    }

    /**
     * Creates an array of objects that can be used to call the constructor.
     * @param parameterInfoList The information about the parameters of the constructor.
     * @param matcher           The matcher containing the parameter values.
     * @return Constructor arguments.
     */
    private Object[] createConstructorArgs(List<ParameterInfo> parameterInfoList, Matcher matcher) {
        Object[] constructorArgs = new Object[parameterInfoList.size()];
        int index = 0;

        for (ParameterInfo parameterInfo : parameterInfoList) {
            String argAsString = matcher.group(parameterInfo.getName());
            constructorArgs[index] = StringConverter.convertStringTo(parameterInfo.getType(), argAsString);
            index++;
        }

        return constructorArgs;
    }

    /**
     * Creates an instance of type {@link T}. Throws an exception if anything goes wrong.
     * @param constructor     The constructor.
     * @param constructorArgs The arguments of the constructor.
     * @return Instance of {@link T}.
     * @throws RegexObjectInstantiationException If the object could not be instantiated.
     */
    private T createNewInstance(Constructor<?> constructor, Object[] constructorArgs) throws RegexObjectInstantiationException {
        try {
            //noinspection unchecked
            return (T) constructor.newInstance(constructorArgs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RegexObjectInstantiationException("Could not instantiate class.", e);
        }
    }
}
