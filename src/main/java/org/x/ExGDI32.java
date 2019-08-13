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
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.WTypes.LPSTR;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.win32.W32APIOptions;

public interface ExGDI32 extends GDI32 {
	public final static int HORZRES = 8;
	public final static int VERTRES = 10;
	
	/** The instance. */
	ExGDI32 INSTANCE = Native.load("gdi32", ExGDI32.class, W32APIOptions.DEFAULT_OPTIONS);

	int ExtEscape(HDC hdc, int iEscape, int cjInput, String lpInData, int cjOutput, LPSTR lpOutData);

	/**
	 * The CreateDC function creates a device context (DC) for a device using
	 * the specified name.
	 * 
	 * @param pwszDriver A pointer to a null-terminated character string that
	 *            specifies either DISPLAY or the name of a specific display
	 *            device. For printing, we recommend that you pass NULL to
	 *            lpszDriver because GDI ignores lpszDriver for printer devices.
	 * @param pwszDevice A pointer to a null-terminated character string that
	 *            specifies the name of the specific output device being used,
	 *            as shown by the Print Manager (for example, Epson FX-80). It
	 *            is not the printer model name. The lpszDevice parameter must
	 *            be used.
	 * @param pszPort This parameter is ignored and should be set to NULL. It is
	 *            provided only for compatibility with 16-bit Windows.
	 * @param pdm A pointer to a DEVMODE structure containing device-specific
	 *            initialization data for the device driver. The
	 *            DocumentProperties function retrieves this structure filled in
	 *            for a specified device. The pdm parameter must be NULL if the
	 *            device driver is to use the default initialization (if any)
	 *            specified by the user.
	 * 
	 *            If lpszDriver is DISPLAY, pdm must be NULL; GDI then uses the
	 *            display device's current DEVMODE.
	 * @return If the function succeeds, the return value is the handle to a DC
	 *         for the specified device.
	 * 
	 *         If the function fails, the return value is NULL.
	 */
	HDC CreateDCA(byte[] pwszDriver, byte[] pwszDevice, byte[] pszPort, DEVMODEA pdm);
}
