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
    public static Map<String, Class<? extends AbstractUserTask>> getRegisteredWorkloads() {
        LoggerFactory.getLogger(TaskRegistry.class).debug("Filling the registry");
        final Map<String, Class<? extends AbstractUserTask>> registeredWorkloads = new HashMap<>();

        final Reflections reflections = new Reflections("org.iobserve.selenium.behavior.tasks");

        final Set<Class<? extends AbstractUserTask>> types = reflections.getSubTypesOf(AbstractUserTask.class);

        for (final Class<? extends AbstractUserTask> type : types) {
            registeredWorkloads.put(type.getSimpleName(), type);
        }

        return registeredWorkloads;
    }

    /**
     * Lookup the given name and try to create a new Instance of the referenced
     * {@link AbstractBehavior Workload}.
     *
     * @param name
     *            The registered name of the workload
     * @return new Instance of the {@link AbstractBehavior Workload}
     * @throws ConfigurationException
     *             If the workload was not found or could not be created.
     */
    public static AbstractUserTask getWorkloadInstanceByName(final String name, final Map<String, Object> parameters)
            throws ConfigurationException {
        final Logger logger = LoggerFactory.getLogger(TaskRegistry.class);

        logger.debug("Looking for workload with name: {}", name);

        final Class<? extends AbstractUserTask> searchedWorkload = TaskRegistry.getRegisteredWorkloads().get(name);
        if (searchedWorkload == null) {
            logger.debug("'{}' was not found in the registry.", name);
            throw new ConfigurationException("Workload " + name + " was not found in the (internal) registry");
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
                                    break;
                                } else {
                                    parameterTypes.add(parameterType);
                                    if (parameterType.isEnum()) {
                                        final Object[] items = parameterType.getEnumConstants();
                                        for (final Object item : items) {
                                            if (item.toString().equals(value)) {
                                                parameterValues.add(item);
                                            }
                                        }
                                    } else {
                                        parameterValues.add(value);
                                    }
                                }
                            }
                            if (parameterTypes.size() == parameters.size()) {
                                return (AbstractUserTask) constructor.newInstance(parameterValues.toArray());
                            } else {
                                parameterTypes.clear();
                                parameterValues.clear();
                            }
                        }
                    }
                }

                throw new ConfigurationException("Workload " + name + " could not be instantiated");
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                logger.error("{} could not be instantiated.", name, e);
                throw new ConfigurationException("Workload " + name + " could not be instantiated"); // NOPMD
                                                                                                     // stacktrace
                                                                                                     // in
                                                                                                     // error
                                                                                                     // log
            }
        }
    }
}
