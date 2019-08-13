/**
 * XDAMAGE Java Bindings - This is a simple extension of JNA Platform, adding the XFixes and XDamage extensions (and a couple more X11 functions)
 * Copyright © 2012 SSHTOOLS Limited (support@sshtools.com)
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

import org.x.ExWinGDI.DEVMODEA;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public interface ExtUser32 extends User32 {
	/** The instance. */
	ExtUser32 INSTANCE = (ExtUser32) Native.loadLibrary("user32", ExtUser32.class, W32APIOptions.DEFAULT_OPTIONS);
	public final static DWORD ENUM_CURRENT_SETTINGS = new DWORD(-1);
	public final static DWORD ENUM_REGISTRY_SETTINGS = new DWORD(-2);
	public final static DWORD MAXIMUM_ALLOWED = new DWORD(0x02000000);
	
	public final static int CDS_NONE = 0;
	public final static int CDS_UPDATEREGISTRY = 0x00000001;
	public final static int CDS_TEST = 0x00000002;
	public final static int CDS_FULLSCREEN = 0x00000004;
	public final static int CDS_GLOBAL = 0x00000008;
	public final static int CDS_SET_PRIMARY = 0x00000010;
	public final static int CDS_VIDEOPARAMETERS = 0x00000020;
	public final static int CDS_ENABLE_UNSAFE_MODES = 0x00000100;
	public final static int CDS_DISABLE_UNSAFE_MODES = 0x00000200;
	public final static int CDS_RESET = 0x40000000;
	public final static int CDS_RESET_EX = 0x20000000;
	public final static int CDS_NORESET = 0x10000000;

	/**
	 * The GetCursorPos function retrieves the position of the mouse cursor, in
	 * screen coordinates.
	 * 
	 * @param lpPoint Pointer to a LPPOINT structure to receive the information.
	 * @return If the function succeeds, the return value is nonzero. If the
	 *         function fails, the return value is zero.
	 */
	boolean GetCursorPos(POINT lpPoint);

	/**
	 * The EnumDisplaySettings function retrieves information about one of the
	 * graphics modes for a display device. To retrieve information for all the
	 * graphics modes of a display device, make a series of calls to this
	 * function.
	 * 
	 * @param lpszDeviceName A pointer to a null-terminated string that
	 *            specifies the display device about whose graphics mode the
	 *            function will obtain information.
	 * 
	 *            This parameter is either NULL or a DISPLAY_DEVICE.DeviceName
	 *            returned from EnumDisplayDevices. A NULL value specifies the
	 *            current display device on the computer on which the calling
	 *            thread is running.
	 * @param iModeNum The type of information to be retrieved. This value can
	 *            be a graphics mode index or one of the following values.
	 * @param lpDevMode A pointer to a DEVMODE structure into which the function
	 *            stores information about the specified graphics mode. Before
	 *            calling EnumDisplaySettings, set the dmSize member to
	 *            sizeof(DEVMODE), and set the dmDriverExtra member to indicate
	 *            the size, in bytes, of the additional space available to
	 *            receive private driver data.
	 * @return If the function succeeds, the return value is nonzero. If the
	 *         function fails, the return value is zero.
	 */
	boolean EnumDisplaySettingsA(String lpszDeviceName, DWORD iModeNum, DEVMODEA lpDevMode);

	/**
	 * Assigns the specified desktop to the calling thread. All subsequent
	 * operations on the desktop use the access rights granted to the desktop.
	 * 
	 * @param hDesktop A handle to the desktop to be assigned to the calling
	 *            thread. This handle is returned by the CreateDesktop,
	 *            GetThreadDesktop, OpenDesktop, or OpenInputDesktop function.
	 * @return If the function succeeds, the return value is nonzero. If the
	 *         function fails, the return value is zero. To get extended error
	 *         information, call GetLastError.
	 */
	boolean SetThreadDesktop(HDESK hDesktop);

	/**
	 * Closes an open handle to a desktop object.
	 * 
	 * @param hDesktop A handle to the desktop to be closed. This can be a
	 *            handle returned by the CreateDesktop, OpenDesktop, or
	 *            OpenInputDesktop functions. Do not specify the handle returned
	 *            by the GetThreadDesktop function.
	 * @return If the function succeeds, the return value is nonzero. If the
	 *         function fails, the return value is zero. To get extended error
	 *         information, call GetLastError.
	 */
	boolean CloseDesktop(HDESK hDesktop);

	/**
	 * The ChangeDisplaySettingsEx function changes the settings of the
	 * specified display device to the specified graphics mode.
	 * 
	 * @param lpszDeviceName A pointer to a null-terminated string that
	 *            specifies the display device whose graphics mode will change.
	 *            Only display device names as returned by EnumDisplayDevices
	 *            are valid. See EnumDisplayDevices for further information on
	 *            the names associated with these display devices.
	 * @param lpDevMode A pointer to a DEVMODE structure that describes the new
	 *            graphics mode. If lpDevMode is NULL, all the values currently
	 *            in the registry will be used for the display setting. Passing
	 *            NULL for the lpDevMode parameter and 0 for the dwFlags
	 *            parameter is the easiest way to return to the default mode
	 *            after a dynamic mode change.
	 * @param hwnd Reserved; must be NULL.
	 * @param dwflags Indicates how the graphics mode should be changed. This
	 *            parameter can be one of the following values.
	 * @param lParam If dwFlags is CDS_VIDEOPARAMETERS, lParam is a pointer to a
	 *            VIDEOPARAMETERS structure. Otherwise lParam must be NULL.
	 * @return The ChangeDisplaySettingsEx function returns one of the following
	 *         values. Return code Description
	 * 
	 *         DISP_CHANGE_SUCCESSFUL
	 * 
	 *         The settings change was successful.
	 * 
	 *         DISP_CHANGE_BADDUALVIEW
	 * 
	 *         The settings change was unsuccessful because the system is
	 *         DualView capable.
	 * 
	 *         DISP_CHANGE_BADFLAGS
	 * 
	 *         An invalid set of flags was passed in.
	 * 
	 *         DISP_CHANGE_BADMODE
	 * 
	 *         The graphics mode is not supported.
	 * 
	 *         DISP_CHANGE_BADPARAM
	 * 
	 *         An invalid parameter was passed in. This can include an invalid
	 *         flag or combination of flags.
	 * 
	 *         DISP_CHANGE_FAILED
	 * 
	 *         The display driver failed the specified graphics mode.
	 * 
	 *         DISP_CHANGE_NOTUPDATED
	 * 
	 *         Unable to write settings to the registry.
	 * 
	 *         DISP_CHANGE_RESTART
	 * 
	 *         The computer must be restarted for the graphics mode to work.
	 */
	long ChangeDisplaySettingsExA(String lpszDeviceName, DEVMODEA lpDevMode, HWND hwnd, DWORD dwflags, LPVOID lParam);

	/**
	 * @param dwThreadId The thread identifier. The GetCurrentThreadId and
	 *            CreateProcess functions return thread identifiers.
	 * @return If the function succeeds, the return value is a handle to the
	 *         desktop associated with the specified thread. You do not need to
	 *         call the CloseDesktop function to close the returned handle.
	 * 
	 *         If the function fails, the return value is NULL. To get extended
	 *         error information, call GetLastError.
	 */
	HDESK GetThreadDesktop(DWORD dwThreadId);

	/**
	 * Opens the desktop that receives user input.
	 * 
	 * @param dwFlags This parameter can be zero or the following value.
	 *            DF_ALLOWOTHERACCOUNTHOOK 0x0001 Allows processes running in
	 *            other accounts on the desktop to set hooks in this process.
	 * @param fInherit If this value is TRUE, processes created by this process
	 *            will inherit the handle. Otherwise, the processes do not
	 *            inherit this handle.
	 * @param dwDesiredAccess The access to the desktop.
	 * @return If the function succeeds, the return value is a handle to the
	 *         desktop that receives user input. When you are finished using the
	 *         handle, call the CloseDesktop function to close it. If the
	 *         function fails, the return value is NULL. To get extended error
	 *         information, call GetLastError.
	 */
	HDESK OpenInputDesktop(DWORD dwFlags, boolean fInherit, DWORD dwDesiredAccess);

	@FieldOrder({ "lParam", "wParam", "message" })
	public static class CWPSTRUCT extends Structure {
		public LPARAM lParam;
		public WPARAM wParam;
		public int message;
		public HWND hwnd;
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

	class HDESK extends PointerType {
		public HDESK() {
		}

		public HDESK(Pointer pointer) {
			super(pointer);
		}
	}
}
