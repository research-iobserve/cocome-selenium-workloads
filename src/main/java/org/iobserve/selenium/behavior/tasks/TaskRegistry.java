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
                final List<Class<?>> parameterTypes = new ArrayList<>();
                final List<Object> parameterValues = new ArrayList<>();

                for (final Constructor<?> constructor : searchedWorkload.getConstructors()) {
                    if (constructor.getParameterCount() == parameters.size()) {
                        if (constructor.getAnnotationsByType(Parameters.class).length == 1) {
                            final Parameters annotation = constructor.getAnnotationsByType(Parameters.class)[0];

                            for (int i = 0; i < constructor.getParameterCount(); i++) {
                                final String parameterName = annotation.names()[i];
                                final Class<?> parameterType = constructor.getParameters()[i].getType();
                                final Object value = parameters.get(parameterName);
                                if (value == null) {
                                    TaskRegistry.LOGGER.error(
                                            "No parameter with the name '{}' found in the list of specified parameters.",
                                            parameterName);
                                } else {
                                    parameterTypes.add(parameterType);
                                    TaskRegistry.addParameterValues(parameterType, parameterValues, value);
                                }
                            }
                            if (parameterTypes.size() == parameters.size()) {
                                return (AbstractTask) constructor.newInstance(parameterValues.toArray());
                            } else {
                                parameterTypes.clear();
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
     * Add a value of defined type to the parameterValues.
     *
     * @param parameterType
     *            type
     * @param parameterValues
     *            list of values
     * @param value
     *            value to be added
     */
    private static void addParameterValues(final Class<?> parameterType, final List<Object> parameterValues,
            final Object value) {
        if (parameterType.isEnum()) {
            final Object[] items = parameterType.getEnumConstants();
            for (final Object item : items) {
                if (item.toString().equals(value)) {
                    parameterValues.add(item);
                }
            }
        } else {
            if (parameterType.isAssignableFrom(value.getClass())) {
                parameterValues.add(value);
            } else if (parameterType.isPrimitive()) {
                if ("int".equals(parameterType.getName()) && Integer.class.isAssignableFrom(value.getClass())) {
                    parameterValues.add(value);
                } else {
                    TaskRegistry.LOGGER.error("Primitive Value type {} cannot be handled.", parameterType.getName());
                }
            } else if (Map.class.isAssignableFrom(value.getClass())) {
                /** this indicates a specified range. */
                @SuppressWarnings("unchecked")
                final Map<String, Integer> map = (Map<String, Integer>) value;
                final Integer lower = map.get("min");
                final Integer upper = map.get("max");
                if (lower != null && upper != null) {
                    if (lower < upper) {
                        parameterValues.add(RandomGenerator.getRandomNumber(lower, upper));
                    } else {
                        TaskRegistry.LOGGER.error("Min value must be lower than max value, but found min: {} max: {}",
                                lower, upper);
                    }
                } else {
                    TaskRegistry.LOGGER.error("Need one min and one max value.");
                }
            } else {
                TaskRegistry.LOGGER.error("Value type {} cannot be handled.", value.getClass().getCanonicalName());
            }
        }

    }
}
