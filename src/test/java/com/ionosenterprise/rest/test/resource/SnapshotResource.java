/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.LicenceType;
import com.ionosenterprise.rest.domain.Snapshot;

public class SnapshotResource {
    private static Snapshot snapshot;
    private static Snapshot editSnapshot;
    private static Snapshot editLvsSnapshot;

    public static Snapshot getSnapshot() {
        if (snapshot == null) {
            snapshot = new Snapshot();
            snapshot.getProperties().setName("Java SDK Test");
            snapshot.getProperties().setDescription("Java SDK test snapshot");
            snapshot.getProperties().setLicenceType(LicenceType.LINUX);
        }
        return snapshot;
    }

    public static Snapshot getEditSnapshot() {
        if (editSnapshot == null) {
            editSnapshot = new Snapshot();
            editSnapshot.getProperties().setName("Java SDK Test - RENAME");
            editSnapshot.getProperties().setDescription("Java SDK test snapshot - RENAME");
        }
        return editSnapshot;
    }

    public static Snapshot getEditLvsSnapshot() {
        if (editLvsSnapshot == null) {
            editLvsSnapshot = new Snapshot();
            editLvsSnapshot.getProperties().setName("Java SDK Test - EDIT LVS");
            editLvsSnapshot.getProperties().setDescription("Java SDK test snapshot - EDIT LVS");
            editLvsSnapshot.getProperties().setCpuHotPlug(true);
            editLvsSnapshot.getProperties().setCpuHotUnplug(true);
            editLvsSnapshot.getProperties().setRamHotPlug(true);
            editLvsSnapshot.getProperties().setRamHotUnplug(true);
            editLvsSnapshot.getProperties().setNicHotPlug(true);
            editLvsSnapshot.getProperties().setNicHotUnplug(true);
            editLvsSnapshot.getProperties().setDiscScsiHotPlug(true);
            editLvsSnapshot.getProperties().setDiscScsiHotUnplug(true);
            editLvsSnapshot.getProperties().setDiscVirtioHotPlug(true);
            editLvsSnapshot.getProperties().setDiscVirtioHotUnplug(true);
        }
        return editLvsSnapshot;
    }
}
