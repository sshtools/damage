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

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.Union;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinGDI;

public interface ExWinGDI extends WinGDI {
	public final static int DM_BITSPERPEL = 0x00040000;
	public final static int DM_PELSWIDTH = 0x00080000;
	public final static int DM_POSITION = 0x00000020;
	public final static int DM_PELSHEIGHT = 0x00100000;
	public static final int DISPLAY_DEVICE_PRIMARY_DEVICE = 4;

	@FieldOrder({ "cb", "DeviceName", "DeviceString", "stateFlags", "DeviceID", "DeviceKey" })
	public static class DISPLAY_DEVICEA extends Structure {
		public DWORD cb;
		public byte[] DeviceName = new byte[32];
		public byte[] DeviceString = new byte[128];
		public DWORD stateFlags;
		public byte[] DeviceID = new byte[128];
		public byte[] DeviceKey = new byte[128];
	}

	@FieldOrder({ "x", "y" })
	public static class POINTL extends Structure {
		public NativeLong x;
		public NativeLong y;
	}

	@FieldOrder({ "dmDeviceName", "dmSpecVersion", "dmDriverVersion", "dmSize", "dmDriverExtra", "dmFields", "dmUnion1", "dmColor",
			"dmDuplex", "dmYResolution", "dmTTOption", "dmCollate", "dmFormName", "dmLogPixels", "dmBitsPerPel", "dmPelsWidth",
			"dmPelsHeight", "dummyunionname2", "dmDisplayFrequency", "dmICMMethod", "dmICMIntent", "dmMediaType", "dmDitherType",
			"dmReserved1", "dmReserved2", "dmPanningWidth", "dmPanningHeight" })
	public static class DEVMODEA extends Structure {
		private final static int CCHDEVICENAME = 32;
		private final static int CCHFORMNAME = 32;
		public byte[] dmDeviceName = new byte[CCHDEVICENAME];
		public WORD dmSpecVersion;
		public WORD dmDriverVersion;
		public WORD dmSize;
		public WORD dmDriverExtra;
		public DWORD dmFields;
		public DUMMYUNIONNAME dmUnion1;

		public static class DUMMYUNIONNAME extends Union {
			public DUMMYSTRUCTNAME dummystructname;

			@FieldOrder({ "dmOrientation", "dmPaperSize", "dmPaperLength", "dmPaperWidth", "dmScale", "dmCopies", "dmDefaultSource",
					"dmPrintQuality" })
			public static class DUMMYSTRUCTNAME extends Structure {
				public short dmOrientation;
				public short dmPaperSize;
				public short dmPaperLength;
				public short dmPaperWidth;
				public short dmScale;
				public short dmCopies;
				public short dmDefaultSource;
				public short dmPrintQuality;

				public DUMMYSTRUCTNAME() {
					super();
				}
			}

			public POINTL dmPosition;
			public DUMMYSTRUCTNAME2 dummystructname2;

			@FieldOrder({ "dmPosition", "dmDisplayOrientation", "dmDisplayFixedOutput" })
			public static class DUMMYSTRUCTNAME2 extends Structure {
				public POINTL dmPosition;
				public DWORD dmDisplayOrientation;
				public DWORD dmDisplayFixedOutput;

				public DUMMYSTRUCTNAME2() {
					super();
				}
			}
		}

		public short dmColor;
		public short dmDuplex;
		public short dmYResolution;
		public short dmTTOption;
		public short dmCollate;
		public byte[] dmFormName = new byte[CCHFORMNAME];
		public WORD dmLogPixels;
		public DWORD dmBitsPerPel;
		public DWORD dmPelsWidth;
		public DWORD dmPelsHeight;
		public DUMMYUNIONNAME2 dummyunionname2;

		public static class DUMMYUNIONNAME2 extends Union {
			public DWORD dmDisplayFlags;
			public DWORD dmNup;
		}

		public DWORD dmDisplayFrequency;
		public DWORD dmICMMethod;
		public DWORD dmICMIntent;
		public DWORD dmMediaType;
		public DWORD dmDitherType;
		public DWORD dmReserved1;
		public DWORD dmReserved2;
		public DWORD dmPanningWidth;
		public DWORD dmPanningHeight;
	}
}
