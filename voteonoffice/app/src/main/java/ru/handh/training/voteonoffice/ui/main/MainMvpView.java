package ru.handh.training.voteonoffice.ui.main;

import java.util.List;

import ru.handh.training.voteonoffice.data.model.Ribot;
import ru.handh.training.voteonoffice.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
