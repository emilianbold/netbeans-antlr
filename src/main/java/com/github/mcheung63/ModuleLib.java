/*
 * Copyright (C) 2017 Peter (mcheung63@hotmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.mcheung63;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import org.netbeans.api.io.IOProvider;
import org.netbeans.api.io.InputOutput;

public class ModuleLib {

	public static boolean isDebug = true;
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:s.S");

	public static void log(String str) {
		if (isDebug) {
			InputOutput io = IOProvider.getDefault().getIO("Antlr", false);
			io.getOut().println(sdf.format(new Date()) + " - " + str);
		}
	}

	public static void log(Object obj) {
		log(obj.toString());
	}

	public static void logNoNewLine(String str) {
		if (isDebug) {
			InputOutput io = IOProvider.getDefault().getIO("Antlr", false);
			io.getOut().print(sdf.format(new Date()) + " - " + str);
		}
	}

	public static void logNoNewLine(Object obj) {
		log(obj.toString());
	}

	public static String printException(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	public static boolean isMac() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			return true;
		} else {
			return false;
		}
	}

	public static void print(JComponent component, String str) {
		ModuleLib.log("+++ " + str + component);
		for (int x = 0; x < component.getComponentCount(); x++) {
			if (component.getComponent(x) instanceof JComponent) {
				print((JComponent) component.getComponent(x), str + "\t");
			}
		}
	}

	public static JComponent getJComponent(JComponent component, Class c, String str) {
		//ModuleLib.log("---- " + str + component.getClass() + " == " + c + " > " + (component.getClass() == c));
		if (component.getClass() == c) {
			return component;
		}
		for (int x = 0; x < component.getComponentCount(); x++) {
			if (component.getComponent(x) instanceof JComponent) {
				JComponent temp = getJComponent((JComponent) component.getComponent(x), c, str + "\t");
				if (temp != null) {
					return temp;
				}
			}
		}
		return null;
	}
}
