/**
 * XDAMAGE Java Bindings - This is a simple extension of JNA Platform, adding the XFixes and XDamage extensions (and a couple more X11 functions)
 * Copyright © ${project.inceptionYear} SSHTOOLS Limited (support@sshtools.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.x;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public interface ExtUser32 extends User32 {
	/** The instance. */
	ExtUser32 INSTANCE = (ExtUser32) Native.loadLibrary("user32", ExtUser32.class, W32APIOptions.DEFAULT_OPTIONS);

	/**
	 * The GetCursorPos function retrieves the position of the mouse cursor, in
	 * screen coordinates.
	 * 
	 * @param lpPoint Pointer to a LPPOINT structure to receive the information.
	 * @return If the function succeeds, the return value is nonzero. If the
	 *         function fails, the return value is zero.
	 */
	boolean GetCursorPos(POINT lpPoint);

	public static class CWPSTRUCT extends Structure {
		public LPARAM lParam;
		public WPARAM wParam;
		public int message;
		public HWND hwnd;

		protected List<?> getFieldOrder() {
			return Arrays.asList("lParam", "wParam", "message");
		}
	}

	public final static int HC_ACTION = 0;
	public final static int HC_NOREMOVE = 3;
	public final static int WH_CALLWNDPROC = 4;
	public final static int WH_GETMESSAGE = 3;
	public final static int WH_CALLWNDPROCRET = 12;
	public final static int WH_CBT = 5;
	public final static int WH_DEBUG = 9;
	public final static int WH_SYSMSGFILTER = 6;
	public final static int WM_CREATE = 0x0001;
	public final static int WM_DESTROY = 0x0002;
	public final static int WM_WINDOWPOSCHANGING = 0x0046;

	interface EnumWindowsProc {
		boolean callback(HWND hwnd, CWPSTRUCT lParam);
	}

	interface CallWndProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, CWPSTRUCT lParam);
	}

	interface GetMsgProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, CWPSTRUCT lParam);
	}

	interface SysMsgProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, CWPSTRUCT lParam);
	}
}
