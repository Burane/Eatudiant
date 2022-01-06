package com.cnam.eatudiant.view;

import android.content.Context;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.Map;

public interface View {

    Map<String, Observable> getActions();

    Map<String, Consumer> getConsumers();

    Context getContext();
}
