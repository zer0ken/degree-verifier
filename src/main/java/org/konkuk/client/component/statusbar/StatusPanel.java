package org.konkuk.client.component.statusbar;

import org.konkuk.client.App;
import org.konkuk.client.AppModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatusPanel extends JPanel {
    public StatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new ProgressStatusPanel());
    }
}
