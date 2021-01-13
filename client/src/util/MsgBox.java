/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class MsgBox {

    public static void alert(Component c, String content) {
        JOptionPane.showMessageDialog(c, content);
    }

    public static void alert(String content) {
        JOptionPane.showMessageDialog(null, content);
    }

    public static void alertInf(String s) {
        JOptionPane.showMessageDialog(null, s, "AutoCMS Info!!!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alertWar(String s) {
        JOptionPane.showMessageDialog(null, s, "AutoCMS Warning!!!", JOptionPane.WARNING_MESSAGE);
    }

    public static void alertErr(String s) {
        JOptionPane.showMessageDialog(null, s, "AutoCMS Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertInf(Component c, String s) {
        JOptionPane.showMessageDialog(c, s, "AutoCMS Info!!!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alertWar(Component c, String s) {
        JOptionPane.showMessageDialog(c, s, "AutoCMS Warning!!!", JOptionPane.WARNING_MESSAGE);
    }

    public static void alertErr(Component c, String s) {
        JOptionPane.showMessageDialog(c, s, "AutoCMS Error!!!", JOptionPane.ERROR_MESSAGE);
    }
}
