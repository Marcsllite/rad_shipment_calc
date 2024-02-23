package com.marcsllite;

import com.marcsllite.util.factory.ControllerFactory;

import static org.mockito.Mockito.mock;

public class ControllerFactoryTestObj extends ControllerFactory {
    @Override
    public Object call(Class<?> param) {
        return mock(super.call(param).getClass());
    }
}
