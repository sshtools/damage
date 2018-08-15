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
/*
 * SSHTOOLS Limited licenses this file to you under the Apache 
 * License, Version 2.0 (the "License"); you may not use this 
 * file except in compliance with the License.  
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.x;

import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface CLibrary extends Library {
	public final static int IPC_CREAT = 00001000; /*
													 * create if key is
													 * nonexistent
													 */
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
			// return Arrays.asList("shm_perm", );
			return null;
		}
	}

	void shmctl(int shmid, int ipcRmid, shmid_ds buf);
}