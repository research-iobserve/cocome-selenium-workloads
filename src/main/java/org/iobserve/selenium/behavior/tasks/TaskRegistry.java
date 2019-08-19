/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.iobserve.selenium.behavior.tasks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iobserve.selenium.WorkloadGenerationMain;
import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.common.RandomGenerator;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to (manually) register Workloads. The {@link WorkloadGenerationMain Workload Generator}
 * looks the Workloads up and executes them.
 *
 * @author Marc Adolf
 *
 */
public final class TaskRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRegistry.class);

    private static Map<String, Class<? extends AbstractTask>> registeredWorkloads = null;

    /**
     * Empty Constructor.
     */
    private TaskRegistry() {
    }

    /**
     * Returns the predefined registered workloads their names and the corresponding classes. The
     * keys are used to get the right workloads from the command line input.
     *
     * @return all {@link AbstractBehavior workloads} known by the registry
     */
    public static synchronized Map<String, Class<? extends AbstractTask>> getRegisteredWorkloads() {
        if (TaskRegistry.registeredWorkloads == null) {
            LoggerFactory.getLogger(TaskRegistry.class).debug("Filling the registry");
            TaskRegistry.registeredWorkloads = new HashMap<>();

            final Reflections reflections = new Reflections(TaskRegistry.class.getPackage().getName());

            final Set<Class<? extends AbstractTask>> types = reflections.getSubTypesOf(AbstractTask.class);

            for (final Class<? extends AbstractTask> type : types) {
                TaskRegistry.registeredWorkloads.put(type.getSimpleName(), type);
            }
        }

        return TaskRegistry.registeredWorkloads;
    }

    /**
     * Lookup the given name and try to create a new Instance of the referenced
     * {@link AbstractBehavior Workload}.
     *
     * @param name
     *            The registered name of the workload
     * @param parameters
     *            parameters for the behavior to instantiate
     * @return new Instance of the {@link AbstractBehavior Workload}
     * @throws ConfigurationException
     *             If the workload was not found or could not be created.
     */
    public static AbstractTask getWorkloadInstanceByName(final String name, final Map<String, Object> parameters)
            throws ConfigurationException {
        final Class<? extends AbstractTask> searchedWorkload = TaskRegistry.getRegisteredWorkloads().get(name);
        if (searchedWorkload == null) {
            TaskRegistry.LOGGER.debug("'{}' was not found in the registry.", name);
            throw new ConfigurationException("Workload '" + name + "' was not found in the registry.");
        } else {
            try {
                final List<Object> parameterValues = new ArrayList<>();

                for (final Constructor<?> constructor : searchedWorkload.getConstructors()) {
                    if (constructor.getParameterCount() == parameters.size()) {
                        if (constructor.getAnnotationsByType(Parameters.class).length == 1) {
                            if (TaskRegistry.checkConstructor(parameterValues, constructor, parameters)) {
                                return (AbstractTask) constructor.newInstance(parameterValues.toArray());
                            } else {
                                parameterValues.clear();
                            }
                        }
                    }
                }

                TaskRegistry.LOGGER.error("Workload '{}' could not be instantiated. No matching constructor found.",
                        name);
                throw new ConfigurationException(
                        "Workload '" + name + "' could not be instantiated.  No matching constructor found.");
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                TaskRegistry.LOGGER.error("Workload '{}' could not be instantiated. Due to {}", name, e.getMessage(),
                        e);
                throw new ConfigurationException("Workload '" + name + "' could not be instantiated"); // NOPMD
            }
        }
    }

    /**
     * Check if a constructor exists which matches the defined parameters.
     *
     * @param parameterValues
     *            result value
     * @param constructor
     *            constructor to check
     * @param parameters
     *            input parameters
     * @return returns true on success else false
     */
    private static boolean checkConstructor(final List<Object> parameterValues, final Constructor<?> constructor,
            final Map<String, Object> parameters) {
        final Parameters annotation = constructor.getAnnotationsByType(Parameters.class)[0];
        if (constructor.getParameterCount() == parameters.size()) {
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                final String parameterName = annotation.names()[i];
                final Class<?> parameterType = constructor.getParameters()[i].getType();
                final Object value = parameters.get(parameterName);
                if (value == null) {
                    TaskRegistry.LOGGER.debug("Task {}  Missing parameter {}:{}", constructor.getName(), parameterName,
                            parameterType.getName());
                    return false;
                } else {
                    if (!TaskRegistry.addParameterValues(parameterValues, parameterType, parameterName, value)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            TaskRegistry.LOGGER.debug("Task {}  Expected parameter sequence {} received {}", constructor.getName(),
                    constructor.getParameters().toString(), parameters.keySet().toString());
            return false;
        }
    }

    /**
     * Add a value of defined type to the parameterValues.
     *
     * @param parameterValues
     *            result list of parameter values for the constructor
     * @param type
     *            type of the parameter to add
     * @param name
     *            name of the parameter to be processed, used for error output only
     * @param value
     *            value to be added
     */
    @SuppressWarnings("unchecked")
    private static boolean addParameterValues(final List<Object> parameterValues, final Class<?> type,
            final String name, final Object value) {
        if (type.isEnum()) {
            final Object[] items = type.getEnumConstants();
            for (final Object item : items) {
                if (item.toString().equals(value)) {
                    parameterValues.add(item);
                    return true;
                }
            }
            return false;
        } else if (type.isPrimitive()) {
            TaskRegistry.LOGGER.error("Parameter {}:{}  Primitive value type cannot be handled.", name, type.getName());
            return false;
        } else if (Map.class.isAssignableFrom(value.getClass())) {
            /** this indicates key value pairs. */
            return TaskRegistry.addMapParameterValue(parameterValues, type, name, (Map<String, ?>) value);
        } else if (List.class.isAssignableFrom(value.getClass())) {
            /** this indicates a list. */
            return TaskRegistry.addListParameterValue(parameterValues, type, name, (List<?>) value);
        } else if (type.isAssignableFrom(value.getClass())) {
            parameterValues.add(value);
            return true;
        } else {
            TaskRegistry.LOGGER.debug("Parameter {}:{}  Value type {} cannot be handled.", name, type.getName(),
                    value.getClass().getCanonicalName());
            return false;
        }
    }

    /**
     * Handle list parameter sets. Especially, sequence of names.
     *
     * @param parameterValues
     *            sequence of parameters
     * @param type
     *            type of the parameter
     * @param name
     *            name of the parameter
     * @param map
     *            value of the parameter
     * @return returns true when value can be added, else false
     */
    private static boolean addListParameterValue(final List<Object> parameterValues, final Class<?> type,
            final String name, final List<?> value) {
        // unfortunately, we cannot check the list element type
        parameterValues.add(value);
        return true;
    }

    /**
     * Handle map parameter sets. Especially, min max for ranges.
     *
     * @param parameterValues
     *            sequence of parameters
     * @param type
     *            type of the parameter
     * @param name
     *            name of the parameter
     * @param map
     *            value of the parameter
     *
     * @return returns true when the map parameter spec can be applied, elfes false
     */
    private static boolean addMapParameterValue(final List<Object> parameterValues, final Class<?> type,
            final String name, final Map<String, ?> map) {
        if (map.containsKey("min") && map.containsKey("max")) {
            /** this indicates a specified range. */
            @SuppressWarnings("unchecked")
            final Map<String, Integer> minMaxMap = (Map<String, Integer>) map;

            final Integer lower = minMaxMap.get("min");
            final Integer upper = minMaxMap.get("max");

            if (lower != null && upper != null) {
                if (lower < upper) {
                    parameterValues.add(RandomGenerator.getRandomNumber(lower, upper));
                    return true;
                } else {
                    TaskRegistry.LOGGER.error(
                            "Parameter {}:{}  Min value must be lower than max value, but found min: {} max: {}", name,
                            type.getName(), lower, upper);
                    return false;
                }
            } else {
                TaskRegistry.LOGGER.error("Parameter {}:{}  Need one min and one max value.", name, type.getName());
                return false;
            }
        } else {
            TaskRegistry.LOGGER.error("Parameter {}:{}  Unknown set of keys: {}", name, type.getName(),
                    map.keySet().toString());
            return false;
        }
    }
}
