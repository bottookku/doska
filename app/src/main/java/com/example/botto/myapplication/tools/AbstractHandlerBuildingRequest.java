package com.example.botto.myapplication.tools;

import java.util.Objects;

/**
 * Created by bottookku on 12.01.2020.
 */

public abstract class AbstractHandlerBuildingRequest {
    public abstract void setterFromAsyncTask(String string, boolean isNoInet);
    public abstract void actionFromAsyncTask();
}
