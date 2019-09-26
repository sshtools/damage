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

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public interface ExtKernel32 extends Kernel32 {

	public final static int PROCESSOR_ARCHITECTURE_INTEL = 0;
	public final static int PROCESSOR_ARCHITECTURE_IA64 = 6;
	public final static int PROCESSOR_ARCHITECTURE_ARM64 = 12;
	public final static int PROCESSOR_ARCHITECTURE_ARM = 5;
	public final static int PROCESSOR_ARCHITECTURE_AMD64 = 9;
	public final static int PROCESSOR_ARCHITECTURE_UNKNOWN = 0xffff;

	public final static int FILE_MAP_READ = 4;
	public final static int FILE_MAP_WRITE =2;
	public final static int FILE_MAP_COPY  =1;

	
	/** The instance. */
	ExtKernel32 INSTANCE = (ExtKernel32) Native.load("kernel32", ExtKernel32.class, W32APIOptions.DEFAULT_OPTIONS);

	/**
	 * The GetFileSize function retrieves the size, in bytes, of the specified
	 * file.
	 *
	 * @param hFile A handle to the file.
	 * @param lpFileSizeHigh address of high-order word for file size
	 * @return If the function succeeds, the return value is the low-order
	 *         doubleword of the file size, and, if lpFileSizeHigh is non-NULL,
	 *         the function puts the high-order doubleword of the file size into
	 *         the variable pointed to by that parameter.
	 * 
	 *         If the function fails and lpFileSizeHigh is NULL, the return
	 *         value is 0xFFFFFFFF. To get extended error information, call
	 *         GetLastError.
	 * 
	 *         If the function fails and lpFileSizeHigh is non-NULL, the return
	 *         value is 0xFFFFFFFF and GetLastError will return a value other
	 *         than NO_ERROR.
	 */
	public int GetFileSize(HANDLE hFile, IntByReference lpFileSizeHigh);
}
