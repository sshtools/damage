package org.x;

import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface CLibrary extends Library {

	public final static int IPC_CREAT = 00001000; /* create if key is nonexistent */
	public final static int IPC_EXCL = 00002000; /* fail if key exists */
	public final static int IPC_NOWAIT = 00004000; /* return error on wait */
	public static final int IPC_PRIVATE = 0; /* ((__kernel_key_t) 0) ?? */

	public static final int IPC_RMID = 0; /* remove resource */

	CLibrary INSTANCE = (CLibrary) Native.loadLibrary("c", CLibrary.class);

	Pointer shmat(int shmid, final Pointer shmaddr, int shmflg);

	int shmget(int key, int size, int shmflg);

	int shmdt(Pointer shmaddr);
	
	static class shmid_ds extends Structure {
		// TODO - not used yet
		@Override
		protected List<String> getFieldOrder() {
//			return Arrays.asList("shm_perm", );
			return null;
		}
		
	}

	void shmctl(int shmid, int ipcRmid, shmid_ds buf);

}