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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;

public interface ExtX extends X11 {
	class ExtXEvent extends XEvent {
		public Xdamage.NotifyEvent xdamage;
		public XFixes.BarrierNotifyEvent xfixesbarrier;
		public XFixes.CursorNotifyEvent xfixescursor;
		public XFixes.SelectionNotifyEvent xfixesselection;
	}

	class XShmSegmentInfo extends Structure {
		public NativeLong shmseg; /* resource id */
		public int shmid; /* kernel id */
		public Pointer shmaddr; /* address in client */
		public boolean readOnly; /* how the server should attach it */

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("shmseg", "shmid", "shmaddr", "readOnly");
		}
	}

	interface XExt extends Library {
		XExt INSTANCE = (XExt) Native.loadLibrary("Xext", XExt.class);

		XImage XShmCreateImage(Display display, Visual visual, int depth, int format, String data,
				XShmSegmentInfo shminfo, int width, int height);

		boolean XShmGetImage(Display dpy, Drawable drawable, XImage image, int x, int y, NativeLong plane_mask);

		boolean XShmAttach(Display dpy, XShmSegmentInfo shminfo);

		boolean XShmDetach(Display dpy, XShmSegmentInfo shminfo);
		
		boolean XShmQueryExtension(Display dpy);
	}
	interface Xcomposite extends Library {

		Xcomposite INSTANCE = (Xcomposite) Native.loadLibrary("Xcomposite", Xcomposite.class);
		
		int XCompositeQueryExtension(Display dpy, IntBuffer event_base_return, IntBuffer error_base_return);
		@Deprecated
		int XCompositeQueryExtension(Display dpy, IntByReference event_base_return, IntByReference error_base_return);
		int XCompositeQueryVersion(Display dpy, IntBuffer major_version_return, IntBuffer minor_version_return);
		@Deprecated
		int XCompositeQueryVersion(Display dpy, IntByReference major_version_return, IntByReference minor_version_return);
		int XCompositeVersion();
		void XCompositeRedirectWindow(Display dpy, Window window, int update);
		void XCompositeRedirectSubwindows(Display dpy, Window window, int update);
		void XCompositeUnredirectWindows(Display dpy, Window window, int update);
		void XCompositeUnredirectSubwindows(Display dpy, Window window, int update);
		NativeLong XCompositeCreateRegionFromBorderClip(Display dpy, Window window);
		Pixmap XCompositeNameWindowPixmap(Display dpy, Window window);
		Window XCompositeGetOverlayWindow(Display dpy, Window window);
		Window XCompositeReleaseOverlayWindow(Display dpy, Window window);
	}

	interface Xdamage extends Library {
		@FieldOrder({ "type", "serial", "send_event", "display", "drawable", "damage", "level", "more", "timestamp", "area",
		"geometry" })
		class NotifyEvent extends Structure {
			/** event base */
			public int type;
			public NativeLong serial;
			public int send_event;
			/** C type : Display* */
			public Display display;
			/** C type : Drawable */
			public NativeLong drawable;
			/** C type : Damage */
			public NativeLong damage;
			public int level;
			/** more events will be delivered immediately */
			public int more;
			/** C type : Time */
			public NativeLong timestamp;
			/** C type : XRectangle */
			public XRectangle area;
			/** C type : XRectangle */
			public XRectangle geometry;

			public NotifyEvent() {
				super();
			}

			protected ByReference newByReference() {
				return new ByReference();
			}

			protected ByValue newByValue() {
				return new ByValue();
			}

			protected NotifyEvent newInstance() {
				return new NotifyEvent();
			}

			public static class ByReference extends NotifyEvent implements Structure.ByReference {
			};

			public static class ByValue extends NotifyEvent implements Structure.ByValue {
			};
		}

		Xdamage INSTANCE = (Xdamage) Native.loadLibrary("Xdamage", Xdamage.class);
		int XDamageReportRawRectangles = 0;
		int XDamageNotify = 0;

		/**
		 * Original signature :
		 * <code>int XDamageQueryExtension(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2205</i><br>
		 * 
		 * @param dpy display
		 * @param event_base_return event base return
		 * @param error_base_return event base return
		 * 
		 * @deprecated use the safer methods
		 *             {@link Xdamage#XDamageQueryExtension(Display, IntBuffer, IntBuffer)}
		 *             and
		 *             {@link Xdamage#XDamageQueryExtension(Display, IntByReference, IntByReference)}
		 *             instead
		 * @return results
		 */
		@Deprecated
		int XDamageQueryExtension(Display dpy, IntByReference event_base_return, IntByReference error_base_return);

		/**
		 * Original signature :
		 * <code>int XDamageQueryExtension(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2205</i>
		 * 
		 * @param dpy display
		 * @param event_base_return event base return
		 * @param error_base_return event base return
		 * @return result
		 */
		int XDamageQueryExtension(Display dpy, IntBuffer event_base_return, IntBuffer error_base_return);

		/**
		 * Original signature :
		 * <code>int XDamageQueryVersion(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2207</i><br>
		 * 
		 * @param dpy display
		 * @param major_version_return major version return
		 * @param minor_version_return minor version return
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link Xdamage#XDamageQueryVersion(Display, IntBuffer, IntBuffer)}
		 *             and
		 *             {@link Xdamage#XDamageQueryVersion(Display, IntByReference, IntByReference)}
		 *             instead
		 */
		@Deprecated
		int XDamageQueryVersion(Display dpy, IntByReference major_version_return, IntByReference minor_version_return);

		/**
		 * Original signature :
		 * <code>int XDamageQueryVersion(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2207</i>
		 * 
		 * @param dpy display
		 * @param major_version_return major version return
		 * @param minor_version_return minor version return
		 * @return resukt
		 */
		int XDamageQueryVersion(Display dpy, IntBuffer major_version_return, IntBuffer minor_version_return);

		/**
		 * Original signature :
		 * <code>Damage XDamageCreate(Display*, Drawable, int)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2209</i>
		 * 
		 * @param dpy display
		 * @param drawable drawable
		 * @param level level
		 * @return result
		 */
		NativeLong XDamageCreate(Display dpy, NativeLong drawable, int level);

		/**
		 * Original signature :
		 * <code>void XDamageDestroy(Display*, Damage)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2211</i>
		 * 
		 * @param dpy display
		 * @param damage damage
		 */
		void XDamageDestroy(Display dpy, NativeLong damage);

		/**
		 * Original signature :
		 * <code>void XDamageSubtract(Display*, Damage, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xdamage.h:2213</i>
		 * 
		 * @param dpy display
		 * @param damage damage
		 * @param repair repair
		 * @param parts parts
		 */
		void XDamageSubtract(Display dpy, NativeLong damage, NativeLong repair, NativeLong parts);

		/**
		 * Original signature :
		 * <code>void XDamageAdd(Display*, Drawable, XserverRegion)</code><br>
		 * <i>native declaration : extensions/Xdamage.h:2215</i>
		 * 
		 * @param dpy display
		 * @param drawable drawable
		 * @param region region
		 */
		void XDamageAdd(Display dpy, NativeLong drawable, NativeLong region);
	}

	interface XFixes extends Library {
		int XFixesCursorNotify = 1;
		int XFixesDisplayCursorNotifyMask = (1 << 0);
		int X_XFixesGetCursorName = 24;
		int XFixesSelectionClientCloseNotify = 2;
		int WindowRegionClip = 1;
		int X_XFixesRegionExtents = 18;
		int X_XFixesIntersectRegion = 14;
		int SaveSetMap = 0;
		int XFixesSelectionNotify = 0;
		int X_XFixesGetCursorImage = 4;
		int BadRegion = 0;
		int X_XFixesChangeCursorByName = 27;
		int XFixesDisplayCursorNotify = 0;
		int X_XFixesCreateRegionFromPicture = 9;
		int XFixesNumberRequests = (35 + 1);
		int XFixesSelectionClientCloseNotifyMask = (1 << 2);
		int XFixesNumberErrors = (1 + 1);
		int X_XFixesSelectSelectionInput = 2;
		int XFixesSetSelectionOwnerNotify = 0;
		int X_XFixesHideCursor = 29;
		int X_XFixesFetchRegion = 19;
		int X_XFixesExpandRegion = 28;
		int X_XFixesCopyRegion = 12;
		int XFixesSetSelectionOwnerNotifyMask = (1 << 0);
		int X_XFixesSelectBarrierInput = 34;
		int X_XFixesSubtractRegion = 15;
		int X_XFixesCreateRegionFromGC = 8;
		int X_XFixesInvertRegion = 16;
		String XFIXES_NAME = "XFIXES";
		int XFixesBarrierHitNotifyMask = (1 << 0);
		int X_XFixesQueryVersion = 0;
		int X_XFixesSetGCClipRegion = 20;
		int X_XFixesChangeSaveSet = 1;
		int BadBarrier = 1;
		int XFixesSelectionWindowDestroyNotifyMask = (1 << 1);
		int X_XFixesCreateRegionFromBitmap = 6;
		int SaveSetNearest = 0;
		int XFixesBarrierThresholdExceededNotify = 1;
		int XFixesBarrierNotify = 2;
		int X_XFixesCreatePointerBarrierVelocity = 33;
		int WindowRegionBounding = 0;
		int XFixesBarrierThresholdExceededNotifyMask = (1 << 1);
		int X_XFixesCreatePointerBarrier = 31;
		int X_XFixesSelectCursorInput = 3;
		int X_XFixesBarrierReleasePointer = 35;
		int SaveSetRoot = 1;
		int BarrierPositiveY = (1 << 1);
		int BarrierPositiveX = (1 << 0);
		int XFixesSelectionWindowDestroyNotify = 1;
		int XFixesNumberEvents = (2 + 1);
		int X_XFixesDestroyRegion = 10;
		int X_XFixesCreateRegionFromWindow = 7;
		int X_XFixesSetCursorName = 23;
		int SaveSetUnmap = 1;
		int X_XFixesSetPictureClipRegion = 22;
		int X_XFixesChangeCursor = 26;
		int X_XFixesCreateRegion = 5;
		int X_XFixesUnionRegion = 13;
		int BarrierNegativeX = (1 << 2);
		int BarrierNegativeY = (1 << 3);
		int X_XFixesSetRegion = 11;
		int X_XFixesSetWindowShapeRegion = 21;
		int X_XFixesTranslateRegion = 17;
		int XFixesBarrierHitNotify = 0;
		int X_XFixesDestroyPointerBarrier = 32;
		int X_XFixesGetCursorImageAndName = 25;
		int X_XFixesShowCursor = 30;
		int XFIXES_MAJOR = 6;
		int XFIXES_MINOR = 0;

		@FieldOrder({ "type", "serial", "send_event", "display", "window", "subtype", "owner", "selection", "timestamp",
				"selection_timestamp" })
		public class SelectionNotifyEvent extends Structure {
			/** event base */
			public int type;
			public NativeLong serial;
			public int send_event;
			/** C type : Display* */
			public Display display;
			/** C type : Window */
			public NativeLong window;
			public int subtype;
			/** C type : Window */
			public NativeLong owner;
			/** C type : Atom */
			public NativeLong selection;
			/** C type : Time */
			public NativeLong timestamp;
			/** C type : Time */
			public NativeLong selection_timestamp;

			public SelectionNotifyEvent() {
				super();
			}

			protected ByReference newByReference() {
				return new ByReference();
			}

			protected ByValue newByValue() {
				return new ByValue();
			}

			protected SelectionNotifyEvent newInstance() {
				return new SelectionNotifyEvent();
			}

			public static class ByReference extends SelectionNotifyEvent implements Structure.ByReference {
			};

			public static class ByValue extends SelectionNotifyEvent implements Structure.ByValue {
			};
		}

		@FieldOrder({ "type", "serial", "send_event", "display", "window", "subtype", "event_id", "directions", "barrier", "x", "y",
				"velocity", "timestamp" })
		public class BarrierNotifyEvent extends Structure {
			/** event base */
			public int type;
			public NativeLong serial;
			public int send_event;
			/** C type : Display* */
			public Display display;
			/** C type : Window */
			public NativeLong window;
			public int subtype;
			/** C type : BarrierEventID */
			public int event_id;
			public int directions;
			/** C type : PointerBarrier */
			public NativeLong barrier;
			public int x;
			public int y;
			public int velocity;
			/** C type : Time */
			public NativeLong timestamp;

			public BarrierNotifyEvent() {
				super();
			}

			protected ByReference newByReference() {
				return new ByReference();
			}

			protected ByValue newByValue() {
				return new ByValue();
			}

			protected BarrierNotifyEvent newInstance() {
				return new BarrierNotifyEvent();
			}

			public static class ByReference extends BarrierNotifyEvent implements Structure.ByReference {
			};

			public static class ByValue extends BarrierNotifyEvent implements Structure.ByValue {
			};
		}

		@FieldOrder({ "type", "serial", "send_event", "display", "window", "subtype", "cursor_serial", "timestamp",
			"cursor_name"})
		class CursorNotifyEvent extends Structure {
			/** event base */
			public int type;
			public NativeLong serial;
			public int send_event;
			/** C type : Display* */
			public Display display;
			/** C type : Window */
			public NativeLong window;
			public int subtype;
			public NativeLong cursor_serial;
			/** C type : Time */
			public NativeLong timestamp;
			/** C type : Atom */
			public NativeLong cursor_name;

			public CursorNotifyEvent() {
				super();
			}

			/**
			 * @param type event base<br>
			 * @param serial serial
			 * @param send_event send event
			 * @param display C type : Display*<br>
			 * @param window C type : Window<br>
			 * @param subtype subtype
			 * @param cursor_serial cursor serial
			 * @param timestamp C type : Time<br>
			 * @param cursor_name C type : Atom
			 */
			public CursorNotifyEvent(int type, NativeLong serial, int send_event, Display display, NativeLong window, int subtype,
					NativeLong cursor_serial, NativeLong timestamp, NativeLong cursor_name) {
				super();
				this.type = type;
				this.serial = serial;
				this.send_event = send_event;
				this.display = display;
				this.window = window;
				this.subtype = subtype;
				this.cursor_serial = cursor_serial;
				this.timestamp = timestamp;
				this.cursor_name = cursor_name;
			}

			protected ByReference newByReference() {
				return new ByReference();
			}

			protected ByValue newByValue() {
				return new ByValue();
			}

			protected CursorNotifyEvent newInstance() {
				return new CursorNotifyEvent();
			}

			public static class ByReference extends CursorNotifyEvent implements Structure.ByReference {
			};

			public static class ByValue extends CursorNotifyEvent implements Structure.ByValue {
			};
		}

		@FieldOrder({ "x", "y", "width", "height", "xhot", "yhot", "cursor_serial", "pixels", "atom", "name"})
		public class XFixesCursorImage extends Structure {
			public short x;
			public short y;
			public short width;
			public short height;
			public short xhot;
			public short yhot;
			public NativeLong cursor_serial;
			/** C type : unsigned long* */
			public NativeLongByReference pixels;
			/**
			 * Version &gt;= 2 only<br>
			 * C type : Atom
			 */
			public NativeLong atom;
			/**
			 * Version &gt;= 2 only<br>
			 * C type : const char*
			 */
			public Pointer name;

			public XFixesCursorImage() {
				super();
			}

			protected ByReference newByReference() {
				return new ByReference();
			}

			protected ByValue newByValue() {
				return new ByValue();
			}

			protected XFixesCursorImage newInstance() {
				return new XFixesCursorImage();
			}

			// public static XFixesCursorImage[] newArray(int arrayLength) {
			// return Structure.newArray(XFixesCursorImage.class, arrayLength);
			// }
			public static class ByReference extends XFixesCursorImage implements Structure.ByReference {
			};

			public static class ByValue extends XFixesCursorImage implements Structure.ByValue {
			};
		}

		XFixes INSTANCE = (XFixes) Native.loadLibrary("Xfixes", XFixes.class);
		/** <i>native declaration : extensions/Xfixes.h</i> */
		public static final int XFIXES_VERSION = (int) ((6 * 10000) + (0 * 100) + (1));
		/** <i>native declaration : extensions/Xfixes.h</i> */
		public static final int XFIXES_REVISION = (int) 1;

		/**
		 * Original signature :
		 * <code>int XFixesQueryExtension(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2096</i><br>
		 * 
		 * @param dpy display
		 * @param event_base_return event base return
		 * @param error_base_return error base return
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesQueryExtension(Display, IntBuffer, IntBuffer)}
		 *             and
		 *             {@link XFixes#XFixesQueryExtension(Display, IntByReference, IntByReference)}
		 *             instead
		 */
		@Deprecated
		int XFixesQueryExtension(Display dpy, IntByReference event_base_return, IntByReference error_base_return);

		/**
		 * Original signature :
		 * <code>int XFixesQueryExtension(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2096</i>
		 * 
		 * @param dpy display
		 * @param event_base_return event base return
		 * @param error_base_return error base return
		 * @return result
		 */
		int XFixesQueryExtension(Display dpy, IntBuffer event_base_return, IntBuffer error_base_return);

		/**
		 * Original signature :
		 * <code>int XFixesQueryVersion(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2098</i><br>
		 * 
		 * @param dpy display
		 * @param major_version_return major version return
		 * @param minor_version_return minor version return
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesQueryVersion(Display, IntBuffer, IntBuffer)}
		 *             and
		 *             {@link XFixes#XFixesQueryVersion(Display, IntByReference, IntByReference)}
		 *             instead
		 */
		@Deprecated
		int XFixesQueryVersion(Display dpy, IntByReference major_version_return, IntByReference minor_version_return);

		/**
		 * Original signature :
		 * <code>int XFixesQueryVersion(Display*, int*, int*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2098</i>
		 * 
		 * @param dpy display
		 * @param major_version_return major version return
		 * @param minor_version_return minor version return
		 * @return result
		 */
		int XFixesQueryVersion(Display dpy, IntBuffer major_version_return, IntBuffer minor_version_return);

		/**
		 * Original signature : <code>int XFixesVersion()</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2100</i>
		 * 
		 * @return version
		 */
		int XFixesVersion();

		/**
		 * Original signature :
		 * <code>void XFixesChangeSaveSet(Display*, Window, int, int, int)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2102</i>
		 * 
		 * @param dpy display
		 * @param win win
		 * @param mode mode
		 * @param target target
		 * @param map map
		 */
		void XFixesChangeSaveSet(Display dpy, NativeLong win, int mode, int target, int map);

		/**
		 * Original signature :
		 * <code>void XFixesSelectSelectionInput(Display*, Window, Atom, unsigned long)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2104</i>
		 * 
		 * @param dpy display
		 * @param win win
		 * @param selection selection
		 * @param eventMask event mask
		 */
		void XFixesSelectSelectionInput(Display dpy, NativeLong win, NativeLong selection, NativeLong eventMask);

		/**
		 * Original signature :
		 * <code>void XFixesSelectCursorInput(Display*, Window, unsigned long)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2106</i>
		 * 
		 * @param dpy display
		 * @param win win
		 * @param eventMask event mask
		 */
		void XFixesSelectCursorInput(Display dpy, NativeLong win, NativeLong eventMask);

		/**
		 * Original signature :
		 * <code>XFixesCursorImage* XFixesGetCursorImage(Display*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2108</i>
		 * 
		 * @param dpy display
		 * @return image
		 */
		XFixesCursorImage XFixesGetCursorImage(Display dpy);

		/**
		 * Original signature :
		 * <code>XserverRegion XFixesCreateRegion(Display*, XRectangle*, int)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2110</i>
		 * 
		 * @param dpy display
		 * @param rectangles rectangles
		 * @param nrectangles nrectangles
		 * @return result
		 */
		NativeLong XFixesCreateRegion(Display dpy, XRectangle rectangles, int nrectangles);

		/**
		 * Original signature :
		 * <code>XserverRegion XFixesCreateRegionFromBitmap(Display*, Pixmap)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2112</i>
		 * 
		 * @param dpy display
		 * @param bitmap bitmap
		 * @return result
		 */
		NativeLong XFixesCreateRegionFromBitmap(Display dpy, NativeLong bitmap);

		/**
		 * Original signature :
		 * <code>XserverRegion XFixesCreateRegionFromWindow(Display*, Window, int)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2114</i>
		 * 
		 * @param dpy display
		 * @param window window
		 * @param kind kind
		 * @return result
		 */
		NativeLong XFixesCreateRegionFromWindow(Display dpy, NativeLong window, int kind);

		/**
		 * Original signature :
		 * <code>XserverRegion XFixesCreateRegionFromGC(Display*, GC)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2116</i>
		 * 
		 * @param dpy display
		 * @param gc gc
		 * @return result
		 */
		NativeLong XFixesCreateRegionFromGC(Display dpy, GC gc);

		/**
		 * Original signature :
		 * <code>XserverRegion XFixesCreateRegionFromPicture(Display*, XID)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2118</i>
		 * 
		 * @param dpy display
		 * @param picture picture
		 * @return result
		 */
		NativeLong XFixesCreateRegionFromPicture(Display dpy, NativeLong picture);

		/**
		 * Original signature :
		 * <code>void XFixesDestroyRegion(Display*, XserverRegion)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2120</i>
		 * 
		 * @param dpy display
		 * @param region region
		 */
		void XFixesDestroyRegion(Display dpy, NativeLong region);

		/**
		 * Original signature :
		 * <code>void XFixesSetRegion(Display*, XserverRegion, XRectangle*, int)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2122</i>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param rectangles rectangles
		 * @param nrectangles nrectangles
		 */
		void XFixesSetRegion(Display dpy, NativeLong region, XRectangle rectangles, int nrectangles);

		/**
		 * Original signature :
		 * <code>void XFixesCopyRegion(Display*, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2124</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src source
		 */
		void XFixesCopyRegion(Display dpy, NativeLong dst, NativeLong src);

		/**
		 * Original signature :
		 * <code>void XFixesUnionRegion(Display*, XserverRegion, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2126</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src1 source 1
		 * @param src2 source 2
		 */
		void XFixesUnionRegion(Display dpy, NativeLong dst, NativeLong src1, NativeLong src2);

		/**
		 * Original signature :
		 * <code>void XFixesIntersectRegion(Display*, XserverRegion, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2128</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src1 source 1
		 * @param src2 source 2
		 */
		void XFixesIntersectRegion(Display dpy, NativeLong dst, NativeLong src1, NativeLong src2);

		/**
		 * Original signature :
		 * <code>void XFixesSubtractRegion(Display*, XserverRegion, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2130</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src1 source 1
		 * @param src2 source 2
		 */
		void XFixesSubtractRegion(Display dpy, NativeLong dst, NativeLong src1, NativeLong src2);

		/**
		 * Original signature :
		 * <code>void XFixesInvertRegion(Display*, XserverRegion, XRectangle*, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2132</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param rect rectangle
		 * @param src source
		 */
		void XFixesInvertRegion(Display dpy, NativeLong dst, XRectangle rect, NativeLong src);

		/**
		 * Original signature :
		 * <code>void XFixesTranslateRegion(Display*, XserverRegion, int, int)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2134</i>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param dx X
		 * @param dy Y
		 */
		void XFixesTranslateRegion(Display dpy, NativeLong region, int dx, int dy);

		/**
		 * Original signature :
		 * <code>void XFixesRegionExtents(Display*, XserverRegion, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2136</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src source
		 */
		void XFixesRegionExtents(Display dpy, NativeLong dst, NativeLong src);

		/**
		 * Original signature :
		 * <code>XRectangle* XFixesFetchRegion(Display*, XserverRegion, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2138</i><br>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param nrectanglesRet nrectanglesRet
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesFetchRegion(Display, NativeLong, IntBuffer)}
		 *             and
		 *             {@link XFixes#XFixesFetchRegion(Display, NativeLong, IntByReference)}
		 *             instead
		 */
		@Deprecated
		XRectangle XFixesFetchRegion(Display dpy, NativeLong region, IntByReference nrectanglesRet);

		/**
		 * Original signature :
		 * <code>XRectangle* XFixesFetchRegion(Display*, XserverRegion, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2138</i>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param nrectanglesRet nrectanglesRet
		 * @return result
		 */
		XRectangle XFixesFetchRegion(Display dpy, NativeLong region, IntBuffer nrectanglesRet);

		/**
		 * Original signature :
		 * <code>XRectangle* XFixesFetchRegionAndBounds(Display*, XserverRegion, int*, XRectangle*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2140</i><br>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param nrectanglesRet nrectanglesRet
		 * @param bounds bounds
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesFetchRegionAndBounds(Display, NativeLong, IntBuffer, org.x.X11.XRectangle)}
		 *             and
		 *             {@link XFixes#XFixesFetchRegionAndBounds(Display, NativeLong, IntByReference, org.x.X11.XRectangle)}
		 *             instead
		 */
		@Deprecated
		XRectangle XFixesFetchRegionAndBounds(Display dpy, NativeLong region, IntByReference nrectanglesRet, XRectangle bounds);

		/**
		 * Original signature :
		 * <code>XRectangle* XFixesFetchRegionAndBounds(Display*, XserverRegion, int*, XRectangle*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2140</i>
		 * 
		 * @param dpy display
		 * @param region region
		 * @param nrectanglesRet nrectanglesRet
		 * @param bounds bounds
		 * @return result
		 */
		XRectangle XFixesFetchRegionAndBounds(Display dpy, NativeLong region, IntBuffer nrectanglesRet, XRectangle bounds);

		/**
		 * Original signature :
		 * <code>void XFixesSetGCClipRegion(Display*, GC, int, int, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2142</i>
		 * 
		 * @param dpy display
		 * @param gc gc
		 * @param clip_x_origin X origin
		 * @param clip_y_origin Y origin
		 * @param region region
		 */
		void XFixesSetGCClipRegion(Display dpy, GC gc, int clip_x_origin, int clip_y_origin, NativeLong region);

		/**
		 * Original signature :
		 * <code>void XFixesSetWindowShapeRegion(Display*, Window, int, int, int, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2144</i>
		 * 
		 * @param dpy display
		 * @param win wine
		 * @param shape_kind shape knd
		 * @param x_off X offset
		 * @param y_off Y offset
		 * @param region region
		 */
		void XFixesSetWindowShapeRegion(Display dpy, NativeLong win, int shape_kind, int x_off, int y_off, NativeLong region);

		/**
		 * Original signature :
		 * <code>void XFixesSetPictureClipRegion(Display*, XID, int, int, XserverRegion)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2146</i>
		 * 
		 * @param dpy display
		 * @param picture picture
		 * @param clip_x_origin X origin
		 * @param clip_y_origin Y origin
		 * @param region region
		 */
		void XFixesSetPictureClipRegion(Display dpy, NativeLong picture, int clip_x_origin, int clip_y_origin, NativeLong region);

		/**
		 * Original signature :
		 * <code>void XFixesSetCursorName(Display*, Cursor, const char*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2148</i><br>
		 * 
		 * @param dpy display
		 * @param cursor cursor
		 * @param name name
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesSetCursorName(Display, NativeLong, Pointer)}
		 *             and
		 *             {@link XFixes#XFixesSetCursorName(Display, NativeLong, String)}
		 *             instead
		 */
		@Deprecated
		void XFixesSetCursorName(Display dpy, NativeLong cursor, Pointer name);

		/**
		 * Original signature :
		 * <code>void XFixesSetCursorName(Display*, Cursor, const char*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2148</i>
		 * 
		 * @param dpy display
		 * @param cursor curosr
		 * @param name name
		 */
		void XFixesSetCursorName(Display dpy, NativeLong cursor, String name);

		/**
		 * Original signature :
		 * <code>char* XFixesGetCursorName(Display*, Cursor, Atom*)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2150</i>
		 * 
		 * @param dpy display
		 * @param cursor cursor
		 * @param atom atom
		 * @return result
		 */
		String XFixesGetCursorName(Display dpy, NativeLong cursor, NativeLongByReference atom);

		/**
		 * Original signature :
		 * <code>void XFixesChangeCursor(Display*, Cursor, Cursor)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2152</i>
		 * 
		 * @param dpy display
		 * @param source source
		 * @param destination destination
		 */
		void XFixesChangeCursor(Display dpy, NativeLong source, NativeLong destination);

		/**
		 * Original signature :
		 * <code>void XFixesChangeCursorByName(Display*, Cursor, const char*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2154</i><br>
		 * 
		 * @param dpy display
		 * @param source source
		 * @param name name
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesChangeCursorByName(Display, NativeLong, Pointer)}
		 *             and
		 *             {@link XFixes#XFixesChangeCursorByName(Display, NativeLong, String)}
		 *             instead
		 */
		@Deprecated
		void XFixesChangeCursorByName(Display dpy, NativeLong source, Pointer name);

		/**
		 * Original signature :
		 * <code>void XFixesChangeCursorByName(Display*, Cursor, const char*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2154</i>
		 * 
		 * @param dpy display
		 * @param source source
		 * @param name name
		 */
		void XFixesChangeCursorByName(Display dpy, NativeLong source, String name);

		/**
		 * Original signature :
		 * <code>void XFixesExpandRegion(Display*, XserverRegion, XserverRegion, unsigned, unsigned, unsigned, unsigned)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2156</i>
		 * 
		 * @param dpy display
		 * @param dst destination
		 * @param src source
		 * @param left left
		 * @param right right
		 * @param top top
		 * @param bottom bottom
		 */
		void XFixesExpandRegion(Display dpy, NativeLong dst, NativeLong src, int left, int right, int top, int bottom);

		/**
		 * Original signature :
		 * <code>void XFixesHideCursor(Display*, Window)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2158</i>
		 * 
		 * @param dpy display
		 * @param win win
		 */
		void XFixesHideCursor(Display dpy, NativeLong win);

		/**
		 * Original signature :
		 * <code>void XFixesShowCursor(Display*, Window)</code><br>
		 * <i>native declaration : extensions/Xfixes.h:2160</i>
		 * 
		 * @param dpy display
		 * @param win win
		 */
		void XFixesShowCursor(Display dpy, NativeLong win);

		/**
		 * Original signature :
		 * <code>PointerBarrier XFixesCreatePointerBarrier(Display*, Window, int, int, int, int, int, int, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2163</i><br>
		 * 
		 * @param dpy display
		 * @param w w
		 * @param x1 x1
		 * @param y1 y1
		 * @param x2 x2
		 * @param y2 y2
		 * @param directions directions
		 * @param num_devices num devices
		 * @param devices devices
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesCreatePointerBarrier(Display, NativeLong, int, int, int, int, int, int, IntBuffer)}
		 *             and
		 *             {@link XFixes#XFixesCreatePointerBarrier(Display, NativeLong, int, int, int, int, int, int, IntByReference)}
		 *             instead
		 */
		@Deprecated
		NativeLong XFixesCreatePointerBarrier(Display dpy, NativeLong w, int x1, int y1, int x2, int y2, int directions,
				int num_devices, IntByReference devices);

		/**
		 * Original signature :
		 * <code>PointerBarrier XFixesCreatePointerBarrier(Display*, Window, int, int, int, int, int, int, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2163</i>
		 * 
		 * @param dpy display
		 * @param w w
		 * @param x1 x1
		 * @param y1 y1
		 * @param x2 x2
		 * @param y2 y2
		 * @param directions directions
		 * @param num_devices num_devices
		 * @param devices num_devices
		 * @return result
		 */
		NativeLong XFixesCreatePointerBarrier(Display dpy, NativeLong w, int x1, int y1, int x2, int y2, int directions,
				int num_devices, IntBuffer devices);

		/**
		 * Original signature :
		 * <code>PointerBarrier XFixesCreatePointerBarrierVelocity(Display*, Window, int, int, int, int, int, int, int, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2165</i><br>
		 * 
		 * @param dpy display
		 * @param w w
		 * @param x1 x1
		 * @param y1 y1
		 * @param x2 x2
		 * @param y2 y2
		 * @param directions directions
		 * @param velocity velocity
		 * @param num_devices num_devices
		 * @param devices devices
		 * @return result
		 * 
		 * @deprecated use the safer methods
		 *             {@link XFixes#XFixesCreatePointerBarrierVelocity(Display, NativeLong, int, int, int, int, int, int, int, IntBuffer)}
		 *             and
		 *             {@link XFixes#XFixesCreatePointerBarrierVelocity(Display, NativeLong, int, int, int, int, int, int, int, IntByReference)}
		 *             instead
		 */
		@Deprecated
		NativeLong XFixesCreatePointerBarrierVelocity(Display dpy, NativeLong w, int x1, int y1, int x2, int y2, int directions,
				int velocity, int num_devices, IntByReference devices);

		/**
		 * Original signature :
		 * <code>PointerBarrier XFixesCreatePointerBarrierVelocity(Display*, Window, int, int, int, int, int, int, int, int*)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2165</i>
		 * 
		 * @param dpy display
		 * @param w w
		 * @param x1 x1
		 * @param y1 y1
		 * @param x2 x2
		 * @param y2 y1
		 * @param directions directions
		 * @param velocity velocity
		 * @param num_devices num_devices
		 * @param devices devices
		 * @return result
		 */
		NativeLong XFixesCreatePointerBarrierVelocity(Display dpy, NativeLong w, int x1, int y1, int x2, int y2, int directions,
				int velocity, int num_devices, IntBuffer devices);

		/**
		 * Original signature :
		 * <code>void XFixesDestroyPointerBarrier(Display*, PointerBarrier)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2167</i>
		 * 
		 * @param dpy display
		 * @param b b
		 */
		void XFixesDestroyPointerBarrier(Display dpy, NativeLong b);

		/**
		 * Original signature :
		 * <code>void XFixesSelectBarrierInput(Display*, Window, unsigned long)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2185</i>
		 * 
		 * @param dpy display
		 * @param win win
		 * @param eventMask event mask
		 */
		void XFixesSelectBarrierInput(Display dpy, NativeLong win, NativeLong eventMask);

		/**
		 * Original signature :
		 * <code>void XFixesBarrierReleasePointer(Display*, PointerBarrier, BarrierEventID)</code>
		 * <br>
		 * <i>native declaration : extensions/Xfixes.h:2187</i>
		 * 
		 * @param dpy display
		 * @param b b
		 * @param event_id event id
		 */
		void XFixesBarrierReleasePointer(Display dpy, NativeLong b, int event_id);

		public static class GC extends PointerType {
			public GC(Pointer address) {
				super(address);
			}

			public GC() {
				super();
			}
		};
	}

	int XInitThreads();

	int XGetErrorText(Display display, int code, ByteBuffer buffer, int len);

	int XGetErrorDatabaseText(Display display, String name, String message, String default_string, ByteBuffer buffer_return,
			int length);

	ExtX INSTANCE = (ExtX) Native.loadLibrary("X11", ExtX.class);
	public final static NativeLong AllPlanes = new NativeLong(0);

	class Macros {
		public static int DefaultDepth(Display dpy, int scr) {
			return ScreenOfDisplay(dpy, scr).root_depth;
		}

		public static Visual DefaultVisual(Display dpy, int scr) {
			return ScreenOfDisplay(dpy, scr).root_visual;
		}

		public static Screen ScreenOfDisplay(Display dpy, int scr) {
			Pointer pointer = dpy.getPointer();
			System.err.println(pointer.dump(0, 90));
			_XPrivDisplay_struct s = new _XPrivDisplay_struct(pointer);
			System.err.println(s);
			return (Screen) s.screens.toArray(s.nscreens)[scr];
		}
	}
}
