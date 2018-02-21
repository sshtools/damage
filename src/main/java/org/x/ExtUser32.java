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
	ExtUser32 INSTANCE = (ExtUser32) Native.loadLibrary("user32", ExtUser32.class,
			W32APIOptions.DEFAULT_OPTIONS);

	/**
	 * The GetCursorPos function retrieves the position of the mouse cursor, in screen coordinates.
	 * 
	 * @param hWnd
	 *            Handle to the window whose information is to be retrieved.
	 * @param lpPoint
	 *            Pointer to a LPPOINT structure to receive the information.
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
