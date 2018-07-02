package ru.handh.training.voteonoffice.injection.component;

import dagger.Subcomponent;
import ru.handh.training.voteonoffice.injection.PerActivity;
import ru.handh.training.voteonoffice.injection.module.ActivityModule;
import ru.handh.training.voteonoffice.ui.login.LogInActivity;
import ru.handh.training.voteonoffice.ui.main.MainActivity;
import ru.handh.training.voteonoffice.ui.signup.SignUpActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(LogInActivity logInActivity);
    void inject(SignUpActivity signUpActivity);

}
