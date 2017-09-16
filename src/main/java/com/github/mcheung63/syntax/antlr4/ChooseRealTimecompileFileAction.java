package com.github.mcheung63.syntax.antlr4;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

@ActionID(
		category = "Bugtracking",
		id = "com.github.mcheung63.ChooseRealTimecompileFileAction"
)
@ActionRegistration(
		displayName = "#CTL_ChooseRealTimecompileFileAction"
)
@Messages("CTL_ChooseRealTimecompileFileAction=ChooseRealTimecompileFileAction")
public final class ChooseRealTimecompileFileAction extends AbstractAction implements Presenter.Toolbar {

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public Component getToolbarPresenter() {
		return new ChooseRealTimecompileFilePanel();
	}
}
