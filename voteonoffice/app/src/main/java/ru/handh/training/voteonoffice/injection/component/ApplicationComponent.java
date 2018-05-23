package ru.handh.training.voteonoffice.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.handh.training.voteonoffice.data.DataManager;
import ru.handh.training.voteonoffice.data.SyncService;
import ru.handh.training.voteonoffice.data.local.DatabaseHelper;
import ru.handh.training.voteonoffice.data.local.PreferencesHelper;
import ru.handh.training.voteonoffice.data.remote.RibotsService;
import ru.handh.training.voteonoffice.injection.ApplicationContext;
import ru.handh.training.voteonoffice.injection.module.ApplicationModule;
import ru.handh.training.voteonoffice.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
